package com.jstech.onestop.model;

public class Tailor {
    String name;
    double experience;
    String address;
    String phone;
    String email;
    double latitude;
    double longitude;
    int ratCount;
    float ratings;


   public float computeRatings()
    {
        if(ratCount==0)
        {
            return 0;
        }
        else {
            return ratings/ratCount;
        }
    }

    public Tailor()
    {

    }

    public Tailor(String name, double experience, String address, String phone, String email, double latitude, double longitude) {
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        ratCount = 0;
        ratings = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return "\n\nName: "+name+"\n\nExperience: "+experience+" years\n\nEmail: "+email+"\n\nPhone: "+phone+"\n\nAddress: "+address+"\n";
    }

}
