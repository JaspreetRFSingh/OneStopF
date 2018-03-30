package com.jstech.onestop.firebase;

import android.app.ProgressDialog;
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
import com.jstech.onestop.model.Doctor;

public class AddDoctorActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtDoctorName;
    EditText eTxtDoctorExperience;
    EditText eTxtDoctorEmail;
    EditText eTxtDoctorPhone;
    EditText eTxtDoctorAddress;
    Button btnAddDoct;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Doctor doctor = null;
    String doctTypeRcv;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        doctor = new Doctor();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        doctTypeRcv = getIntent().getExtras().getString("keyDoctorType");

        eTxtDoctorAddress = (EditText)findViewById(R.id.editTextAddressDoct);
        eTxtDoctorName = (EditText)findViewById(R.id.editTextNameDoct);
        eTxtDoctorEmail = (EditText)findViewById(R.id.editTextEmailDoct);
        eTxtDoctorExperience = (EditText)findViewById(R.id.editTextExperienceDoct);
        eTxtDoctorPhone = (EditText)findViewById(R.id.editTextPhoneDoct);
        btnAddDoct = (Button)findViewById(R.id.buttonAddDoct);
        btnAddDoct.setOnClickListener(this);

    }

    void addDoctor()
    {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtDoctorExperience.getText().toString();
        String phone = eTxtDoctorPhone.getText().toString();
        String email = eTxtDoctorEmail.getText().toString();
        String name = eTxtDoctorName.getText().toString();
        String address = eTxtDoctorAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ "."+doctTypeRcv +".doctor@onestop.com";
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setName(name);
        doctor.setAddress(address);
        doctor.setExperience(Double.parseDouble(experience));


        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), doctor);
                } else {
                    Toast.makeText(AddDoctorActivity.this, "Adding doctor failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Doctor doctor) {
        eTxtDoctorPhone.setText("");
        eTxtDoctorExperience.setText("");
        eTxtDoctorEmail.setText("");
        eTxtDoctorName.setText("");
        eTxtDoctorAddress.setText("");
        Toast.makeText(AddDoctorActivity.this, "New doctor's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewDoctor(fUser.getUid(),doctor.getName(), doctor.getExperience(), doctor.getEmail(), doctor.getPhone(), doctor.getAddress());
    }

    private void writeNewDoctor(String userId, String doctName, Double doctExperience, String doctEmail ,String doctPhone, String doctAddress) {
        Doctor doctor = new Doctor(doctName, doctExperience, doctAddress, doctPhone, doctEmail);
        mDatabase.child("Doctors").child(doctTypeRcv).child(userId).setValue(doctor);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtDoctorName.getText().toString())) {
            eTxtDoctorName.setError("Required");
            result = false;
        } else {
            eTxtDoctorName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDoctorEmail.getText().toString())) {
            eTxtDoctorEmail.setError("Required");
            result = false;
        } else {
            eTxtDoctorEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtDoctorExperience.getText().toString())) {
            eTxtDoctorExperience.setError("Required");
            result = false;
        } else {
            eTxtDoctorExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDoctorPhone.getText().toString())) {
            eTxtDoctorPhone.setError("Required");
            result = false;
        } else {
            eTxtDoctorPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDoctorAddress.getText().toString())) {
            eTxtDoctorAddress.setError("Required");
            result = false;
        } else {
            eTxtDoctorAddress.setError(null);
        }
        return result;
    }
    @Override
    public void onClick(View v) {

        addDoctor();
    }
}
