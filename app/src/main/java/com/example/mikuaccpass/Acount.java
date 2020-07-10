package com.example.mikuaccpass;

public class Acount {
    private String station;
    private String name;
    private String password;
    private int imageId;
    private  String origin_appname;


    public Acount(String station, String name, String password, int imageId,String origin_appname) {
        this.station = station;
        this.name = name;
        this.password = password;
        this.imageId = imageId;
        this.origin_appname=origin_appname;
    }

    public String getOrigin_appname() {
        return origin_appname;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getStation() {
        return station;
    }

    public String getPassword() {
        return password;
    }
}
