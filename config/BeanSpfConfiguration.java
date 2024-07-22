package pe.financieraoh.mscspfconcil.infrastucture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.financieraoh.mscspfconcil.application.service.SpfService;
import pe.financieraoh.mscspfconcil.application.service.DomainSpfService;
import pe.financieraoh.mscspfconcil.domain.port.SpfRepository;

@Configuration
public class BeanSpfConfiguration {

    @Bean
    SpfService spfService(final SpfRepository spfRepository){
        return new DomainSpfService(spfRepository);
    }
}
