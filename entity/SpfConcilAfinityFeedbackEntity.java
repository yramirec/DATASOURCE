package pe.financieraoh.mscspfconcil.infrastucture.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "SPF_CONCIL_AFINITY_FEEDBACK", schema = "ESPFCONCIL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpfConcilAfinityFeedbackEntity {

    @Id
    @GeneratedValue
    @Column(name = "spf_concil_carga_file_id", columnDefinition = "uuid")
    private UUID spfConcilCargaFileId;

    @Column(name = "asegurador")
    private String asegurador;

    @Column(name = "producto")
    private String producto;

    @Column(name = "id_certificado", columnDefinition = "uuid")
    private UUID idCertificado;

    @Column(name = "num_poliza")
    private String numPoliza;

    @Column(name = "tipo_doc")
    private String tipoDoc;

    @Column(name = "num_documento")
    private String numDocumento;

    @Column(name = "num_cert_poliza")
    private Integer numCertPoliza;

    @Column(name = "estado_certificado")
    private String estadoCertificado;

    @Column(name = "cuotas_cobradas")
    private Integer cuotasCobradas;

    @Column(name = "fec_venta")
    private LocalDateTime fecVenta;

    @Column(name = "periodo_venta")
    private String periodoVenta;

    @Column(name = "fec_dig")
    private LocalDateTime fecDig;

    @Column(name = "estado_poliza")
    private String estadoPoliza;

    @Column(name = "id_lote", columnDefinition = "uuid")
    private UUID idLote;

    @Column(name = "nom_archivo")
    private String nomArchivo;

    @Column(name = "fec_ult_envio")
    private LocalDateTime fecUltEnvio;

    @Column(name = "ult_log")
    private String ultLog;

    @Column(name = "tipo_error")
    private String tipoError;

    @Column(name = "flag")
    private String flag;

    @Column(name = "usuario_creacion")
    private String usuarioCreacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "estado")
    private Integer estado;

}
