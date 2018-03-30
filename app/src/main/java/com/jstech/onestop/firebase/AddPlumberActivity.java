package com.jstech.onestop.firebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.jstech.onestop.model.Plumber;

public class AddPlumberActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtPlumberName;
    EditText eTxtPlumberExperience;
    EditText eTxtPlumberEmail;
    EditText eTxtPlumberPhone;
    EditText eTxtPlumberAddress;
    Button btnAddPlum;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Plumber plumber = null;


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
        setContentView(R.layout.activity_add_plumber);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        plumber = new Plumber();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        eTxtPlumberAddress = (EditText)findViewById(R.id.editTextAddressPlum);
        eTxtPlumberName = (EditText)findViewById(R.id.editTextNamePlum);
        eTxtPlumberEmail = (EditText)findViewById(R.id.editTextEmailPlum);
        eTxtPlumberExperience = (EditText)findViewById(R.id.editTextExperiencePlum);
        eTxtPlumberPhone = (EditText)findViewById(R.id.editTextPhonePlum);
        btnAddPlum = (Button)findViewById(R.id.buttonAddPlum);
        btnAddPlum.setOnClickListener(this);

    }


    void addPlumber()
    {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtPlumberExperience.getText().toString();
        String phone = eTxtPlumberPhone.getText().toString();
        String email = eTxtPlumberEmail.getText().toString();
        String name = eTxtPlumberName.getText().toString();
        String address = eTxtPlumberAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ ".plumber@onestop.com";
        plumber.setPhone(phone);
        plumber.setEmail(email);
        plumber.setName(name);
        plumber.setAddress(address);
        plumber.setExperience(Double.parseDouble(experience));


        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), plumber);
                } else {
                    Toast.makeText(AddPlumberActivity.this, "Adding plumber failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Plumber plumber) {
        eTxtPlumberPhone.setText("");
        eTxtPlumberExperience.setText("");
        eTxtPlumberEmail.setText("");
        eTxtPlumberName.setText("");
        eTxtPlumberAddress.setText("");
        Toast.makeText(AddPlumberActivity.this, "New plumber's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewPlumber(fUser.getUid(),plumber.getName(), plumber.getExperience(), plumber.getEmail(), plumber.getPhone(), plumber.getAddress());
    }

    private void writeNewPlumber(String userId, String plumName, Double plumExperience, String plumEmail ,String plumPhone, String plumAddress) {
        Plumber plumber = new Plumber(plumName, plumExperience, plumAddress, plumPhone, plumEmail);
        mDatabase.child("Plumbers").child(userId).setValue(plumber);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtPlumberName.getText().toString())) {
            eTxtPlumberName.setError("Required");
            result = false;
        } else {
            eTxtPlumberName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPlumberEmail.getText().toString())) {
            eTxtPlumberEmail.setError("Required");
            result = false;
        } else {
            eTxtPlumberEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtPlumberExperience.getText().toString())) {
            eTxtPlumberExperience.setError("Required");
            result = false;
        } else {
            eTxtPlumberExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPlumberPhone.getText().toString())) {
            eTxtPlumberPhone.setError("Required");
            result = false;
        } else {
            eTxtPlumberPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPlumberAddress.getText().toString())) {
            eTxtPlumberAddress.setError("Required");
            result = false;
        } else {
            eTxtPlumberAddress.setError(null);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        addPlumber();
    }

}
