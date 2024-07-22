package pe.financieraoh.mscspfconcil.infrastucture.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.financieraoh.mscspfconcil.infrastucture.entity.FinanLpSolicitudEntity;
import pe.financieraoh.mscspfconcil.domain.model.PolicyActivation;

@Repository
public interface SpfCrudRepository extends CrudRepository<FinanLpSolicitudEntity, String> {

    @Query("SELECT new pe.financieraoh.mscspfconcil.domain.model.PolicyActivation(" +
            "fls.nroSolicitud, " +
            "p.numDocumIde, " +
            "fls.monSeguro, " +
            "NULL, " +
            "NULL, " +
            "NULL, " +
            "NULL, " +
            "NULL, " +
            "NULL) " +
            "FROM FinanLpSolicitudEntity fls " +
            "INNER JOIN PucPersonasEntity p ON fls.codInterno = p.codInterno ")
    Page<PolicyActivation> findPolicyActivations(Pageable pageable);


}
