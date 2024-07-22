package pe.financieraoh.mscspfconcil.infrastucture.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.financieraoh.mscspfconcil.infrastucture.entity.PucPersonasEntity;

import java.util.Optional;

public interface PucPersonasRepository extends JpaRepository<PucPersonasEntity, String> {
    Optional<PucPersonasEntity> findByCodInterno(String codInterno);
}
