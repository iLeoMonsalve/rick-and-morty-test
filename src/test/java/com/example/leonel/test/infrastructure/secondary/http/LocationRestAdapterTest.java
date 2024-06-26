package com.example.leonel.test.infrastructure.secondary.http;

import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.port.secondary.LocationPort;
import com.example.leonel.test.infrastructure.secondary.http.LocationRestAdapter;
import com.example.leonel.test.infrastructure.secondary.http.dto.LocationResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationRestAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private LocationPort locationPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        locationPort = new LocationRestAdapter(restTemplate);
    }

    @Test
    void getLocation_SuccessfulResponse_ReturnsLocation() {
        // Given
        String urlLocation = "http://example.com/api/locations/1";
        LocationResDto mockLocationResDto = createMockLocationResDto();
        ResponseEntity<LocationResDto> responseEntity = new ResponseEntity<>(mockLocationResDto, HttpStatus.OK);

        // When
        when(restTemplate.getForEntity(eq(urlLocation), eq(LocationResDto.class)))
                .thenReturn(responseEntity);


        Optional<Location> locationOptional = locationPort.getLocation(urlLocation);

        // Then - Asserts
        assertTrue(locationOptional.isPresent());
        Location location = locationOptional.get();
        assertEquals(mockLocationResDto.getId(), location.getId());
        assertEquals(mockLocationResDto.getDimension(), location.getDimension());
        assertEquals(mockLocationResDto.getResidents(), location.getResidents());

        // Verify restTemplate method was called
        verify(restTemplate, times(1)).getForEntity(eq(urlLocation), eq(LocationResDto.class));
    }

    @Test
    void getLocation_NotFoundResponse_ReturnsEmptyOptional() {
        // given
        String urlLocation = "http://example.com/api/locations/999";

        // when
        when(restTemplate.getForEntity(eq(urlLocation), eq(LocationResDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Optional<Location> locationOptional = locationPort.getLocation(urlLocation);

        // then
        assertTrue(locationOptional.isEmpty());

        // Verify restTemplate method was called
        verify(restTemplate, times(1)).getForEntity(eq(urlLocation), eq(LocationResDto.class));
    }

    private LocationResDto createMockLocationResDto() {
        LocationResDto locationResDto = new LocationResDto();
        locationResDto.setId(1L);
        locationResDto.setDimension("Dimension C-137");
        locationResDto.setResidents(List.of("2323", "2323"));
        return locationResDto;
    }
}