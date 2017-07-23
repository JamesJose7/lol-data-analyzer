package com.jeep.lolesports.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jeeps on 3/5/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Summoner {
    private int id;
    private String name;

    public Summoner() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
