package com.jstech.onestop.firebase;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jstech.onestop.Manifest;
import com.jstech.onestop.R;
import com.jstech.onestop.model.Cook;
import com.jstech.onestop.model.Tailor;

public class AddCookActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxtCookName;
    EditText eTxtCookExperience;
    EditText eTxtCookEmail;
    EditText eTxtCookPhone;
    EditText eTxtCookAddress;
    Switch switchLocation;
    Button btnAddCook;

    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Cook cook = null;


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
        setContentView(R.layout.activity_add_cook);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cook = new Cook();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationCook);
        eTxtCookAddress = (EditText) findViewById(R.id.editTextAddressCook);
        eTxtCookName = (EditText) findViewById(R.id.editTextNameCook);
        eTxtCookEmail = (EditText) findViewById(R.id.editTextEmailCook);
        eTxtCookExperience = (EditText) findViewById(R.id.editTextExperienceCook);
        eTxtCookPhone = (EditText) findViewById(R.id.editTextPhoneCook);
        btnAddCook = (Button) findViewById(R.id.buttonAddCook);
        btnAddCook.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddCookActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddCookActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddCookActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    }
                }
            }
        });

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latFetch = location.getLatitude();
                longFetch = location.getLongitude();
                switchLocation.setText(latFetch+", "+longFetch);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


    }


    void addCook() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtCookExperience.getText().toString();
        String phone = eTxtCookPhone.getText().toString();
        String email = eTxtCookEmail.getText().toString();
        String name = eTxtCookName.getText().toString();
        String address = eTxtCookAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".cook@onestop.com";
        String password = "nobody";
        cook.setPhone(phone);
        cook.setEmail(email);
        cook.setName(name);
        cook.setAddress(address);
        cook.setLatitude(latFetch);
        cook.setLongitude(longFetch);
        cook.setExperience(Double.parseDouble(experience));

        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), cook);
                } else {
                    Toast.makeText(AddCookActivity.this, "Adding cook failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Cook cook) {
        eTxtCookPhone.setText("");
        eTxtCookExperience.setText("");
        eTxtCookEmail.setText("");
        eTxtCookName.setText("");
        eTxtCookAddress.setText("");
        switchLocation.setHint("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddCookActivity.this, "New cook's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewCook(fUser.getUid(), cook.getName(), cook.getExperience(), cook.getEmail(), cook.getPhone(), cook.getAddress(), cook.getLatitude(), cook.getLongitude());
    }

    private void writeNewCook(String userId, String cookName, Double cookExperience, String cookEmail, String cookPhone, String cookAddress, double cookLat, double cookLon) {
        Cook cook = new Cook(cookName, cookExperience, cookAddress, cookPhone, cookEmail, cookLat, cookLon);
        mDatabase.child("Cooks").child(userId).setValue(cook);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtCookName.getText().toString())) {
            eTxtCookName.setError("Required");
            result = false;
        } else {
            eTxtCookName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCookEmail.getText().toString())) {
            eTxtCookEmail.setError("Required");
            result = false;
        } else {
            eTxtCookEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtCookExperience.getText().toString())) {
            eTxtCookExperience.setError("Required");
            result = false;
        } else {
            eTxtCookExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCookPhone.getText().toString())) {
            eTxtCookPhone.setError("Required");
            result = false;
        } else {
            eTxtCookPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCookAddress.getText().toString())) {
            eTxtCookAddress.setError("Required");
            result = false;
        } else {
            eTxtCookAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addCook();
    }
}