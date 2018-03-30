package com.jstech.onestop.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.jstech.onestop.R;
import com.jstech.onestop.firebase.AddBabySitterActivity;
import com.jstech.onestop.firebase.AddCarpenterActivity;
import com.jstech.onestop.firebase.AddCatererActivity;
import com.jstech.onestop.firebase.AddCookActivity;
import com.jstech.onestop.firebase.AddDoctorActivity;
import com.jstech.onestop.firebase.AddDriverActivity;
import com.jstech.onestop.firebase.AddElectricianActivity;
import com.jstech.onestop.firebase.AddEventPlannerActivity;
import com.jstech.onestop.firebase.AddMechanicActivity;
import com.jstech.onestop.firebase.AddPainterActivity;
import com.jstech.onestop.firebase.AddPlumberActivity;
import com.jstech.onestop.firebase.AddTailorActivity;
import com.jstech.onestop.view.category.ActivityDoctorType;
import com.jstech.onestop.view.category.ActivityElectricianType;
import com.jstech.onestop.view.category.ActivityMechanicType;
import com.jstech.onestop.view.category.TeacherSubjectsActivity;

import java.util.Arrays;

public class AddNewListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    String []addArray = {"Doctor", "Driver", "Electrician","Plumber", "Cook", "Mechanic", "Teacher", "Tailor", "Painter", "Carpenter", "Baby Sitter", "Event Planner", "Caterer"};

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_list);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addArray);
        listView = (ListView)findViewById(R.id.listViewAddNew);
        listView.setAdapter(listAdapter);
        Arrays.sort(addArray);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = String.valueOf(parent.getItemAtPosition(position));
        Intent intent = new Intent();
        if(String.valueOf(parent.getItemAtPosition(position)).equals("Doctor")) {
            intent = new Intent(AddNewListActivity.this, ActivityDoctorType.class);
            intent.putExtra("keyId", "Doctor");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Electrician")){
            intent = new Intent(AddNewListActivity.this, ActivityElectricianType.class);
            intent.putExtra("keyId", "Electrician");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Plumber")){
            intent = new Intent(AddNewListActivity.this, AddPlumberActivity.class);
            intent.putExtra("keyId", "Plumber");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Driver")){
            intent = new Intent(AddNewListActivity.this, AddDriverActivity.class);
            intent.putExtra("keyId", "Driver");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Mechanic")){
            intent = new Intent(AddNewListActivity.this, ActivityMechanicType.class);
            intent.putExtra("keyId", "Mechanic");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Tailor")){
            intent = new Intent(AddNewListActivity.this, AddTailorActivity.class);
            intent.putExtra("keyId", "Tailor");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Teacher")){
            intent = new Intent(AddNewListActivity.this, TeacherSubjectsActivity.class);
            intent.putExtra("keyId", "Teacher");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Cook")){
            intent = new Intent(AddNewListActivity.this, AddCookActivity.class);
            intent.putExtra("keyId", "Cook");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Painter")){
            intent = new Intent(AddNewListActivity.this, AddPainterActivity.class);
            intent.putExtra("keyId", "Painter");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Caterer")){
            intent = new Intent(AddNewListActivity.this, AddCatererActivity.class);
            intent.putExtra("keyId", "Caterer");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Carpenter")){
            intent = new Intent(AddNewListActivity.this, AddCarpenterActivity.class);
            intent.putExtra("keyId", "Carpenter");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Baby Sitter")){
            intent = new Intent(AddNewListActivity.this, AddBabySitterActivity.class);
            intent.putExtra("keyId", "Baby Sitter");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Event Planner")){
            intent = new Intent(AddNewListActivity.this, AddEventPlannerActivity.class);
            intent.putExtra("keyId", "Event Planner");
        }
        startActivity(intent);
    }
}