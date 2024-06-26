package com.example.leonel.test.infrastructure.config;

import com.example.leonel.test.core.GetCharacterUseCase;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.core.port.secondary.LocationPort;
import com.example.leonel.test.core.service.GetCharacterServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public GetCharacterUseCase getCharacterUseCase(CharacterPort characterPort, LocationPort locationPort){
        return new GetCharacterServiceImpl(characterPort,locationPort);
    }
}
