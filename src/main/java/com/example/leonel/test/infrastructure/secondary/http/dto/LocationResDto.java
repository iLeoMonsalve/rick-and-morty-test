package com.example.leonel.test.infrastructure.secondary.http.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class LocationResDto {
    private Long id;
    private String name;
    private String type;
    private String dimension;
    private List<String> residents;
    private String url;
    private Date created;
}
