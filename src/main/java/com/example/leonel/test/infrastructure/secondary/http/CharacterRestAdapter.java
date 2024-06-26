package com.example.leonel.test.infrastructure.secondary.http;

import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.domain.Origin;
import com.example.leonel.test.core.port.secondary.CharacterPort;
import com.example.leonel.test.infrastructure.config.properties.UrlProperties;
import com.example.leonel.test.infrastructure.secondary.http.dto.CharacterResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class CharacterRestAdapter implements CharacterPort {
    private final RestTemplate restTemplate;
    private final UrlProperties urlProperties;

    @Override
    public Optional<Character> getById(Long id) {
        try{
            ResponseEntity<CharacterResDto> responseEntity = restTemplate.getForEntity(urlProperties.getUrlCharacter() + id, CharacterResDto.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                return Optional.of(this.transformToCharacter(responseEntity.getBody()));
            }
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                return Optional.empty();
            }

            throw new RuntimeException("error fetching data for Character");
        }
    }

    private Character transformToCharacter(CharacterResDto characterResDto) {

        Character character = Character.builder()
                .id(characterResDto.getId())
                .name(characterResDto.getName())
                .status(characterResDto.getStatus())
                .species(characterResDto.getSpecies())
                .type(characterResDto.getType())
                .episode_count(characterResDto.getEpisode().size())
                .origin(Origin.builder()
                        .name(characterResDto.getOrigin().getName())
                        .url(characterResDto.getOrigin().getUrl())
                        .build())
                .location(
                        Location.builder()
                                .url(characterResDto.getLocation().getUrl())
                                .name(characterResDto.getLocation().getName())
                                .build()
                )
                .build();

        return character;
    }

}
