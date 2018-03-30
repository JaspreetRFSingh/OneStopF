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
import com.jstech.onestop.controller.viewholder.ViewHolderTeacher;
import com.jstech.onestop.model.Teacher;

public class TeacherActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryTeachers;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Teacher, ViewHolderTeacher> adapterTeacher;
    RecyclerView recyclerViewTeacher;
    String rcvShowTeacherType;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        recyclerViewTeacher = (RecyclerView) findViewById(R.id.recyclerViewTeacher);
        recyclerViewTeacher.setHasFixedSize(true);
        rcvShowTeacherType = getIntent().getExtras().getString("keyShowTeacherType");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Teachers").child(rcvShowTeacherType);
        mManager = new LinearLayoutManager(TeacherActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewTeacher.setLayoutManager(mManager);

        qryTeachers = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(qryTeachers, Teacher.class)
                .build();

        adapterTeacher = new FirebaseRecyclerAdapter<Teacher, ViewHolderTeacher>(options) {

            @Override
            public ViewHolderTeacher onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderTeacher(inflater.inflate(R.layout.item_teacher_cardview, viewGroup, false));
            }
            @Override
            protected void onBindViewHolder(ViewHolderTeacher viewHolder, int position, final Teacher model) {
                viewHolder.bindToTeacher(model);
            }
        };
        recyclerViewTeacher.setAdapter(adapterTeacher);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapterTeacher != null) {
            adapterTeacher.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterTeacher != null) {
            adapterTeacher.stopListening();
        }
    }
}
