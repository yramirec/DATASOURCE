package pe.financieraoh.mscspfconcil.infrastucture.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PUC_PERSONAS", schema = "EFINAN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PucPersonasEntity {

    @Id
    @Column(name = "COD_INTERNO", nullable = false, length = 20)
    private String codInterno;

    @Column(name = "NUM_DOCUM_IDE", nullable = false, length = 20)
    private String numDocumIde;

}
