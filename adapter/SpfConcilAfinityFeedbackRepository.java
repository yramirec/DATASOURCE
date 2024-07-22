package pe.financieraoh.mscspfconcil.infrastucture.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.financieraoh.mscspfconcil.infrastucture.entity.SpfConcilAfinityFeedbackEntity;

import java.util.Optional;
import java.util.UUID;

public interface SpfConcilAfinityFeedbackRepository extends JpaRepository<SpfConcilAfinityFeedbackEntity, UUID> {
    Optional<SpfConcilAfinityFeedbackEntity> findByNumPoliza(String numPoliza);
}
