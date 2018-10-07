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
import com.jstech.onestop.controller.AdapterServiceType;
import com.jstech.onestop.controller.ListServiceType;
import com.jstech.onestop.fetchview.DoctorActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class ActivityShowDoctorType extends AppCompatActivity{

    TextView txtSearchDoctorType;
    EditText eTxtSearchDoctorType;
    ArrayList<ListServiceType> typeArrayList;
    RecyclerView rview;
    AdapterServiceType adapter;

    void initTypes()
    {
        rview = findViewById(R.id.recyclerViewDoctorType);
        ListServiceType l1 = new ListServiceType("Addiction psychiatrist");
        ListServiceType l2 = new ListServiceType("Adolescent medicine specialist");
        ListServiceType l3 = new ListServiceType("Allergist (immunologist)");
        ListServiceType l4 = new ListServiceType("Cardiologist");
        ListServiceType l5 = new ListServiceType("Cardiovascular surgeon");
        ListServiceType l6 = new ListServiceType("Colon and rectal surgeon");
        ListServiceType l6b = new ListServiceType("Dentist");
        ListServiceType l7 = new ListServiceType("Dermatologist");
        ListServiceType l8 = new ListServiceType("Endocrinologist");
        ListServiceType l9 = new ListServiceType("Forensic pathologist");
        ListServiceType l10 = new ListServiceType("Gynaecologist");
        ListServiceType l11 = new ListServiceType("Neurological surgeon");
        ListServiceType l12 = new ListServiceType("Neurologist");
        ListServiceType l13 = new ListServiceType("Nuclear medicine specialist");
        ListServiceType l14 = new ListServiceType("Oncologist");
        ListServiceType l15 = new ListServiceType("Ophthalmologist");
        ListServiceType l16 = new ListServiceType("Oral surgeon (maxillofacial surgeon)");
        ListServiceType l17 = new ListServiceType("Orthodontist");
        ListServiceType l18 = new ListServiceType("Orthopaedic surgeon");
        ListServiceType l19 = new ListServiceType("Pathologist");
        ListServiceType l20 = new ListServiceType("Pediatrician");
        ListServiceType l21 = new ListServiceType("Plastic surgeon");
        ListServiceType l22 = new ListServiceType("Psychiatrist");
        ListServiceType l23 = new ListServiceType("Pulmonologist");
        ListServiceType l24 = new ListServiceType("Radiation oncologist");
        ListServiceType l25 = new ListServiceType("Radiologist");
        ListServiceType l26 = new ListServiceType("Reproductive endocrinologist");
        ListServiceType l27 = new ListServiceType("Rheumatologist");
        ListServiceType l28 = new ListServiceType("Spinal cord injury specialist");
        ListServiceType l29 = new ListServiceType("Thoracic surgeon");
        ListServiceType l30 = new ListServiceType("Urologist");
        ListServiceType l31 = new ListServiceType("Vascular surgeon");

        typeArrayList = new ArrayList<ListServiceType>();
        typeArrayList.add(l1);
        typeArrayList.add(l2);
        typeArrayList.add(l3);
        typeArrayList.add(l4);
        typeArrayList.add(l5);
        typeArrayList.add(l6);
        typeArrayList.add(l6b);
        typeArrayList.add(l7);
        typeArrayList.add(l8);
        typeArrayList.add(l9);
        typeArrayList.add(l10);
        typeArrayList.add(l11);
        typeArrayList.add(l12);
        typeArrayList.add(l13);
        typeArrayList.add(l14);
        typeArrayList.add(l15);
        typeArrayList.add(l16);
        typeArrayList.add(l17);
        typeArrayList.add(l18);
        typeArrayList.add(l19);
        typeArrayList.add(l20);
        typeArrayList.add(l21);
        typeArrayList.add(l22);
        typeArrayList.add(l23);
        typeArrayList.add(l24);
        typeArrayList.add(l25);
        typeArrayList.add(l26);
        typeArrayList.add(l27);
        typeArrayList.add(l28);
        typeArrayList.add(l29);
        typeArrayList.add(l30);
        typeArrayList.add(l31);
        adapter = new AdapterServiceType(typeArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_type);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar_showDoctorType);
        myToolbar.setTitle("Doctors");
        setSupportActionBar(myToolbar);

        eTxtSearchDoctorType = (EditText)findViewById(R.id.editTextSearchDoctorType);
        txtSearchDoctorType =(TextView)findViewById(R.id.txtSearchDoctorType);
        eTxtSearchDoctorType.setVisibility(View.INVISIBLE);
        txtSearchDoctorType.setVisibility(View.VISIBLE);
        initTypes();

        txtSearchDoctorType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchDoctorType.setVisibility(View.INVISIBLE);
                eTxtSearchDoctorType.setVisibility(View.VISIBLE);
            }
        });

        eTxtSearchDoctorType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}