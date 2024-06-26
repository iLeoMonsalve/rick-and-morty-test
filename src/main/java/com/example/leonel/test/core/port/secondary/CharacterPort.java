package com.example.leonel.test.core.port.secondary;

import com.example.leonel.test.core.domain.Character;

import java.util.Optional;

public interface CharacterPort {
    Optional<Character> getById(Long id);
}
