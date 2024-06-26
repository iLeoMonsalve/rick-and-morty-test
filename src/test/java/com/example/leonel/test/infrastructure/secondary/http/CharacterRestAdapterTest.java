package com.example.leonel.test.infrastructure.secondary.http;

import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.domain.Origin;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.infrastructure.config.properties.UrlProperties;
import com.example.leonel.test.infrastructure.secondary.http.CharacterRestAdapter;
import com.example.leonel.test.infrastructure.secondary.http.dto.CharacterResDto;
import com.example.leonel.test.infrastructure.secondary.http.dto.LocationDto;
import com.example.leonel.test.infrastructure.secondary.http.dto.OriginResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CharacterRestAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UrlProperties urlProperties;

    private CharacterPort characterPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        characterPort = new CharacterRestAdapter(restTemplate, urlProperties);
    }

    @Test
    void getById_SuccessfulResponse_ReturnsCharacter() {
        // Given
        long characterId = 1L;
        CharacterResDto mockCharacterResDto = createMockCharacterResDto();
        ResponseEntity<CharacterResDto> responseEntity = new ResponseEntity<>(mockCharacterResDto, HttpStatus.OK);

        // When
        when(restTemplate.getForEntity(anyString(), eq(CharacterResDto.class)))
                .thenReturn(responseEntity);
        when(urlProperties.getUrlCharacter()).thenReturn("http://example.com/api/characters/");

        Optional<Character> characterOptional = characterPort.getById(characterId);

        // Then - Asserts
        assertTrue(characterOptional.isPresent());
        Character character = characterOptional.get();
        assertEquals(mockCharacterResDto.getId(), character.getId());
        assertEquals(mockCharacterResDto.getName(), character.getName());
        assertEquals(mockCharacterResDto.getStatus(), character.getStatus());
        assertEquals(mockCharacterResDto.getSpecies(), character.getSpecies());
        assertEquals(mockCharacterResDto.getType(), character.getType());
        assertEquals(mockCharacterResDto.getEpisode().size(), character.getEpisode_count());
        assertEquals(mockCharacterResDto.getOrigin().getName(), character.getOrigin().getName());
        assertEquals(mockCharacterResDto.getOrigin().getUrl(), character.getOrigin().getUrl());
        assertEquals(mockCharacterResDto.getLocation().getUrl(), character.getLocation().getUrl());
        assertEquals(mockCharacterResDto.getLocation().getName(), character.getLocation().getName());

        // Verify restTemplate method was called
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(CharacterResDto.class));
    }

    @Test
    void getById_NotFoundResponse_ReturnsEmptyOptional() {
        // Given
        long characterId = 1L;

        // When
        when(restTemplate.getForEntity(anyString(), eq(CharacterResDto.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Optional<Character> characterOptional = characterPort.getById(characterId);

        // Then
        assertTrue(characterOptional.isEmpty());
        verify(restTemplate, times(1)).getForEntity(anyString(), eq(CharacterResDto.class));
    }

    private CharacterResDto createMockCharacterResDto() {
        LocationDto locationDto = new LocationDto();
        locationDto.setName("Earth");
        locationDto.setUrl("https://rickandmortyapi.com/api/location/1");

        OriginResDto originDto = new OriginResDto();
        originDto.setName("Earth");
        originDto.setUrl("https://rickandmortyapi.com/api/location/1");

        CharacterResDto characterResDto = new CharacterResDto();
        characterResDto.setId(1);
        characterResDto.setName("Rick Sanchez");
        characterResDto.setStatus("Alive");
        characterResDto.setSpecies("Human");
        characterResDto.setType("Scientist");
        characterResDto.setEpisode(List.of("https://rickandmortyapi.com/api/episode/1"));
        characterResDto.setOrigin(originDto);
        characterResDto.setLocation(locationDto);
        return characterResDto;
    }
}
