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
import com.jstech.onestop.R;
import com.jstech.onestop.model.Doctor;

public class AddDoctorActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtDoctorName;
    EditText eTxtDoctorExperience;
    EditText eTxtDoctorEmail;
    EditText eTxtDoctorPhone;
    EditText eTxtDoctorAddress;
    Button btnAddDoct;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationDoctor);

        eTxtDoctorAddress = (EditText)findViewById(R.id.editTextAddressDoct);
        eTxtDoctorName = (EditText)findViewById(R.id.editTextNameDoct);
        eTxtDoctorEmail = (EditText)findViewById(R.id.editTextEmailDoct);
        eTxtDoctorExperience = (EditText)findViewById(R.id.editTextExperienceDoct);
        eTxtDoctorPhone = (EditText)findViewById(R.id.editTextPhoneDoct);
        btnAddDoct = (Button)findViewById(R.id.buttonAddDoct);
        btnAddDoct.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddDoctorActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddDoctorActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddDoctorActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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
        doctor.setLatitude(latFetch);
        doctor.setLongitude(longFetch);


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
        switchLocation.setChecked(false);
        switchLocation.setText("Add Location");
        Toast.makeText(AddDoctorActivity.this, "New doctor's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewDoctor(fUser.getUid(),doctor.getName(), doctor.getExperience(), doctor.getEmail(), doctor.getPhone(), doctor.getAddress(), doctor.getLatitude(), doctor.getLongitude());
    }

    private void writeNewDoctor(String userId, String doctName, Double doctExperience, String doctEmail ,String doctPhone, String doctAddress, double doctLat, double doctLon) {
        Doctor doctor = new Doctor(doctName, doctExperience, doctAddress, doctPhone, doctEmail, doctLat, doctLon);
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
