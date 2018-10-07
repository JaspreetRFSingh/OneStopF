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
import com.jstech.onestop.controller.AdapterMechanicType;
import com.jstech.onestop.controller.AdapterTeacherType;
import com.jstech.onestop.controller.ListServiceType;
import com.jstech.onestop.fetchview.MechanicActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityShowMechanicType extends AppCompatActivity {

    ArrayList<ListServiceType> typeArrayList;
    RecyclerView rview;
    AdapterMechanicType adapter;


    void initTypes()
    {
        rview = findViewById(R.id.recyclerViewMechanicType);
        ListServiceType l1 = new ListServiceType("Bicycles");
        ListServiceType l2 = new ListServiceType("Cars");
        ListServiceType l3 = new ListServiceType("Motorcycles");
        ListServiceType l4 = new ListServiceType("Scooters");
        ListServiceType l5 = new ListServiceType("Two Wheelers (without gear)");
        typeArrayList = new ArrayList<ListServiceType>();
        typeArrayList.add(l1);
        typeArrayList.add(l2);
        typeArrayList.add(l3);
        typeArrayList.add(l4);
        typeArrayList.add(l5);
        adapter = new AdapterMechanicType(typeArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mechanic_type);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar_showMechanicType);
        myToolbar.setTitle("Mechanics");
        setSupportActionBar(myToolbar);
        initTypes();
    }
}