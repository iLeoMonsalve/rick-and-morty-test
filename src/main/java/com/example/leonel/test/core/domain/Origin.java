package com.example.leonel.test.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Origin {
    private String name;
    private String url;
    private String dimension;
    private List<String> residents;

}
