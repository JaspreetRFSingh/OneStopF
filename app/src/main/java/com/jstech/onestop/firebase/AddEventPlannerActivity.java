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
import com.jstech.onestop.model.EventPlanner;

public class AddEventPlannerActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxtEPlannerName;
    EditText eTxtEPlannerExperience;
    EditText eTxtEPlannerEmail;
    EditText eTxtEPlannerPhone;
    EditText eTxtEPlannerAddress;
    Button btnAddEPlanner;


    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    EventPlanner eventPlanner = null;
    String eventTypeRcv;


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
        setContentView(R.layout.activity_add_event_planner);

        eventPlanner = new EventPlanner();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        eventTypeRcv = getIntent().getExtras().getString("keyEventType");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationEventPlanner);

        eTxtEPlannerAddress = (EditText)findViewById(R.id.editTextAddressEvenPl);
        eTxtEPlannerName = (EditText)findViewById(R.id.editTextNameEvenPl);
        eTxtEPlannerEmail = (EditText)findViewById(R.id.editTextEmailEvenPl);
        eTxtEPlannerExperience = (EditText)findViewById(R.id.editTextExperienceEvenPl);
        eTxtEPlannerPhone = (EditText)findViewById(R.id.editTextPhoneEvenPl);
        btnAddEPlanner = (Button)findViewById(R.id.buttonAddEvenPl);
        btnAddEPlanner.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddEventPlannerActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddEventPlannerActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddEventPlannerActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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

    void addEventPlanner()
    {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtEPlannerExperience.getText().toString();
        String phone = eTxtEPlannerPhone.getText().toString();
        String email = eTxtEPlannerEmail.getText().toString();
        String name = eTxtEPlannerName.getText().toString();
        String address = eTxtEPlannerAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ "."+eventTypeRcv +".eventplanner@onestop.com";
        eventPlanner.setPhone(phone);
        eventPlanner.setEmail(email);
        eventPlanner.setName(name);
        eventPlanner.setAddress(address);
        eventPlanner.setExperience(Double.parseDouble(experience));
        eventPlanner.setLatitude(latFetch);
        eventPlanner.setLongitude(longFetch);


        mAuth.createUserWithEmailAndPassword(emailf,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), eventPlanner);
                } else {
                    Toast.makeText(AddEventPlannerActivity.this, "Adding event-planner failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, EventPlanner eventPlanner) {
        eTxtEPlannerPhone.setText("");
        eTxtEPlannerExperience.setText("");
        eTxtEPlannerAddress.setText("");
        eTxtEPlannerName.setText("");
        eTxtEPlannerEmail.setText("");
        switchLocation.setChecked(false);
        switchLocation.setText("Add Location");
        Toast.makeText(AddEventPlannerActivity.this, "New event-planner's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewEventPlanner(fUser.getUid(),eventPlanner.getName(), eventPlanner.getExperience(), eventPlanner.getEmail(), eventPlanner.getPhone(), eventPlanner.getAddress(), eventPlanner.getLatitude(), eventPlanner.getLongitude());
    }

    private void writeNewEventPlanner(String userId, String ePlName, Double ePlExperience, String ePlEmail ,String ePlPhone, String ePlAddress, double ePlLat, double ePlLon) {
        EventPlanner eventPlanner = new EventPlanner(ePlName, ePlExperience, ePlAddress, ePlPhone, ePlEmail, ePlLat, ePlLon);
        mDatabase.child("EventPlanners").child(eventTypeRcv).child(userId).setValue(eventPlanner);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtEPlannerName.getText().toString())) {
            eTxtEPlannerName.setError("Required");
            result = false;
        } else {
            eTxtEPlannerName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtEPlannerEmail.getText().toString())) {
            eTxtEPlannerEmail.setError("Required");
            result = false;
        } else {
            eTxtEPlannerEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtEPlannerExperience.getText().toString())) {
            eTxtEPlannerExperience.setError("Required");
            result = false;
        } else {
            eTxtEPlannerExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtEPlannerPhone.getText().toString())) {
            eTxtEPlannerPhone.setError("Required");
            result = false;
        } else {
            eTxtEPlannerPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtEPlannerAddress.getText().toString())) {
            eTxtEPlannerAddress.setError("Required");
            result = false;
        } else {
            eTxtEPlannerAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
     addEventPlanner();
    }
}