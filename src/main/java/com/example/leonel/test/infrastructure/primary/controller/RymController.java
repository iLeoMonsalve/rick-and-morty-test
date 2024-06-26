package com.example.leonel.test.infrastructure.primary.controller;


import com.example.leonel.test.core.GetCharacterUseCase;
import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.exception.ResourceNotFoundException;
import com.example.leonel.test.infrastructure.primary.dto.CharacterInfoDto;
import com.example.leonel.test.infrastructure.primary.dto.OriginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/character")
@RequiredArgsConstructor
public class RymController {

    private final GetCharacterUseCase getCharacterUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCharacterById(@PathVariable Long id) {

        try {
            Character character = this.getCharacterUseCase.getCharacterId(id);

            return ResponseEntity.ok(CharacterInfoDto.builder()
                    .id(character.getId())
                    .name(character.getName())
                    .status(character.getStatus())
                    .species(character.getSpecies())
                    .type(character.getType())
                    .episode_count(character.getEpisode_count())
                    .origin(
                            OriginDto.builder()
                                    .name(character.getOrigin().getName())
                                    .url(character.getOrigin().getUrl())
                                    .dimension(character.getOrigin().getDimension())
                                    .residents(character.getOrigin().getResidents())
                                    .build()
                    )
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
