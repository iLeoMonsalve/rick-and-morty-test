package com.example.leonel.test.infrastructure.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class UrlValidator {

    private static final List<String> ALLOWED_PROTOCOLS = Arrays.asList("https");
    private static final List<String> ALLOWED_DOMAINS = Arrays.asList(
            "rickandmortyapi.com"
    );
    private static final Pattern PATH_PATTERN = Pattern.compile("^/api/(?:character|location|episode)/?(?:\\d+)?$");

    /**
     * Validates a URL string against security rules
     *
     * @param urlString The URL to validate
     * @return true if the URL is valid and meets security requirements
     */
    public static boolean isValid(String urlString) {
        if (urlString == null || urlString.trim().isEmpty()) {
            return false;
        }

        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            return false;
        }

        // Validate protocol (only HTTPS allowed)
        if (!ALLOWED_PROTOCOLS.contains(url.getProtocol())) {
            return false;
        }

        // Validate domain (whitelist approach)
        String host = url.getHost();
        if (!isAllowedDomain(host)) {
            return false;
        }

        // Validate path structure
        String path = url.getPath();
        return PATH_PATTERN.matcher(path).matches();
    }

    private static boolean isAllowedDomain(String host) {
        return ALLOWED_DOMAINS.stream().anyMatch(host::endsWith);
    }
}