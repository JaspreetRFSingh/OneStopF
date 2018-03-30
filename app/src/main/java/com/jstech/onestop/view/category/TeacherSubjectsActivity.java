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
import com.jstech.onestop.firebase.AddTeacherActivity;

import java.util.Arrays;

public class TeacherSubjectsActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    String subjectsArray[] = {"English", "SBI PO", "SSC", "UGC NET", "TOEFL", "UPSC", "CAT", "GATE", "IELTS", "GRE", "Mathematics", "Physics", "Chemistry", "Punjabi", "Hindi", "Accountancy", "Economics", "Business Studies", "Biology", "Java", "C++"};
    ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subjects);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectsArray);
        listView1 = (ListView)findViewById(R.id.listTeacherSubjects);
        listView1.setAdapter(listAdapter);
        Arrays.sort(subjectsArray);
        listView1.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        intent = new Intent(TeacherSubjectsActivity.this, AddTeacherActivity.class);
        if(String.valueOf(parent.getItemAtPosition(position)).equals("Accountancy")) {
            intent.putExtra("keyId", "Accountancy");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Biology")){
            intent.putExtra("keyId", "Biology");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Business Studies")){
            intent.putExtra("keyId", "Business Studies");
        }

        else if(String.valueOf(parent.getItemAtPosition(position)).equals("C++")){
            intent.putExtra("keyId", "C++");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Chemistry")){
            intent.putExtra("keyId", "Chemistry");

        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Economics")){
            intent.putExtra("keyId", "Economics");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("English")){
            intent.putExtra("keyId", "English");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Hindi")){
            intent.putExtra("keyId", "Hindi");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Java")){
            intent.putExtra("keyId", "Java");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Mathematics")){
            intent.putExtra("keyId", "Mathematics");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Physics")){
            intent.putExtra("keyId", "Physics");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("Punjabi")){
            intent.putExtra("keyId", "Punjabi");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("SBI PO")){
            intent.putExtra("keyId", "SBI PO");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("SSC")){
            intent.putExtra("keyId", "SSC");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("UPSC")){
            intent.putExtra("keyId", "UPSC");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("CAT")){
            intent.putExtra("keyId", "CAT");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("GATE")){
            intent.putExtra("keyId", "GATE");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("UGC NET")){
            intent.putExtra("keyId", "UGC NET");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("IELTS")){
            intent.putExtra("keyId", "IELTS");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("GRE")){
            intent.putExtra("keyId", "GRE");
        }
        else if(String.valueOf(parent.getItemAtPosition(position)).equals("TOEFL")){
            intent.putExtra("keyId", "TOEFL");
        }
        startActivity(intent);
    }
}