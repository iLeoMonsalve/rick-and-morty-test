package com.example.leonel.test.core.service;

import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.domain.Origin;
import com.example.leonel.test.core.exception.ResourceNotFoundException;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.core.port.secondary.LocationPort;
import com.example.leonel.test.core.service.GetCharacterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class GetCharacterServiceImplTest {

    @Mock
    private CharacterPort characterPort;

    @Mock
    private LocationPort locationPort;

    private GetCharacterServiceImpl getCharacterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getCharacterService = new GetCharacterServiceImpl(characterPort, locationPort);
    }

    @Test
    void getCharacterId_CharacterFound_LocationFound_ReturnsCharacter() {
        // mocking characterPort
        long characterId = 1L;
        Character mockCharacter = createMockCharacter(1);
        when(characterPort.getById(characterId)).thenReturn(Optional.of(mockCharacter));

        // mocking locationPort
        Location mockLocation = createMockLocation();
        when(locationPort.getLocation(mockCharacter.getUrlLocation())).thenReturn(Optional.of(mockLocation));

        // call method
        Character result = getCharacterService.getCharacterId(characterId);

        // verify
        assertNotNull(result);
        assertEquals(mockCharacter.getId(), result.getId());
        assertEquals(mockCharacter.getName(), result.getName());
        assertEquals(mockLocation.getDimension(), result.getOrigin().getDimension());
        assertEquals(mockLocation.getResidents(), result.getOrigin().getResidents());

        // verify characterPort and locationPort
        verify(characterPort, times(1)).getById(characterId);
        verify(locationPort, times(1)).getLocation(mockCharacter.getUrlLocation());
    }

    @Test
    void getCharacterId_CharacterNotFound_ThrowsResourceNotFoundException() {
        // mocking characterPort when character is not found
        long characterId = 1L;
        when(characterPort.getById(characterId)).thenReturn(Optional.empty());

        // call method and expect ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> getCharacterService.getCharacterId(characterId));

        // verify characterPort
        verify(characterPort, times(1)).getById(characterId);
        verify(locationPort, never()).getLocation(anyString());
    }

    @Test
    void getCharacterId_LocationNotFound_ThrowsResourceNotFoundException() {
        // mocking characterPort
        Character mockCharacter = createMockCharacter(1);
        when(characterPort.getById(1L)).thenReturn(Optional.of(mockCharacter));

        // mocking locationPort when location is not found
        when(locationPort.getLocation(mockCharacter.getUrlLocation())).thenReturn(Optional.empty());

        // call method under test and expect ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> getCharacterService.getCharacterId(1L));

        // verify characterPort and locationPort
        verify(characterPort, times(1)).getById(1L);
        verify(locationPort, times(1)).getLocation(mockCharacter.getUrlLocation());
    }

    private Character createMockCharacter(int characterId) {
        return Character.builder()
                .id(characterId)
                .name("Rick Sanchez")
                .status("Alive")
                .species("Human")
                .type("Scientist")
                .episode_count(5)
                .origin(
                        Origin.builder()
                                .name("Earth")
                                .url("https://rickandmortyapi.com/api/location/1")
                                .build()
                )
                .build();

    }

    private Location createMockLocation() {
        return Location.builder()
                .id(1L)
                .url("https://rickandmortyapi.com/api/location/1")
                .name("Earth")
                .build();
    }
}