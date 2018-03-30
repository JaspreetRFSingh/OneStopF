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
import com.jstech.onestop.firebase.AddElectricianActivity;

import java.util.Arrays;

public class ActivityElectricianType extends AppCompatActivity implements ListView.OnItemClickListener{

    String elecTypeArray[] = {"AC", "Refrigerators", "Other Appliances", "Geysers"};

    ListView elecTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_type);

        ListAdapter listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,elecTypeArray);
        elecTypeList = (ListView)findViewById(R.id.electricianTypeListView);
        elecTypeList.setAdapter(listAdapter);
        Arrays.sort(elecTypeArray);
        elecTypeList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(ActivityElectricianType.this, AddElectricianActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("AC")) {
            intent.putExtra("keyElecType", "AC");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Refrigerators")){
            intent.putExtra("keyElecType", "Refrigerators");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Other Appliances")){
            intent.putExtra("keyElecType", "Other Appliances");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Geysers")){
            intent.putExtra("keyElecType", "Geysers");

        }

        startActivity(intent);

    }
}
