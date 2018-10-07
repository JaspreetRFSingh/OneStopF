package com.jstech.onestop.view;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jstech.onestop.Manifest;
import com.jstech.onestop.R;
import com.jstech.onestop.controller.AdapterMain;
import com.jstech.onestop.controller.ListMain;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    TextView txtSearchMain;
    EditText eTxtSearchMain;
    ArrayList<ListMain> mainArrayList;
    RecyclerView rview;
    AdapterMain adapter;


    FirebaseAuth mAuth;

    void init(){
        rview = (RecyclerView)findViewById(R.id.recyclerViewMain);
        ListMain u1 = new ListMain(R.drawable.electrician,"Electrician");
        ListMain u2 = new ListMain(R.drawable.plumber,"Plumber");
        ListMain u3 = new ListMain(R.drawable.doctor,"Doctor");
        ListMain u4 = new ListMain(R.drawable.driver,"Driver");
        ListMain u5 = new ListMain(R.drawable.mechanic,"Mechanic");
        ListMain u6 = new ListMain(R.drawable.tailor,"Tailor");
        ListMain u7 = new ListMain(R.drawable.teacher,"Teacher");
        ListMain u8 = new ListMain(R.drawable.cook,"Cook");
        ListMain u9 = new ListMain(R.drawable.painter,"Painter");
        ListMain u10 = new ListMain(R.drawable.carpenter,"Carpenter");
        ListMain u11 = new ListMain(R.drawable.eventplanner,"Event Planner");
        ListMain u12 = new ListMain(R.drawable.caterer,"Caterer");
        ListMain u13 = new ListMain(R.drawable.babysitter,"Baby Sitter");
        ListMain u14 = new ListMain(R.drawable.masseuse, "Masseuse");
        ListMain u15 = new ListMain(R.drawable.beautician, "Beautician");

        mainArrayList = new ArrayList<ListMain>();
        mainArrayList.add(u1);
        mainArrayList.add(u2);
        mainArrayList.add(u3);
        mainArrayList.add(u4);
        mainArrayList.add(u5);
        mainArrayList.add(u6);
        mainArrayList.add(u7);
        mainArrayList.add(u8);
        mainArrayList.add(u9);
        mainArrayList.add(u10);
        mainArrayList.add(u11);
        mainArrayList.add(u12);
        mainArrayList.add(u13);
        mainArrayList.add(u14);
        mainArrayList.add(u15);
        Arrays.sort(new ArrayList[]{mainArrayList});
        adapter = new AdapterMain(mainArrayList, this);
        rview.setLayoutManager(new LinearLayoutManager(this));
        rview.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.app_logo);
        myToolbar.setTitle("OneStop");
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        //Permissiion Code start
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        101);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }



        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }


        //Permission Code End

        eTxtSearchMain = (EditText)findViewById(R.id.editTextSearchMain);
        txtSearchMain =(TextView)findViewById(R.id.txtSearchMain);
        eTxtSearchMain.setVisibility(View.INVISIBLE);
        txtSearchMain.setVisibility(View.VISIBLE);
        init();
        txtSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearchMain.setVisibility(View.INVISIBLE);
                eTxtSearchMain.setVisibility(View.VISIBLE);
            }
        });
        eTxtSearchMain.addTextChangedListener(new TextWatcher() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,101,0,"Add Data");
        menu.add(0,102,1,"Sign Out");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 101) {
            Intent intent = new Intent(MainActivity.this, AddNewListActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == 102)
        {
            mAuth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }
}