package com.jstech.onestop.controller;

/**
 * Created by samsung on 20-02-2018.
 */

public class ListMain {


    int icon;
    String name;
    public ListMain()
    {
    }

    public ListMain(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
