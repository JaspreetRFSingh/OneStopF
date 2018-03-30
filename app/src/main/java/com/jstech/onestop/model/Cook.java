package com.jstech.onestop.model;

/**
 * Created by samsung on 12-02-2018.
 */

public class Cook {

    public String name;
    public double experience;
    public String address;
    public String phone;
    public String email;



    public Cook(){

    }

    public Cook(String name, double experience, String address, String phone, String email) {
        this.name = name;
        this.experience = experience;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    @Override
    public String toString() {
        return name+"\n\n"+experience+"\n\n"+email+"\n\n"+phone+"\n\n"+address;
    }
}
