package com.zzx.spanzy.mymaterialdesign.entity;

/**
 * Created by Spanzy on 2018/11/6.
 */
public class Animal {
    private String name;
    private int imageId;

    public Animal(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
