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
import com.jstech.onestop.controller.viewholder.ViewHolderMechanic;
import com.jstech.onestop.model.Mechanic;
public class MechanicActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryMechanic;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Mechanic, ViewHolderMechanic> adapterMechanic;
    RecyclerView recyclerViewMechanic;
    int size;
    String rcvShowMechType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
        recyclerViewMechanic = (RecyclerView) findViewById(R.id.recyclerViewMechanic);
        recyclerViewMechanic.setHasFixedSize(true);
        rcvShowMechType = getIntent().getExtras().getString("keyShowMechType");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Mechanics").child(rcvShowMechType);
        mManager = new LinearLayoutManager(MechanicActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewMechanic.setLayoutManager(mManager);

        qryMechanic = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Mechanic>()
                .setQuery(qryMechanic, Mechanic.class)
                .build();

        adapterMechanic = new FirebaseRecyclerAdapter<Mechanic, ViewHolderMechanic>(options) {

            @Override
            public ViewHolderMechanic onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderMechanic(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderMechanic viewHolder, int position, final Mechanic model) {
                viewHolder.bindToMechanic(model);
            }

        };
        recyclerViewMechanic.setAdapter(adapterMechanic);
        /*qryMechanic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(MechanicActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MechanicActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterMechanic != null) {
            adapterMechanic.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterMechanic != null) {
            adapterMechanic.stopListening();
        }
    }
}