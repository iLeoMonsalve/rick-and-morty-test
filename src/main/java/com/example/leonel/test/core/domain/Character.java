package com.example.leonel.test.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class Character {

    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private int episode_count;
    private Origin origin;
    private Location location;

    public String getUrlLocation() {
        return this.getOrigin().getUrl().isEmpty() ? this.getLocation().getUrl() : this.getOrigin().getUrl();
    }

}
