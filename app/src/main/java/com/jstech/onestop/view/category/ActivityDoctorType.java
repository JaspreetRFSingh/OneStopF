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
import com.jstech.onestop.firebase.AddDoctorActivity;

import java.util.Arrays;

public class ActivityDoctorType extends AppCompatActivity implements ListView.OnItemClickListener{

    String doctorTypeArray[] = {"Addiction psychiatrist",
            "Adolescent medicine specialist",
            "Allergist (immunologist)",
            "Cardiologist",
            "Cardiovascular surgeon",
            "Colon and rectal surgeon",
            "Dermatologist",
            "Endocrinologist",
            "Forensic pathologist",
            "Gynaecologist",
            "Neurological surgeon",
            "Neurologist",
            "Nuclear medicine specialist",
            "Obstetrician",
            "Oncologist",
            "Ophthalmologist",
            "Oral surgeon (maxillofacial surgeon)",
            "Orthodontist",
            "Orthopaedic surgeon",
            "Pathologist",
            "Pediatrician",
            "Plastic surgeon",
            "Psychiatrist",
            "Pulmonologist",
            "Radiation oncologist",
            "Radiologist",
            "Reproductive endocrinologist",
            "Rheumatologist",
            "Spinal cord injury specialist",
            "Thoracic surgeon",
            "Urologist",
            "Vascular surgeon"};

    ListView doctorTypeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_type);

        ListAdapter listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,doctorTypeArray);
        doctorTypeList = (ListView)findViewById(R.id.doctorTypeListView);
        doctorTypeList.setAdapter(listAdapter);
        Arrays.sort(doctorTypeArray);
        doctorTypeList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        intent = new Intent(ActivityDoctorType.this, AddDoctorActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("Addiction psychiatrist")) {
            intent.putExtra("keyDoctorType", "Addiction psychiatrist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Adolescent medicine specialist")){
            intent.putExtra("keyDoctorType", "Adolescent medicine specialist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Allergist (immunologist)")){
            intent.putExtra("keyDoctorType", "Allergist (immunologist)");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Cardiologist")){
            intent.putExtra("keyDoctorType", "Cardiologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Cardiovascular surgeon")){
            intent.putExtra("keyDoctorType", "Cardiovascular surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Colon and rectal surgeon")){
            intent.putExtra("keyDoctorType", "Colon and rectal surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Dermatologist")){
            intent.putExtra("keyDoctorType", "Dermatologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Endocrinologist")){
            intent.putExtra("keyDoctorType", "Endocrinologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Forensic pathologist")){
            intent.putExtra("keyDoctorType", "Forensic pathologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Gynaecologist")){
            intent.putExtra("keyDoctorType", "Gynaecologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Neurological surgeon")){
            intent.putExtra("keyDoctorType", "Neurological surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Neurologist")){
            intent.putExtra("keyDoctorType", "Neurologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Nuclear medicine specialist")){
            intent.putExtra("keyDoctorType", "Nuclear medicine specialist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Obstetrician")){
            intent.putExtra("keyDoctorType", "Obstetrician");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Oncologist")){
            intent.putExtra("keyDoctorType", "Oncologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Ophthalmologist")){
            intent.putExtra("keyDoctorType", "Ophthalmologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Oral surgeon (maxillofacial surgeon)")){
            intent.putExtra("keyDoctorType", "Oral surgeon (maxillofacial surgeon)");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Orthodontist")){
            intent.putExtra("keyDoctorType", "Orthodontist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Orthopaedic surgeon")){
            intent.putExtra("keyDoctorType", "Orthopaedic surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Pathologist")){
            intent.putExtra("keyDoctorType", "Pathologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Pediatrician")){
            intent.putExtra("keyDoctorType", "Pediatrician");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Plastic surgeon")){
            intent.putExtra("keyDoctorType", "Plastic surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Psychiatrist")){
            intent.putExtra("keyDoctorType", "Psychiatrist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Pulmonologist")){
            intent.putExtra("keyDoctorType", "Pulmonologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Radiation oncologist")){
            intent.putExtra("keyDoctorType", "Radiation oncologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Radiologist")){
            intent.putExtra("keyDoctorType", "Radiologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Reproductive endocrinologist")){
            intent.putExtra("keyDoctorType", "Reproductive endocrinologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Rheumatologist")){
            intent.putExtra("keyDoctorType", "Rheumatologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Spinal cord injury specialist")){
            intent.putExtra("keyDoctorType", "Spinal cord injury specialist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Thoracic surgeon")){
            intent.putExtra("keyDoctorType", "Thoracic surgeon");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Urologist")){
            intent.putExtra("keyDoctorType", "Urologist");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Vascular surgeon")){
            intent.putExtra("keyDoctorType", "Vascular surgeon");
        }
        startActivity(intent);
    }
}