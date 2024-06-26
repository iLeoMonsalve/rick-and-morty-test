package com.example.leonel.test.infrastructure.primary.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OriginDto {
        private String name;
        private String url;
        private String dimension;
        private List<String> residents;
}



