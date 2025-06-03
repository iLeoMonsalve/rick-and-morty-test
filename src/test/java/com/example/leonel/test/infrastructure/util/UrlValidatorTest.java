package com.example.leonel.test.infrastructure.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UrlValidatorTest {

    @Test
    void isValid_shouldReturnTrue_forValidUrls() {
        // Valid URLs
        assertTrue(UrlValidator.isValid("https://rickandmortyapi.com/api/character/1"));
        assertTrue(UrlValidator.isValid("https://rickandmortyapi.com/api/location/20"));
        assertTrue(UrlValidator.isValid("https://rickandmortyapi.com/api/episode/15"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://rickandmortyapi.com/api/character/1", // Non-HTTPS protocol
            "https://malicious-site.com/api/character/1", // Non-whitelisted domain
            "https://rickandmortyapi.com/api/invalid/1", // Invalid API path
            "https://rickandmortyapi.com/api/character/1/../../sensitive", // Path traversal attempt
            "https://rickandmortyapi.com/api/character/<script>alert(1)</script>", // XSS attempt
            "ftp://rickandmortyapi.com/api/character/1", // Invalid protocol
            "javascript:alert(1)", // JavaScript URL
            "" // Empty URL
    })
    void isValid_shouldReturnFalse_forInvalidUrls(String url) {
        assertFalse(UrlValidator.isValid(url));
    }

    @Test
    void isValid_shouldReturnFalse_forNullUrl() {
        assertFalse(UrlValidator.isValid(null));
    }
}