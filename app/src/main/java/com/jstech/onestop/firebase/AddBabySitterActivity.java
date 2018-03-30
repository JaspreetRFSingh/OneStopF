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
import com.jstech.onestop.model.BabySitter;

public class AddBabySitterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtBabySitterName;
    EditText eTxtBabySitterExperience;
    EditText eTxtBabySitterEmail;
    EditText eTxtBabySitterPhone;
    EditText eTxtBabySitterAddress;
    Button btnAddBabySitter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    BabySitter babySitter = null;


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
        setContentView(R.layout.activity_add_baby_sitter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        babySitter = new BabySitter();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        eTxtBabySitterAddress = (EditText) findViewById(R.id.editTextAddressBabyS);
        eTxtBabySitterName = (EditText) findViewById(R.id.editTextNameBabyS);
        eTxtBabySitterEmail = (EditText) findViewById(R.id.editTextEmailBabyS);
        eTxtBabySitterExperience = (EditText) findViewById(R.id.editTextExperienceBabyS);
        eTxtBabySitterPhone = (EditText) findViewById(R.id.editTextPhoneBabyS);
        btnAddBabySitter = (Button) findViewById(R.id.buttonAddBabyS);
        btnAddBabySitter.setOnClickListener(this);

    }


    void addBabySitter() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtBabySitterExperience.getText().toString();
        String phone = eTxtBabySitterPhone.getText().toString();
        String email = eTxtBabySitterEmail.getText().toString();
        String name = eTxtBabySitterName.getText().toString();
        String address = eTxtBabySitterAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".babysitter@onestop.com";
        String password = "nobody";

        babySitter.setPhone(phone);
        babySitter.setEmail(email);
        babySitter.setName(name);
        babySitter.setAddress(address);
        babySitter.setExperience(Double.parseDouble(experience));


        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), babySitter);
                } else {
                    Toast.makeText(AddBabySitterActivity.this, "Adding baby-sitter failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, BabySitter babySitter) {
        eTxtBabySitterPhone.setText("");
        eTxtBabySitterExperience.setText("");
        eTxtBabySitterEmail.setText("");
        eTxtBabySitterName.setText("");
        eTxtBabySitterAddress.setText("");
        Toast.makeText(AddBabySitterActivity.this, "New baby-sitter's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewBabySitter(fUser.getUid(), babySitter.getName(), babySitter.getExperience(), babySitter.getEmail(), babySitter.getPhone(), babySitter.getAddress());
    }

    private void writeNewBabySitter(String userId, String babySitterName, Double babySitterExperience, String babySitterEmail, String babySitterPhone, String babySitterAddress) {
        BabySitter babySitter = new BabySitter(babySitterName, babySitterExperience, babySitterAddress, babySitterPhone, babySitterEmail);
        mDatabase.child("BabySitters").child(userId).setValue(babySitter);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtBabySitterName.getText().toString())) {
            eTxtBabySitterName.setError("Required");
            result = false;
        } else {
            eTxtBabySitterName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBabySitterEmail.getText().toString())) {
            eTxtBabySitterEmail.setError("Required");
            result = false;
        } else {
            eTxtBabySitterEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtBabySitterExperience.getText().toString())) {
            eTxtBabySitterExperience.setError("Required");
            result = false;
        } else {
            eTxtBabySitterExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBabySitterPhone.getText().toString())) {
            eTxtBabySitterPhone.setError("Required");
            result = false;
        } else {
            eTxtBabySitterPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBabySitterAddress.getText().toString())) {
            eTxtBabySitterAddress.setError("Required");
            result = false;
        } else {
            eTxtBabySitterAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addBabySitter();
    }
}
