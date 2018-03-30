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
import com.jstech.onestop.controller.viewholder.ViewHolderTailor;
import com.jstech.onestop.model.Tailor;


public class TailorActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    Query qryTailor;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Tailor, ViewHolderTailor> adapterTailor;
    RecyclerView recyclerViewTailor;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor);
        recyclerViewTailor = (RecyclerView) findViewById(R.id.recyclerViewTailor);
        recyclerViewTailor.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        mManager = new LinearLayoutManager(TailorActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewTailor.setLayoutManager(mManager);

        qryTailor = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Tailor>()
                .setQuery(qryTailor, Tailor.class)
                .build();

        adapterTailor = new FirebaseRecyclerAdapter<Tailor, ViewHolderTailor>(options) {

            @Override
            public ViewHolderTailor onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderTailor(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderTailor viewHolder, int position, final Tailor model) {
                viewHolder.bindToTailor(model);
            }

        };
        recyclerViewTailor.setAdapter(adapterTailor);
        /*qryTailor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(TailorActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(TailorActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterTailor != null) {
            adapterTailor.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterTailor != null) {
            adapterTailor.stopListening();
        }

    }
}