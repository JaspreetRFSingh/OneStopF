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
import android.util.Log;
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
import com.jstech.onestop.model.Plumber;

public class AddPlumberActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtPlumberName;
    EditText eTxtPlumberExperience;
    EditText eTxtPlumberEmail;
    EditText eTxtPlumberPhone;
    EditText eTxtPlumberAddress;
    Button btnAddPlum;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

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

        plumber = new Plumber();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationPlumber);

        eTxtPlumberAddress = (EditText)findViewById(R.id.editTextAddressPlum);
        eTxtPlumberName = (EditText)findViewById(R.id.editTextNamePlum);
        eTxtPlumberEmail = (EditText)findViewById(R.id.editTextEmailPlum);
        eTxtPlumberExperience = (EditText)findViewById(R.id.editTextExperiencePlum);
        eTxtPlumberPhone = (EditText)findViewById(R.id.editTextPhonePlum);
        btnAddPlum = (Button)findViewById(R.id.buttonAddPlum);
        btnAddPlum.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddPlumberActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddPlumberActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddPlumberActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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
        plumber.setLatitude(latFetch);
        plumber.setLongitude(longFetch);


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
        switchLocation.setText("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddPlumberActivity.this, "New plumber's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewPlumber(fUser.getUid(),plumber.getName(), plumber.getExperience(), plumber.getEmail(), plumber.getPhone(), plumber.getAddress(), plumber.getLatitude(), plumber.getLongitude());
    }

    private void writeNewPlumber(String userId, String plumName, Double plumExperience, String plumEmail ,String plumPhone, String plumAddress, double plumLat, double plumLon) {
        Plumber plumber = new Plumber(plumName, plumExperience, plumAddress, plumPhone, plumEmail, plumLat, plumLon);
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
