package com.example.xc_voyager.easylife;

/**
 * Created by xc_voyager on 2017/12/18.
 */

public class Statistic {
    private String name;
    private String description;
    private int imageId;

    public Statistic(String name, String description, int imageId){
        this.name = name;
        this.description = description;
        this.imageId = imageId;
    }

    public Statistic(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getImageId(){
        return imageId;
    }
}
