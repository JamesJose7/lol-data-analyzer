package com.jeep.lolesports.model.static_riot;

public class Champion {
    private int key;
    private String name;
    private String imageName;

    public Champion(int key, String name, String imageName) {
        this.key = key;
        this.name = name;
        this.imageName = imageName;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }
}
