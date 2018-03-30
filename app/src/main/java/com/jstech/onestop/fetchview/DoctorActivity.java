
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
import com.jstech.onestop.controller.viewholder.ViewHolderDoctor;
import com.jstech.onestop.model.Doctor;

public class DoctorActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryDoctors;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Doctor, ViewHolderDoctor> adapterDoctor;
    RecyclerView recyclerViewDoctor;
    String rcvShowDoctorType;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        recyclerViewDoctor = (RecyclerView) findViewById(R.id.recyclerViewDoctor);
        recyclerViewDoctor.setHasFixedSize(true);
        rcvShowDoctorType = getIntent().getExtras().getString("keyShowDoctorType");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(rcvShowDoctorType);
        mManager = new LinearLayoutManager(DoctorActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewDoctor.setLayoutManager(mManager);

        qryDoctors = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Doctor>()
                .setQuery(qryDoctors, Doctor.class)
                .build();

        adapterDoctor = new FirebaseRecyclerAdapter<Doctor, ViewHolderDoctor>(options) {

            @Override
            public ViewHolderDoctor onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderDoctor(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(ViewHolderDoctor viewHolder, int position, final Doctor model) {
                viewHolder.bindToDoctor(model);
            }

        };
        recyclerViewDoctor.setAdapter(adapterDoctor);

        /*qryDoctors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                size = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (size==0){
            Toast.makeText(DoctorActivity.this,"No records found!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(DoctorActivity.this,size + " records found!", Toast.LENGTH_LONG).show();
        }*/

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterDoctor != null) {
            adapterDoctor.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterDoctor != null) {
            adapterDoctor.stopListening();
        }
    }
}
