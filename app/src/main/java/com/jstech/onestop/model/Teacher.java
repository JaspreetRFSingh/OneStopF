package com.jstech.onestop.model;


public class Teacher {
    String name;
    //String subject;
    String qualification;
    double experience;
    String address;
    String phone;
    String email;

    public Teacher(){

    }
    public Teacher(String name /*String subject*/, String qualification, double experience, String address, String phone, String email) {
        this.name = name;
        //this.subject = subject;
        this.qualification = qualification;
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

/*
    public String getSubjects() {
        return subject;
    }

    public void setSubjects(String subject) {
        this.subject = subject;
    }
*/

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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
}
