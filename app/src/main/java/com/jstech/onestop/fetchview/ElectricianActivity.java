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
import com.jstech.onestop.controller.viewholder.ViewHolderElectrician;
import com.jstech.onestop.model.Electrician;


public class ElectricianActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryElectricians;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Electrician, ViewHolderElectrician> adapterElectrician;
    RecyclerView recyclerViewElectrician;

    int size;
    String rcvShowElecType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);
        recyclerViewElectrician = (RecyclerView) findViewById(R.id.recyclerViewElectrician);
        recyclerViewElectrician.setHasFixedSize(true);
        rcvShowElecType = getIntent().getExtras().getString("keyShowElecType");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Electricians").child(rcvShowElecType);
        mManager = new LinearLayoutManager(ElectricianActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewElectrician.setLayoutManager(mManager);

        qryElectricians = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Electrician>()
                .setQuery(qryElectricians, Electrician.class)
                .build();

        adapterElectrician = new FirebaseRecyclerAdapter<Electrician, ViewHolderElectrician>(options) {

            @Override
            public ViewHolderElectrician onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderElectrician(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderElectrician viewHolder, int position, final Electrician model) {
                viewHolder.bindToElectrician(model);
            }

        };
        recyclerViewElectrician.setAdapter(adapterElectrician);

        /*qryElectricians.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(ElectricianActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(ElectricianActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/
        }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterElectrician != null) {
            adapterElectrician.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterElectrician != null) {
            adapterElectrician.stopListening();
        }
    }
}
