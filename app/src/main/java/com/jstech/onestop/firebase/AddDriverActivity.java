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
import com.jstech.onestop.model.Driver;
import com.jstech.onestop.model.Electrician;

public class AddDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Driver driver = null;
    EditText eTxtDriverName;
    EditText eTxtDriverExperience;
    EditText eTxtDriverEmail;
    EditText eTxtDriverPhone;
    EditText eTxtDriverAddress;
    Button btnAddDriver;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;


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
        setContentView(R.layout.activity_add_driver);

        driver = new Driver();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationDriver);

        eTxtDriverAddress = (EditText) findViewById(R.id.editTextAddressDriv);
        eTxtDriverName = (EditText) findViewById(R.id.editTextNameDriv);
        eTxtDriverEmail = (EditText) findViewById(R.id.editTextEmailDriv);
        eTxtDriverExperience = (EditText) findViewById(R.id.editTextExperienceDriv);
        eTxtDriverPhone = (EditText) findViewById(R.id.editTextPhoneDriv);
        btnAddDriver = (Button) findViewById(R.id.buttonAddDriv);
        btnAddDriver.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddDriverActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddDriverActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddDriverActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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

    void addDriver()
    {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtDriverExperience.getText().toString();
        String phone = eTxtDriverPhone.getText().toString();
        String email = eTxtDriverEmail.getText().toString();
        String name = eTxtDriverName.getText().toString();
        String address = eTxtDriverAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ ".driver@onestop.com";

        driver.setPhone(phone);
        driver.setEmail(email);
        driver.setName(name);
        driver.setAddress(address);
        driver.setExperience(Double.parseDouble(experience));
        driver.setLatitude(latFetch);
        driver.setLongitude(longFetch);

        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), driver);
                } else {
                    Toast.makeText(AddDriverActivity.this, "Adding driver failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Driver driver) {
        eTxtDriverPhone.setText("");
        eTxtDriverExperience.setText("");
        eTxtDriverEmail.setText("");
        eTxtDriverName.setText("");
        eTxtDriverAddress.setText("");
        Toast.makeText(AddDriverActivity.this, "New driver's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewDriver(fUser.getUid(),driver.getName(), driver.getExperience(), driver.getEmail(), driver.getPhone(), driver.getAddress(), driver.getLatitude(), driver.getLongitude());
    }

    private void writeNewDriver(String userId, String drivName, Double drivExperience, String drivEmail ,String drivPhone, String drivAddress, double drivLat, double drivLon) {
        Driver driver = new Driver(drivName, drivExperience, drivAddress, drivPhone, drivEmail, drivLat, drivLon);
        mDatabase.child("Drivers").child(userId).setValue(driver);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtDriverName.getText().toString())) {
            eTxtDriverName.setError("Required");
            result = false;
        } else {
            eTxtDriverName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDriverEmail.getText().toString())) {
            eTxtDriverEmail.setError("Required");
            result = false;
        } else {
            eTxtDriverEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtDriverExperience.getText().toString())) {
            eTxtDriverExperience.setError("Required");
            result = false;
        } else {
            eTxtDriverExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDriverPhone.getText().toString())) {
            eTxtDriverPhone.setError("Required");
            result = false;
        } else {
            eTxtDriverPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtDriverAddress.getText().toString())) {
            eTxtDriverAddress.setError("Required");
            result = false;
        } else {
            eTxtDriverAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addDriver();
    }
}
