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
import com.jstech.onestop.controller.viewholder.ViewHolderPlumber;
import com.jstech.onestop.model.Plumber;


public class PlumberActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryPlumber;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Plumber, ViewHolderPlumber> adapterPlumber;
    RecyclerView recyclerViewPlumber;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);
        recyclerViewPlumber = (RecyclerView) findViewById(R.id.recyclerViewPlumber);
        recyclerViewPlumber.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Plumbers");
        mManager = new LinearLayoutManager(PlumberActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewPlumber.setLayoutManager(mManager);

        qryPlumber = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Plumber>()
                .setQuery(qryPlumber, Plumber.class)
                .build();

        adapterPlumber = new FirebaseRecyclerAdapter<Plumber, ViewHolderPlumber>(options) {

            @Override
            public ViewHolderPlumber onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderPlumber(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderPlumber viewHolder, int position, final Plumber model) {
                viewHolder.bindToPlumber(model);
            }

        };

        recyclerViewPlumber.setAdapter(adapterPlumber);

        /*qryPlumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(PlumberActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(PlumberActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterPlumber != null) {
            adapterPlumber.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterPlumber != null) {
            adapterPlumber.stopListening();
        }

    }
}
