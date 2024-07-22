package pe.financieraoh.mscspfconcil.infrastucture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINAN_LP_SOLICITUD", schema = "EFINAN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanLpSolicitudEntity {

    @Id
    @Column(name = "NRO_SOLICITUD", nullable = false, length = 20)
    private String nroSolicitud;

    @Column(name = "TCEA")
    private BigDecimal tcea;

    @Column(name = "TERMINOS_CONDICIONES")
    private Integer terminosCondiciones;

    @Column(name = "FEC_ENVIO_MC")
    private LocalDateTime fecEnvioMc;

    @Column(name = "EST_EJECUCION_MC")
    private String estEjecucionMc;

    @Column(name = "FEC_EJECUCION_MC")
    private LocalDateTime fecEjecucionMc;

    @Column(name = "FLAG_ARC_CTA_LP")
    private Integer flagArcCtaLp;

    @Column(name = "FLAG_NRO_DUPLICADO")
    private Integer flagNroDuplicado;

    @Column(name = "TIPO_VALIDACION")
    private String tipoValidacion;

    @Column(name = "FEC_ENVIO_MAS_MC")
    private LocalDateTime fecEnvioMasMc;

    @Column(name = "EST_ENVIO_MAS_MC")
    private String estEnvioMasMc;

    @Column(name = "FLG_IL")
    private String flgIl;

    @Column(name = "NRO_SOL_FAM")
    private String nroSolFam;

    @Column(name = "ESTADO_ENVIO_GTPA")
    private String estadoEnvioGtpa;

    @Column(name = "FECHA_ENVIO_GTPA")
    private LocalDateTime fechaEnvioGtpa;

    @Column(name = "ESTADO_RESPUESTA_GTPA")
    private String estadoRespuestaGtpa;

    @Column(name = "FECHA_RESPUESTA_GTPA")
    private LocalDateTime fechaRespuestaGtpa;

    @Column(name = "MONTO_COMISION")
    private BigDecimal montoComision;

    @Column(name = "COD_CANAL")
    private String codCanal;

    @Column(name = "OBSERVACION_GTPA")
    private String observacionGtpa;

    @Column(name = "FLG_CONDICION")
    private String flgCondicion;

    @Column(name = "COD_SOLICITUD_INT")
    private String codSolicitudInt;

    @Column(name = "EST_ENVIO_SEG_CAN_GLOBAL")
    private String estEnvioSegCanGlobal;

    @Column(name = "FEC_ENVIO_SEG_CAN_GLOBAL")
    private LocalDateTime fecEnvioSegCanGlobal;

    @Column(name = "COD_TIPO_CANCELACION_SEGURO")
    private String codTipoCancelacionSeguro;

    @Column(name = "FEC_CANCELACION_SEGURO")
    private LocalDateTime fecCancelacionSeguro;

    @Column(name = "ESTADO_RECAUDO_SEG_MC")
    private String estadoRecaudoSegMc;

    @Column(name = "FECHA_RECAUDO_SEG_MC")
    private LocalDateTime fechaRecaudoSegMc;

    @Column(name = "MONTO_SEGURO_DEVOLUCION_GLOBAL")
    private BigDecimal montoSeguroDevolucionGlobal;

    @Column(name = "USU_CANCELACION_SEGURO")
    private String usuCancelacionSeguro;

    @Column(name = "FLG_ORIGEN_CAMPANA")
    private Integer flgOrigenCampana;

    @Column(name = "COD_TASA_ORI")
    private String codTasaOri;

    @Column(name = "COD_PCT_ORIG")
    private String codPctOrig;

    @Column(name = "EST_ENVIO_MC")
    private String estEnvioMc;

    @Column(name = "COD_TIPO_DESEMBOLSO", nullable = false)
    private Integer codTipoDesembolso;

    @Column(name = "FEC_SOLICITUD", nullable = false)
    private LocalDateTime fecSolicitud;

    @Column(name = "NRO_SECUENCIA", nullable = false)
    private Integer nroSecuencia;

    @Column(name = "COD_INTERNO", nullable = false)
    private String codInterno;

    @Column(name = "TIP_PRODUCTO", nullable = false)
    private Integer tipProducto;

    @Column(name = "NUM_CUENTA_ORIGEN", nullable = false)
    private String numCuentaOrigen;

    @Column(name = "NUM_TARJETA_ORIGEN", nullable = false)
    private String numTarjetaOrigen;

    @Column(name = "COD_TIPO_VENDEDOR", nullable = false)
    private Integer codTipoVendedor;

    @Column(name = "COD_VENDEDOR", nullable = false)
    private Integer codVendedor;

    @Column(name = "COD_AGENCIA", nullable = false)
    private Integer codAgencia;

    @Column(name = "MON_ASIGNADO")
    private BigDecimal monAsignado;

    @Column(name = "MON_MINIMO_ASIGNADO")
    private BigDecimal monMinimoAsignado;

    @Column(name = "MON_SOLICITADO")
    private BigDecimal monSolicitado;

    @Column(name = "COD_TASA_DISP_EFEC")
    private String codTasaDispEfec;

    @Column(name = "COD_TASA_COMPRAS")
    private String codTasaCompras;

    @Column(name = "NUM_CUOTA_LP")
    private Integer numCuotaLp;

    @Column(name = "MON_CUOTA_LP")
    private BigDecimal monCuotaLp;

    @Column(name = "DES_CAMPANA")
    private String desCampana;

    @Column(name = "EST_CAMPANA")
    private String estCampana;

    @Column(name = "NUM_CUENTA_LP")
    private String numCuentaLp;

    @Column(name = "FLAG_SEGURO")
    private Integer flagSeguro;

    @Column(name = "MON_SEGURO")
    private BigDecimal monSeguro;

    @Column(name = "NUM_CUOTA_SEG")
    private Integer numCuotaSeg;

    @Column(name = "MON_CUOTA_SEG")
    private BigDecimal monCuotaSeg;

    @Column(name = "VAL_TASA_SEG")
    private BigDecimal valTasaSeg;

    @Column(name = "EST_ENVIO_SEG", nullable = false)
    private String estEnvioSeg;

    @Column(name = "FEC_ENVIO_SEG")
    private LocalDateTime fecEnvioSeg;

    @Column(name = "EST_ENVIO")
    private String estEnvio;

    @Column(name = "FEC_ENVIO")
    private LocalDateTime fecEnvio;

    @Column(name = "EST_EJECUCION")
    private String estEjecucion;

    @Column(name = "FEC_EJECUCION")
    private LocalDateTime fecEjecucion;

    @Column(name = "EST_SOLICITUD", nullable = false)
    private Integer estSolicitud;

    @Column(name = "EST_REGISTRO", nullable = false)
    private Integer estRegistro;

    @Column(name = "FEC_REGISTRO")
    private LocalDateTime fecRegistro;

    @Column(name = "USU_REGISTRO")
    private String usuRegistro;

    @Column(name = "FEC_ACTUALIZACION")
    private LocalDateTime fecActualizacion;

    @Column(name = "USU_ACTUALIZACION")
    private String usuActualizacion;

    @Column(name = "EST_ENVIO_MC_SEG")
    private String estEnvioMcSeg;

    @Column(name = "FEC_ENVIO_MC_SEG")
    private LocalDateTime fecEnvioMcSeg;

    @Column(name = "FEC_COBRO")
    private LocalDateTime fecCobro;

    @Column(name = "FLAG_POL_ELECTRONICA")
    private Integer flagPolElectronica;

    @Column(name = "DIR_EMAIL")
    private String dirEmail;

    @Column(name = "MONTO_SEG_DESGRAVAMEN")
    private BigDecimal montoSegDesgravamen;

    @Column(name = "FLG_DISEF")
    private String flgDisef;

    @Column(name = "COD_BANCO_ABONO")
    private Integer codBancoAbono;

    @Column(name = "NUM_CUENTA_ABONO")
    private String numCuentaAbono;


}