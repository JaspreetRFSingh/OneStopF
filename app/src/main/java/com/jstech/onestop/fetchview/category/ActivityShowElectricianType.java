package com.jstech.onestop.fetchview.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.controller.AdapterElectricianType;
import com.jstech.onestop.controller.ListServiceType;
import com.jstech.onestop.fetchview.ElectricianActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityShowElectricianType extends AppCompatActivity {

    ArrayList<ListServiceType> typeArrayList;
    RecyclerView rview;
    AdapterElectricianType adapter;
    void initTypes()
    {
        rview = findViewById(R.id.recyclerViewElectricianType);
        ListServiceType l1 = new ListServiceType("AC");
        ListServiceType l2 = new ListServiceType("Geysers");
        ListServiceType l3 = new ListServiceType("Other Appliances");
        ListServiceType l4 = new ListServiceType("Refrigerators");
        typeArrayList = new ArrayList<ListServiceType>();
        typeArrayList.add(l1);
        typeArrayList.add(l2);
        typeArrayList.add(l3);
        typeArrayList.add(l4);
        adapter = new AdapterElectricianType(typeArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_electrician_type);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar_showElectricianType);
        myToolbar.setTitle("Electricians");
        setSupportActionBar(myToolbar);
        initTypes();
    }
}