package com.example.leonel.test.infrastructure.secondary.http;


import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.port.secondary.LocationPort;
import com.example.leonel.test.infrastructure.secondary.http.dto.LocationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocationRestAdapter implements LocationPort {

    private final RestTemplate restTemplate;

    @Override
    public Optional<Location> getLocation(String urlLocation) {

        ResponseEntity<LocationResDto> responseEntity = restTemplate.getForEntity(urlLocation, LocationResDto.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return Optional.of(this.transformToLocationDomain(responseEntity.getBody()));
        }
        return Optional.empty();

    }

    private Location transformToLocationDomain(LocationResDto locationResDto) {
        Location location = Location.builder()
                .id(locationResDto.getId())
                .dimension(locationResDto.getDimension())
                .residents(locationResDto.getResidents())
                .build();
        return location;
    }
}
