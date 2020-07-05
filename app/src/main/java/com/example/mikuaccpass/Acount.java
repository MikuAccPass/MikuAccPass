package com.example.mikuaccpass;

public class Acount {
    private String station;
    private String name;
    private String password;
    private int imageId;



    public Acount(String station, String name, String password, int imageId){
        this.station = station;
        this.name = name;
        this.password=password;
        this.imageId = imageId;
    }


    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getStation() {
        return station;
    }

    public String getPassword() {
        return password;
    }
}
