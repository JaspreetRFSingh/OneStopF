package com.jstech.onestop.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jstech.onestop.R;
import com.jstech.onestop.model.Teacher;

public class AddTeacherActivity extends AppCompatActivity implements View.OnClickListener{


    EditText eTxtTeacherName;
    EditText eTxtTeacherExperience;
    EditText eTxtTeacherEmail;
    EditText eTxtTeacherPhone;
    EditText eTxtTeacherAddress;
    EditText eTxtTeacherQualification;
    Button btnAddTeach;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Teacher teacher = null;


    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    String subjectRcv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        teacher = new Teacher();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        subjectRcv = getIntent().getExtras().getString("keyId");

        eTxtTeacherQualification = (EditText)findViewById(R.id.editTextQualificationTeach);
        eTxtTeacherAddress = (EditText)findViewById(R.id.editTextAddressTeach);
        eTxtTeacherName = (EditText)findViewById(R.id.editTextNameTeach);
        eTxtTeacherEmail = (EditText)findViewById(R.id.editTextEmailTeach);
        eTxtTeacherExperience = (EditText)findViewById(R.id.editTextExperienceTeach);
        eTxtTeacherPhone = (EditText)findViewById(R.id.editTextPhoneTeach);
        btnAddTeach = (Button)findViewById(R.id.buttonAddTeacher);
        btnAddTeach.setOnClickListener(this);

    }

    void addTeacher()
    {
        if (!validateEntries()) {
            return;
        }

        showProgressDialog();
        String experience = eTxtTeacherExperience.getText().toString();
        String phone = eTxtTeacherPhone.getText().toString();
        String email = eTxtTeacherEmail.getText().toString();
        String name = eTxtTeacherName.getText().toString();
        String address = eTxtTeacherAddress.getText().toString();
        String qualification = eTxtTeacherQualification.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ "."+subjectRcv+".teacher@onestop.com";
        teacher.setPhone(phone);
        teacher.setEmail(email);
        teacher.setName(name);
        teacher.setAddress(address);
        teacher.setQualification(qualification);
        teacher.setExperience(Double.parseDouble(experience));
        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), teacher);
                } else {
                    Toast.makeText(AddTeacherActivity.this, "Adding teacher failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Teacher teacher) {
        eTxtTeacherPhone.setText("");
        eTxtTeacherExperience.setText("");
        eTxtTeacherEmail.setText("");
        eTxtTeacherName.setText("");
        eTxtTeacherAddress.setText("");
        eTxtTeacherQualification.setText("");
        Toast.makeText(AddTeacherActivity.this, "New teacher's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewTeacher(fUser.getUid(),teacher.getName(), teacher.getQualification(), teacher.getExperience(), subjectRcv, teacher.getEmail(), teacher.getPhone(), teacher.getAddress());
    }

    private void writeNewTeacher(String userId, String teachName, String teachQualification, Double teachExperience, String teachSubject, String teachEmail ,String teachPhone, String teachAddress) {
        Teacher teacher = new Teacher(teachName, /*teachSubject,*/ teachQualification, teachExperience, teachAddress, teachPhone, teachEmail);
        mDatabase.child("Teachers").child(teachSubject).child(userId).setValue(teacher);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtTeacherName.getText().toString())) {
            eTxtTeacherName.setError("Required");
            result = false;
        } else {
            eTxtTeacherName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTeacherQualification.getText().toString())) {
            eTxtTeacherQualification.setError("Required");
            result = false;
        } else {
            eTxtTeacherQualification.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTeacherEmail.getText().toString())) {
            eTxtTeacherEmail.setError("Required");
            result = false;
        } else {
            eTxtTeacherEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtTeacherExperience.getText().toString())) {
            eTxtTeacherExperience.setError("Required");
            result = false;
        } else {
            eTxtTeacherExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTeacherPhone.getText().toString())) {
            eTxtTeacherPhone.setError("Required");
            result = false;
        } else {
            eTxtTeacherPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTeacherAddress.getText().toString())) {
            eTxtTeacherAddress.setError("Required");
            result = false;
        } else {
            eTxtTeacherAddress.setError(null);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        addTeacher();
    }
}
