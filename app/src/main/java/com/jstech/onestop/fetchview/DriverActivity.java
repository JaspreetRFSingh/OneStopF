package com.jstech.onestop.fetchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jstech.onestop.R;
import com.jstech.onestop.controller.viewholder.ViewHolderDriver;
import com.jstech.onestop.model.Driver;

public class DriverActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    Query qryDrivers;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Driver, ViewHolderDriver> adapterDriver;
    RecyclerView recyclerViewDriver;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        recyclerViewDriver = (RecyclerView) findViewById(R.id.recyclerViewDriver);
        recyclerViewDriver.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Drivers");
        mManager = new LinearLayoutManager(DriverActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewDriver.setLayoutManager(mManager);

        qryDrivers = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Driver>()
                .setQuery(qryDrivers, Driver.class)
                .build();
        adapterDriver = new FirebaseRecyclerAdapter<Driver, ViewHolderDriver>(options) {
            @Override
            public ViewHolderDriver onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderDriver(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }
            @Override
            protected void onBindViewHolder(ViewHolderDriver viewHolder, int position, final Driver model) {
                viewHolder.bindToDriver(model);
            }
        };
        recyclerViewDriver.setAdapter(adapterDriver);

        /*qryDrivers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(DriverActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(DriverActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterDriver != null) {
            adapterDriver.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterDriver != null) {
            adapterDriver.stopListening();
        }
    }
}
