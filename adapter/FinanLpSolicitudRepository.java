package pe.financieraoh.mscspfconcil.infrastucture.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.financieraoh.mscspfconcil.infrastucture.entity.FinanLpSolicitudEntity;

import java.util.Optional;

public interface FinanLpSolicitudRepository extends JpaRepository<FinanLpSolicitudEntity, String> {
    Optional<FinanLpSolicitudEntity> findByNroSolicitud(String nroSolicitud);
}
