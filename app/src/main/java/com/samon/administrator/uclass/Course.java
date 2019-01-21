package com.samon.administrator.uclass;

public class Course {
    private String name;
    private int imageId;

    public Course(String name , int imageId){
        this.name = name;
        this.imageId = imageId;

    }

    public String getName(){
        return  name;
    }

    public int getImageId() {
        return imageId;
    }
}
