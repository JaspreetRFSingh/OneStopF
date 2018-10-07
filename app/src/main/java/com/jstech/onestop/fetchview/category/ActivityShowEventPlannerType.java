package com.jstech.onestop.fetchview.category;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jstech.onestop.R;
import com.jstech.onestop.controller.AdapterEventPlannerType;
import com.jstech.onestop.controller.ListServiceType;

import java.util.ArrayList;

public class ActivityShowEventPlannerType extends AppCompatActivity {

    ArrayList<ListServiceType> typeArrayList;
    RecyclerView rview;
    AdapterEventPlannerType adapter;
    void initTypes()
    {
        rview = findViewById(R.id.recyclerViewEventPlannerType);
        ListServiceType l1 = new ListServiceType("Award Ceremonies");
        ListServiceType l2 = new ListServiceType("Birthday Parties");
        ListServiceType l3 = new ListServiceType("Conferences");
        ListServiceType l4 = new ListServiceType("Fund Raiser Events");
        ListServiceType l5 = new ListServiceType("Kitty Parties");
        ListServiceType l6 = new ListServiceType("Meetings");
        ListServiceType l7 = new ListServiceType("Reception Parties");
        ListServiceType l8 = new ListServiceType("Seminars");
        ListServiceType l9 = new ListServiceType("Weddings");
        typeArrayList = new ArrayList<ListServiceType>();
        typeArrayList.add(l1);
        typeArrayList.add(l2);
        typeArrayList.add(l3);
        typeArrayList.add(l4);
        typeArrayList.add(l5);
        typeArrayList.add(l6);
        typeArrayList.add(l7);
        typeArrayList.add(l8);
        typeArrayList.add(l9);
        adapter = new AdapterEventPlannerType(typeArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_planner_type);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar_showEventPlannerType);
        myToolbar.setTitle("Event Planners");
        setSupportActionBar(myToolbar);
        initTypes();
    }
}