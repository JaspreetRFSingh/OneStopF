package com.jstech.onestop.view.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jstech.onestop.R;
import com.jstech.onestop.firebase.AddEventPlannerActivity;

import java.util.Arrays;

public class ActivityEventPlannerType extends AppCompatActivity implements ListView.OnItemClickListener {

    String eventTypeArray[] = {"Award Ceremonies", "Birthday Parties", "Conferences", "Fund Raiser Events", "Kitty Parties", "Meetings", "Reception Parties","Seminars", "Weddings"};

    ListView eventTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_planner_type);

        ListAdapter listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,eventTypeArray);
        eventTypeList = (ListView)findViewById(R.id.eventPlannerTypeListView);
        eventTypeList.setAdapter(listAdapter);
        Arrays.sort(eventTypeArray);
        eventTypeList.setOnItemClickListener(this);

    }


    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(ActivityEventPlannerType.this, AddEventPlannerActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("Award Ceremonies")) {
            intent.putExtra("keyEventType", "Award Ceremonies");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Birthday Parties")){
            intent.putExtra("keyEventType", "Birthday Parties");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Conferences")){
            intent.putExtra("keyEventType", "Conferences");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Fund Raiser Events")){
            intent.putExtra("keyEventType", "Fund Raiser Events");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Kitty Parties")){
            intent.putExtra("keyEventType", "Kitty Parties");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Meetings")){
            intent.putExtra("keyEventType", "Meetings");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Reception Parties")){
            intent.putExtra("keyEventType", "Reception Parties");

        }else if(String.valueOf(parent.getItemAtPosition(position)).equals("Seminars")){
            intent.putExtra("keyEventType", "Seminars");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Weddings")){
            intent.putExtra("keyEventType", "Weddings");
        }
        startActivity(intent);

    }
}
