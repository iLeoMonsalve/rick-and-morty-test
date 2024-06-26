package com.example.leonel.test.infrastructure.primary.controller;


import com.example.leonel.test.core.GetCharacterUseCase;
import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Origin;
import com.example.leonel.test.core.exception.ResourceNotFoundException;
import com.example.leonel.test.infrastructure.primary.controller.RymController;
import com.example.leonel.test.infrastructure.primary.dto.CharacterInfoDto;
import com.example.leonel.test.infrastructure.primary.dto.OriginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class RymControllerTest {

    @Mock
    private GetCharacterUseCase getCharacterUseCase;

    @InjectMocks
    private RymController rymController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCharacterById_success() {
        //given
        Origin origin = Origin.builder()
                .name("Earth")
                .url("http://earth.url")
                .dimension("Dimension C-137")
                .residents(Arrays.asList("Rick", "Morty"))
                .build();

        Character character = Character.builder()
                .id(1)
                .name("Rick Sanchez")
                .status("Alive")
                .species("Human")
                .type("Scientist")
                .episode_count(41)
                .origin(origin)
                .build();
        //when
        when(getCharacterUseCase.getCharacterId(anyLong())).thenReturn(character);


        ResponseEntity<?> responseEntity = rymController.getCharacterById(1L);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CharacterInfoDto responseBody = (CharacterInfoDto) responseEntity.getBody();
        assertEquals(character.getId(), responseBody.getId());
        assertEquals(character.getName(), responseBody.getName());
        assertEquals(character.getStatus(), responseBody.getStatus());
        assertEquals(character.getSpecies(), responseBody.getSpecies());
        assertEquals(character.getType(), responseBody.getType());
        assertEquals(character.getEpisode_count(), responseBody.getEpisode_count());

        OriginDto originDto = responseBody.getOrigin();
        assertEquals(character.getOrigin().getName(), originDto.getName());
        assertEquals(character.getOrigin().getUrl(), originDto.getUrl());
        assertEquals(character.getOrigin().getDimension(), originDto.getDimension());
        assertEquals(character.getOrigin().getResidents(), originDto.getResidents());
    }

    @Test
    public void testGetCharacterById_notFound() {
        // when
        when(getCharacterUseCase.getCharacterId(anyLong())).thenThrow(new ResourceNotFoundException("Character not found"));


        ResponseEntity<?> responseEntity = rymController.getCharacterById(1L);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Character not found", responseEntity.getBody());
    }
}
