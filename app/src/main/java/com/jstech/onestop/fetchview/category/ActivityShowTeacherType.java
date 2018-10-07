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
import com.jstech.onestop.controller.AdapterTeacherType;
import com.jstech.onestop.controller.ListServiceType;
import com.jstech.onestop.fetchview.TeacherActivity;
import com.jstech.onestop.model.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
public class ActivityShowTeacherType extends AppCompatActivity {

    TextView txtSearchTeacher;
    EditText eTxtSearchTeacherType;
    ArrayList<ListServiceType> typeArrayList;
    RecyclerView rview;
    AdapterTeacherType adapter;


    void initTypes()
    {
        rview = findViewById(R.id.recyclerViewTeacherType);
        ListServiceType l1 = new ListServiceType("Accountancy");
        ListServiceType l2 = new ListServiceType("Biology");
        ListServiceType l3 = new ListServiceType("Business Studies");
        ListServiceType l4 = new ListServiceType("C++");
        ListServiceType l5 = new ListServiceType("CAT");
        ListServiceType l6 = new ListServiceType("Chemistry");
        ListServiceType l7 = new ListServiceType("Economics");
        ListServiceType l8 = new ListServiceType("English");
        ListServiceType l9 = new ListServiceType("GATE");
        ListServiceType l10 = new ListServiceType("GRE");
        ListServiceType l11 = new ListServiceType("Hindi");
        ListServiceType l12 = new ListServiceType("IELTS");
        ListServiceType l13 = new ListServiceType("Java");
        ListServiceType l14 = new ListServiceType("Mathematics");
        ListServiceType l15 = new ListServiceType("Physics");
        ListServiceType l16 = new ListServiceType("Punjabi");
        ListServiceType l17 = new ListServiceType("SBI PO");
        ListServiceType l18 = new ListServiceType("SSC");
        ListServiceType l19 = new ListServiceType("TOEFL");
        ListServiceType l20 = new ListServiceType("UGC NET");
        ListServiceType l21 = new ListServiceType("UPSC");
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
        adapter = new AdapterTeacherType(typeArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_type);

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar_showTeacherType);
        myToolbar.setTitle("Teachers");
        setSupportActionBar(myToolbar);

        eTxtSearchTeacherType = (EditText)findViewById(R.id.editTextSearchTeacherType);
        txtSearchTeacher =(TextView)findViewById(R.id.txtSearchTeacherType);
        eTxtSearchTeacherType.setVisibility(View.INVISIBLE);
        txtSearchTeacher.setVisibility(View.VISIBLE);
        initTypes();

        txtSearchTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchTeacher.setVisibility(View.INVISIBLE);
                eTxtSearchTeacherType.setVisibility(View.VISIBLE);
            }
        });

        eTxtSearchTeacherType.addTextChangedListener(new TextWatcher() {
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