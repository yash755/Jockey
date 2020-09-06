package com.gappydevelopers.xsarcasm.helper;

public class HomeMasterData {
    private final String imageName;
    int position;

    public HomeMasterData(String imageName, int position) {
        this.imageName = imageName;
        this.position = position;
    }


    public String getImageName() {
        return imageName;
    }


    public int getPosition() {
        return position;
    }
}