package com.example.leonel.test.core.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Location {
    private Long id;
    private String url;
    private String name;
    private String dimension;
    private List<String> residents;
}
