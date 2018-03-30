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
import com.jstech.onestop.model.Electrician;

public class AddElectricianActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtElectricianName;
    EditText eTxtElectricianExperience;
    EditText eTxtElectricianEmail;
    EditText eTxtElectricianPhone;
    EditText eTxtElectricianAddress;
    Button btnAddElec;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Electrician electrician = null;
    String elecTypeRcv;


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



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_electrician);

        electrician = new Electrician();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        elecTypeRcv = getIntent().getExtras().getString("keyElecType");

        eTxtElectricianAddress = (EditText)findViewById(R.id.editTextAddressElec);
        eTxtElectricianName = (EditText)findViewById(R.id.editTextNameElec);
        eTxtElectricianEmail = (EditText)findViewById(R.id.editTextEmailElec);
        eTxtElectricianExperience = (EditText)findViewById(R.id.editTextExperienceElec);
        eTxtElectricianPhone = (EditText)findViewById(R.id.editTextPhoneElec);
        btnAddElec = (Button)findViewById(R.id.buttonAddElec);
        btnAddElec.setOnClickListener(this);

    }

    void addElectrician()
    {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtElectricianExperience.getText().toString();
        String phone = eTxtElectricianPhone.getText().toString();
        String email = eTxtElectricianEmail.getText().toString();
        String name = eTxtElectricianName.getText().toString();
        String address = eTxtElectricianAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ "."+elecTypeRcv +".electrician@onestop.com";
        electrician.setPhone(phone);
        electrician.setEmail(email);
        electrician.setName(name);
        electrician.setAddress(address);
        electrician.setExperience(Double.parseDouble(experience));


        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), electrician);
                } else {
                    Toast.makeText(AddElectricianActivity.this, "Adding electrician failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Electrician electrician) {
        eTxtElectricianPhone.setText("");
        eTxtElectricianExperience.setText("");
        eTxtElectricianEmail.setText("");
        eTxtElectricianName.setText("");
        eTxtElectricianAddress.setText("");
        Toast.makeText(AddElectricianActivity.this, "New electrician's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewElectrician(fUser.getUid(),electrician.getName(), electrician.getExperience(), electrician.getEmail(), electrician.getPhone(), electrician.getAddress());
    }

    private void writeNewElectrician(String userId, String elecName, Double elecExperience, String elecEmail ,String elecPhone, String elecAddress) {
        Electrician electrician = new Electrician(elecName, elecExperience, elecAddress, elecPhone, elecEmail);
        mDatabase.child("Electricians").child(elecTypeRcv).child(userId).setValue(electrician);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtElectricianName.getText().toString())) {
            eTxtElectricianName.setError("Required");
            result = false;
        } else {
            eTxtElectricianName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtElectricianEmail.getText().toString())) {
            eTxtElectricianEmail.setError("Required");
            result = false;
        } else {
            eTxtElectricianEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtElectricianExperience.getText().toString())) {
            eTxtElectricianExperience.setError("Required");
            result = false;
        } else {
            eTxtElectricianExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtElectricianPhone.getText().toString())) {
            eTxtElectricianPhone.setError("Required");
            result = false;
        } else {
            eTxtElectricianPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtElectricianAddress.getText().toString())) {
            eTxtElectricianAddress.setError("Required");
            result = false;
        } else {
            eTxtElectricianAddress.setError(null);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        addElectrician();
    }
}
