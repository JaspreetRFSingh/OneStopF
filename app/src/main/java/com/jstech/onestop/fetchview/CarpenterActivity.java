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
import com.jstech.onestop.controller.viewholder.ViewHolderCarpenter;
import com.jstech.onestop.model.Carpenter;

public class CarpenterActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryCarpenters;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Carpenter, ViewHolderCarpenter> adapterCarpenter;
    RecyclerView recyclerViewCarpenter;

    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter);
        recyclerViewCarpenter = (RecyclerView) findViewById(R.id.recyclerViewCarpenter);
        recyclerViewCarpenter.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Carpenters");
        mManager = new LinearLayoutManager(CarpenterActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewCarpenter.setLayoutManager(mManager);

        qryCarpenters = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Carpenter>()
                .setQuery(qryCarpenters, Carpenter.class)
                .build();

        adapterCarpenter = new FirebaseRecyclerAdapter<Carpenter, ViewHolderCarpenter>(options) {

            @Override
            public ViewHolderCarpenter onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderCarpenter(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderCarpenter viewHolder, int position, final Carpenter model) {
                viewHolder.bindToCarpenter(model);
            }

        };
        recyclerViewCarpenter.setAdapter(adapterCarpenter);
        /*qryCarpenters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(CarpenterActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(CarpenterActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterCarpenter != null) {
            adapterCarpenter.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterCarpenter != null) {
            adapterCarpenter.stopListening();
        }
    }
}