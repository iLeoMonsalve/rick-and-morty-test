package com.example.leonel.test.infrastructure.config;


import com.example.leonel.test.core.GetCharacterUseCase;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.core.port.secondary.LocationPort;
import com.example.leonel.test.core.service.GetCharacterServiceImpl;

import com.example.leonel.test.infrastructure.config.BeanConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(BeanConfiguration.class)
public class BeanConfigurationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GetCharacterUseCase getCharacterUseCase;

    @Autowired
    private CharacterPort characterPort;

    @Autowired
    private LocationPort locationPort;

    @Test
    public void testRestTemplateBean() {
        assertNotNull(restTemplate);
    }

    @Test
    public void testGetCharacterUseCaseBean() {
        assertNotNull(getCharacterUseCase);
        assertNotNull(characterPort);
        assertNotNull(locationPort);
        assertNotNull(getCharacterUseCase.getClass().getName(), GetCharacterServiceImpl.class.getName());
    }
}
