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
import com.jstech.onestop.fetchview.ElectricianActivity;

import java.util.Arrays;
import java.util.List;

public class ActivityShowElectricianType extends AppCompatActivity implements ListView.OnItemClickListener {


    String showElecType[] = {"Geysers", "AC", "Refrigerators", "Other Appliances"};
    ListView listViewShowElecList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_electrician_type);

        ListAdapter listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,showElecType);
        listViewShowElecList = (ListView)findViewById(R.id.listViewElectricianType);
        listViewShowElecList.setAdapter(listAdapter);
        Arrays.sort(showElecType);
        listViewShowElecList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(ActivityShowElectricianType.this, ElectricianActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("AC")) {
            intent.putExtra("keyShowElecType", "AC");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Refrigerators")){
            intent.putExtra("keyShowElecType", "Refrigerators");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Other Appliances")){
            intent.putExtra("keyShowElecType", "Other Appliances");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Geysers")){
            intent.putExtra("keyShowElecType", "Geysers");

        }
        startActivity(intent);
    }
}
