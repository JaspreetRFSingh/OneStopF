package com.jstech.onestop.fetchview.category;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jstech.onestop.R;
import com.jstech.onestop.fetchview.MechanicActivity;

import java.util.Arrays;

public class ActivityShowMechanicType extends AppCompatActivity implements ListView.OnItemClickListener{

    String showMechType[] = {"Cars", "Scooters", "Motorcycles", "Two Wheelers (without gear)", "Bicycles"};
    ListView listViewShowMechList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_mechanic_type);

        ListAdapter listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,showMechType);
        listViewShowMechList = (ListView)findViewById(R.id.listViewMechanicType);
        listViewShowMechList.setAdapter(listAdapter);
        Arrays.sort(showMechType);
        listViewShowMechList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(ActivityShowMechanicType.this, MechanicActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("Two Wheelers (without gear)")) {
            intent.putExtra("keyShowMechType", "Two Wheelers (without gear)");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Bicycles")){
            intent.putExtra("keyShowMechType", "Bicycles");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Scooters")){
            intent.putExtra("keyShowMechType", "Scooters");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Motorcycles")){
            intent.putExtra("keyShowMechType", "Motorcycles");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Cars")){
            intent.putExtra("keyShowMechType", "Cars");

        }
        startActivity(intent);
    }
}
