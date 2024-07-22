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
import org.springframework.stereotype.Repository;
import pe.financieraoh.mscspfconcil.domain.model.PolicyActivation;
import pe.financieraoh.mscspfconcil.domain.model.PolicyActivationRequest;
import pe.financieraoh.mscspfconcil.domain.port.SpfRepository;
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

    public SpfRepositoryImpl(SpfCrudRepository spfCrudRepository, SpfMapper spfMapper,
                             @Qualifier("destinationFohct17JdbcTemplate") JdbcTemplate oracleJdbcTemplate) {
        this.spfCrudRepository = spfCrudRepository;
        this.spfMapper = spfMapper;
        this.oracleJdbcTemplate = oracleJdbcTemplate;
    }

    @Override
    public Page<PolicyActivation> findPolicyActivations(Pageable pageable) {
        String query = """
            SELECT fls.nro_solicitud AS nroSolicitud,
                   p.num_docum_ide AS numDocumIde,
                   fls.mon_seguro AS monSeguro
            FROM FINAN_LP_SOLICITUD fls
            INNER JOIN PUC_PERSONAS p ON fls.cod_interno = p.cod_interno
            OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY
        """;

        Map<String, Object> params = new HashMap<>();
        params.put("offset", pageable.getOffset());
        params.put("limit", pageable.getPageSize());

        try {
            List<PolicyActivation> activations = oracleJdbcTemplate.query(query, params, (rs, rowNum) ->
                    PolicyActivation.builder()
                            .nroSolicitud(rs.getString("nroSolicitud"))
                            .numDocumIde(rs.getString("numDocumIde"))
                            .monSeguro(rs.getBigDecimal("monSeguro"))
                            .build()
            );
            long total = getTotalCount(); // Método para obtener el conteo total
            return new PageImpl<>(activations, pageable, total);
        } catch (DataAccessException e) {
            log.error("Error al acceder a los datos", e);
            return Page.empty();
        }
    }

    @Override
    public List<PolicyActivation> findPolicyActivationsFiltered(PolicyActivationRequest request) {
        return null;
        // ... (lógica similar a findPolicyActivations, adaptando la consulta SQL y parámetros)
    }

    private long getTotalCount() {
        String countQuery = "SELECT COUNT(*) FROM FINAN_LP_SOLICITUD fls INNER JOIN PUC_PERSONAS p ON fls.cod_interno = p.cod_interno";
        return oracleJdbcTemplate.queryForObject(countQuery, new MapSqlParameterSource[]{new MapSqlParameterSource()}, Long.class);
    }

    //query
    /*SELECT
    fls.NRO_SOLICITUD,
    p.num_docum_ide,
    fls.MON_SEGURO,
    NULL AS DIAS_SOLICITUD, -- campo calculado fecha actual - fecha de solicitud del CE
    NULL AS ESTADO_SEGURO, -- VALIDAR DE DONDE OBTENER
    NULL AS TIPO_ERROR, --OBTENER DE PG
    NULL AS MOTIVO, --OBTENER DE PG
    NULL AS ULTIMA_FECHA_REPORTE, -- DE DONDE SE OBTIENE
    NULL AS FLAG_REPORTE
    FROM EFINAN.FINAN_LP_SOLICITUD fls
    INNER JOIN EFINAN.PUC_PERSONAS p ON FLS.cod_interno = p.cod_interno*/
}
