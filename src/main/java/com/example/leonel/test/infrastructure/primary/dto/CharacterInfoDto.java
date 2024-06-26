package com.example.leonel.test.infrastructure.primary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterInfoDto {
        private int id;
        private String name;
        private String status;
        private String species;
        private String type;
        private int episode_count;
        private OriginDto origin;
}
