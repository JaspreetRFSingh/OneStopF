package com.jstech.onestop.fetchview;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jstech.onestop.R;
import com.jstech.onestop.controller.viewholder.ViewHolderCook;
import com.jstech.onestop.model.Cook;

import java.io.Serializable;

public class CookActivity extends AppCompatActivity {


    DatabaseReference dbRef;
    Query qryCooks;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Cook, ViewHolderCook> adapterCook;
    RecyclerView recyclerViewCook;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        size = 0;
        recyclerViewCook = (RecyclerView) findViewById(R.id.recyclerViewCook);
        recyclerViewCook.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Cooks");
        mManager = new LinearLayoutManager(CookActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewCook.setLayoutManager(mManager);

        /*dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.child("Cooks").getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        qryCooks = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Cook>()
                .setQuery(qryCooks, Cook.class)
                .build();

        adapterCook = new FirebaseRecyclerAdapter<Cook, ViewHolderCook>(options) {

            @Override
            public ViewHolderCook onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderCook(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final ViewHolderCook viewHolder, final int position, final Cook model) {
                viewHolder.bindToCook(model);
            }

        };
        recyclerViewCook.setAdapter(adapterCook);

        /*if (size==0){
            Toast.makeText(CookActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(CookActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterCook != null) {
            adapterCook.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterCook != null) {
            adapterCook.stopListening();
        }
    }
}