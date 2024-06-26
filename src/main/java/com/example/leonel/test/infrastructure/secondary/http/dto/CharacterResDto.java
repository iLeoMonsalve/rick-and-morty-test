package com.example.leonel.test.infrastructure.secondary.http.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CharacterResDto {

    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private OriginResDto origin;
    private LocationDto location;
    private String image;
    private List<String> episode;
    private String url;
    private Date created;
}
