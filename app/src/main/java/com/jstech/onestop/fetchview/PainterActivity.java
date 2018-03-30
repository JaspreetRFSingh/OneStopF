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
import com.jstech.onestop.controller.viewholder.ViewHolderPainter;
import com.jstech.onestop.model.Painter;

public class PainterActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryPainters;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Painter, ViewHolderPainter> adapterPainter;
    RecyclerView recyclerViewPainter;

    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);
        recyclerViewPainter = (RecyclerView) findViewById(R.id.recyclerViewPainter);
        recyclerViewPainter.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Painters");
        mManager = new LinearLayoutManager(PainterActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewPainter.setLayoutManager(mManager);

        qryPainters = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Painter>()
                .setQuery(qryPainters, Painter.class)
                .build();

        adapterPainter = new FirebaseRecyclerAdapter<Painter, ViewHolderPainter>(options) {

            @Override
            public ViewHolderPainter onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderPainter(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderPainter viewHolder, int position, final Painter model) {
                viewHolder.bindToPainter(model);
            }
        };
        recyclerViewPainter.setAdapter(adapterPainter);
        /*qryPainters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(PainterActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(PainterActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/
    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterPainter != null) {
            adapterPainter.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterPainter != null) {
            adapterPainter.stopListening();
        }
    }
}