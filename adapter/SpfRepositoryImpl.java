package pe.financieraoh.mscspfconcil.infrastucture.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.financieraoh.mscspfconcil.domain.model.PolicyActivation;
import pe.financieraoh.mscspfconcil.domain.model.PolicyActivationRequest;
import pe.financieraoh.mscspfconcil.domain.port.SpfRepository;
import pe.financieraoh.mscspfconcil.infrastucture.entity.SpfConcilAfinityFeedbackEntity;
import pe.financieraoh.mscspfconcil.infrastucture.rest.mapper.SpfMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SpfRepositoryImpl implements SpfRepository {

    private final SpfCrudRepository spfCrudRepository;
    private final SpfMapper spfMapper;
    private final JdbcTemplate oracleJdbcTemplate;
    private final JdbcTemplate postgresJdbcTemplate;

    public SpfRepositoryImpl(SpfCrudRepository spfCrudRepository, SpfMapper spfMapper,
                             @Qualifier("originFulxt01JdbcTemplate") JdbcTemplate oracleJdbcTemplate,
                             @Qualifier("destinationFohct17JdbcTemplate") JdbcTemplate postgresJdbcTemplate) {
        this.spfCrudRepository = spfCrudRepository;
        this.spfMapper = spfMapper;
        this.oracleJdbcTemplate = oracleJdbcTemplate;
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }

    @Override
    public Page<PolicyActivation> findPolicyActivations(Pageable pageable) {
        String queryOracle =  """
            SELECT * FROM (
                SELECT fls.nro_solicitud AS nroSolicitud,
                       p.num_docum_ide AS numDocumIde,
                       fls.mon_seguro AS monSeguro,
                       ROWNUM rnum
                FROM EFINAN.FINAN_LP_SOLICITUD fls
                INNER JOIN EFINAN.PUC_PERSONAS p ON fls.cod_interno = p.cod_interno
            )
            WHERE rnum > ? AND ROWNUM <= ?
        """;

        try {
            List<PolicyActivation> policyActivations = oracleJdbcTemplate.query(queryOracle,
                    new Object[]{pageable.getOffset(), pageable.getOffset() + pageable.getPageSize()},
                    new PolicyActivationRowMapper());

            fillAdditionalData(policyActivations);

            long total = getTotalCount(); // Deberías tener una forma de obtener el conteo total de registros

            return new PageImpl<>(policyActivations, pageable, total);
        } catch (DataAccessException e) {
            log.error("Error accessing data", e);
            return Page.empty();
        }
    }

    @Override
    public List<PolicyActivation> findPolicyActivationsFiltered(PolicyActivationRequest request) {
        String queryOracle = """
            SELECT fls.nro_solicitud AS nroSolicitud, 
                   p.num_docum_ide AS numDocumIde, 
                   fls.mon_seguro AS monSeguro, 
                   NULL AS DIAS_SOLICITUD, 
                   NULL AS ESTADO_SEGURO, 
                   NULL AS TIPO_ERROR, 
                   NULL AS MOTIVO, 
                   NULL AS ULTIMA_FECHA_REPORTE, 
                   NULL AS FLAG_REPORTE 
            FROM EFINAN.FINAN_LP_SOLICITUD fls 
            INNER JOIN EFINAN.PUC_PERSONAS p ON fls.cod_interno = p.cod_interno 
            WHERE fls.MON_SEGURO BETWEEN ? AND ? 
              AND fls.vigencia >= ? 
              AND fls.estado_seguro = ? 
              AND fls.ultima_fecha_reporte BETWEEN ? AND ?
        """;

        try {
            List<PolicyActivation> policyActivations = oracleJdbcTemplate.query(queryOracle, new Object[]{
                    request.getMontoSPFMin(),
                    request.getMontoSPFMax(),
                    request.getVigenciaMin(),
                    request.getEstadoSeguro(),
                    request.getUltimaFechaReporteDesde(),
                    request.getUltimaFechaReporteHasta()
            }, new PolicyActivationRowMapper());

            fillAdditionalData(policyActivations);

            return policyActivations;
        } catch (DataAccessException e) {
            log.error("Error accessing data", e);
            return new ArrayList<>();
        }
    }

    private void fillAdditionalData(List<PolicyActivation> policyActivations) {
        String queryPostgres = """
            SELECT spf.num_poliza AS numPoliza, 
                   spf.tipo_error AS tipoError, 
                   spf.motivo AS motivo 
            FROM spf_concil_afinity_feedback spf 
            WHERE spf.num_poliza IN (:nroSolicitudes)
        """;

        List<String> nroSolicitudes = new ArrayList<>();
        for (PolicyActivation activation : policyActivations) {
            nroSolicitudes.add(activation.getNroSolicitud());
        }

        if (!nroSolicitudes.isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            params.put("nroSolicitudes", nroSolicitudes);

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(postgresJdbcTemplate.getDataSource());

            List<SpfConcilAfinityFeedbackEntity> feedbackEntities = namedParameterJdbcTemplate.query(
                    queryPostgres,
                    params,
                    new SpfConcilAfinityFeedbackRowMapper()
            );

            for (PolicyActivation activation : policyActivations) {
                for (SpfConcilAfinityFeedbackEntity feedback : feedbackEntities) {
                    if (activation.getNroSolicitud().equals(feedback.getNumPoliza())) {
                        activation.setTipoError(feedback.getTipoError());
                        activation.setMotivo(feedback.getFlag());
                        break;
                    }
                }
            }
        }
    }

    private long getTotalCount() {
        String countQuery = "SELECT COUNT(*) FROM FINAN_LP_SOLICITUD fls INNER JOIN PUC_PERSONAS p ON fls.cod_interno = p.cod_interno";
        return oracleJdbcTemplate.queryForObject(countQuery, Long.class);
    }

    private static class PolicyActivationRowMapper implements RowMapper<PolicyActivation> {
        @Override
        public PolicyActivation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return PolicyActivation.builder()
                    .nroSolicitud(rs.getString("nroSolicitud"))
                    .numDocumIde(rs.getString("numDocumIde"))
                    .monSeguro(rs.getBigDecimal("monSeguro"))
                    .diasSolicitud(null) // O rs.getLong("DIAS_SOLICITUD") si la columna existe
                    .estadoSeguro(null) // O rs.getString("ESTADO_SEGURO") si la columna existe
                    .tipoError(null) // O rs.getString("TIPO_ERROR") si la columna existe
                    .motivo(null) // O rs.getString("MOTIVO") si la columna existe
                    .ultimaFechaReporte(null) // O rs.getTimestamp("ULTIMA_FECHA_REPORTE") si la columna existe
                    .flagReporte(false) // O rs.getBoolean("FLAG_REPORTE") si la columna existe
                    .build();
        }
    }

    private static class SpfConcilAfinityFeedbackRowMapper implements RowMapper<SpfConcilAfinityFeedbackEntity> {
        @Override
        public SpfConcilAfinityFeedbackEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            SpfConcilAfinityFeedbackEntity entity = new SpfConcilAfinityFeedbackEntity();
            entity.setNumPoliza(rs.getString("numPoliza"));
            entity.setTipoError(rs.getString("tipoError"));
            entity.setFlag(rs.getString("motivo"));
            return entity;
        }
    }
}
