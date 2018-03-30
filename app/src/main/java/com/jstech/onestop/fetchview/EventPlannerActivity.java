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
import com.jstech.onestop.controller.viewholder.ViewHolderEventPlanner;
import com.jstech.onestop.model.EventPlanner;

public class EventPlannerActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryEvPlanners;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<EventPlanner, ViewHolderEventPlanner> adapterEvPlanner;
    RecyclerView recyclerViewEvPlanner;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_planner);
        recyclerViewEvPlanner = (RecyclerView) findViewById(R.id.recyclerViewEventPlanner);
        recyclerViewEvPlanner.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("EventPlanners");
        mManager = new LinearLayoutManager(EventPlannerActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewEvPlanner.setLayoutManager(mManager);

        qryEvPlanners = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<EventPlanner>()
                .setQuery(qryEvPlanners, EventPlanner.class)
                .build();

        adapterEvPlanner = new FirebaseRecyclerAdapter<EventPlanner, ViewHolderEventPlanner>(options) {

            @Override
            public ViewHolderEventPlanner onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderEventPlanner(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderEventPlanner viewHolder, int position, final EventPlanner model) {
                viewHolder.bindToEventPlanner(model);
            }

        };
        recyclerViewEvPlanner.setAdapter(adapterEvPlanner);
        /*qryEvPlanners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(EventPlannerActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(EventPlannerActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterEvPlanner != null) {
            adapterEvPlanner.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterEvPlanner != null) {
            adapterEvPlanner.stopListening();
        }
    }
}
