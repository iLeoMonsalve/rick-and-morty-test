package com.example.leonel.test.core.port.secondary;

import com.example.leonel.test.core.domain.Character;
import com.example.leonel.test.core.domain.Location;
import com.example.leonel.test.core.domain.Origin;

import java.util.Optional;

public interface LocationPort {
    Optional<Location> getLocation(String urlLocation);
}
