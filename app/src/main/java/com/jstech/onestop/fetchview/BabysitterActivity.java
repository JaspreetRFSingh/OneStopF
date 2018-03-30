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
import com.jstech.onestop.controller.viewholder.ViewHolderBabySitter;
import com.jstech.onestop.model.BabySitter;
public class BabysitterActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryBabySitter;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<BabySitter, ViewHolderBabySitter> adapterBabySitter;
    RecyclerView recyclerViewBabySitter;

    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter);
        recyclerViewBabySitter = (RecyclerView) findViewById(R.id.recyclerViewBabySitter);
        recyclerViewBabySitter.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("BabySitters");
        mManager = new LinearLayoutManager(BabysitterActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewBabySitter.setLayoutManager(mManager);
        qryBabySitter = dbRef.limitToFirst(100);



        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<BabySitter>()
                .setQuery(qryBabySitter, BabySitter.class)
                .build();

        adapterBabySitter = new FirebaseRecyclerAdapter<BabySitter, ViewHolderBabySitter>(options) {

            @Override
            public ViewHolderBabySitter onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderBabySitter(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderBabySitter viewHolder, int position, final BabySitter model) {
                viewHolder.bindToBabySitter(model);
            }

        };
        recyclerViewBabySitter.setAdapter(adapterBabySitter);

        /*qryBabySitter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(BabysitterActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(BabysitterActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterBabySitter != null) {
            adapterBabySitter.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterBabySitter != null) {
            adapterBabySitter.stopListening();
        }
    }
}