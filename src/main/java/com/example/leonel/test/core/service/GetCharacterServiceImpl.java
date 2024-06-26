package com.example.leonel.test.core.service;

import com.example.leonel.test.core.GetCharacterUseCase;
import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.domain.Origin;
import com.example.leonel.test.core.exception.ResourceNotFoundException;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.core.port.secondary.LocationPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetCharacterServiceImpl implements GetCharacterUseCase {

    private final CharacterPort characterPort;
    private final LocationPort locationPort;

    @Override
    public Character getCharacterId(Long id) {

        Character character = this.characterPort.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Character with ID " + id + " not found"));

        Location location = this.locationPort.getLocation(character.getUrlLocation())
                .orElseThrow(() -> new ResourceNotFoundException("Location with Origin " + character.getOrigin() + " not found"));

        character.getOrigin().setDimension(location.getDimension());
        character.getOrigin().setResidents(location.getResidents());

        return character;
    }
}
