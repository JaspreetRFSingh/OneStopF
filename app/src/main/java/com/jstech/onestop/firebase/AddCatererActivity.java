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
import com.jstech.onestop.model.Caterer;

public class AddCatererActivity extends AppCompatActivity implements View.OnClickListener {


    EditText eTxtCatererName;
    EditText eTxtCatererExperience;
    EditText eTxtCatererEmail;
    EditText eTxtCatererPhone;
    EditText eTxtCatererAddress;
    Button btnAddCaterer;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Caterer caterer = null;


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
        setContentView(R.layout.activity_add_caterer);

        /*mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();*/

        caterer = new Caterer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationCaterer);

        eTxtCatererAddress = (EditText) findViewById(R.id.editTextAddressCate);
        eTxtCatererName = (EditText) findViewById(R.id.editTextNameCate);
        eTxtCatererEmail = (EditText) findViewById(R.id.editTextEmailCate);
        eTxtCatererExperience = (EditText) findViewById(R.id.editTextExperienceCate);
        eTxtCatererPhone = (EditText) findViewById(R.id.editTextPhoneCate);
        btnAddCaterer = (Button) findViewById(R.id.buttonAddCate);
        btnAddCaterer.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddCatererActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddCatererActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddCatererActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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

    void addCaterer() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtCatererExperience.getText().toString();
        String phone = eTxtCatererPhone.getText().toString();
        String email = eTxtCatererEmail.getText().toString();
        String name = eTxtCatererName.getText().toString();
        String address = eTxtCatererAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".caterer@onestop.com";
        String password = "nobody";

        caterer.setPhone(phone);
        caterer.setEmail(email);
        caterer.setName(name);
        caterer.setAddress(address);
        caterer.setExperience(Double.parseDouble(experience));
        caterer.setLatitude(latFetch);
        caterer.setLongitude(longFetch);


        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), caterer);
                } else {
                    Toast.makeText(AddCatererActivity.this, "Adding caterer failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Caterer caterer) {
        eTxtCatererPhone.setText("");
        eTxtCatererExperience.setText("");
        eTxtCatererEmail.setText("");
        eTxtCatererName.setText("");
        eTxtCatererAddress.setText("");
        switchLocation.setHint("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddCatererActivity.this, "New caterer's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewCaterer(fUser.getUid(), caterer.getName(), caterer.getExperience(), caterer.getEmail(), caterer.getPhone(), caterer.getAddress(), caterer.getLatitude(), caterer.getLongitude());
    }

    private void writeNewCaterer(String userId, String catererName, Double catererExperience, String catererEmail, String catererPhone, String catererAddress, double catererLat, double catererLon) {
        Caterer caterer = new Caterer(catererName, catererExperience, catererAddress, catererPhone, catererEmail, catererLat, catererLon);
        mDatabase.child("Caterers").child(userId).setValue(caterer);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtCatererName.getText().toString())) {
            eTxtCatererName.setError("Required");
            result = false;
        } else {
            eTxtCatererName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCatererEmail.getText().toString())) {
            eTxtCatererEmail.setError("Required");
            result = false;
        } else {
            eTxtCatererEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtCatererExperience.getText().toString())) {
            eTxtCatererExperience.setError("Required");
            result = false;
        } else {
            eTxtCatererExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCatererPhone.getText().toString())) {
            eTxtCatererPhone.setError("Required");
            result = false;
        } else {
            eTxtCatererPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCatererAddress.getText().toString())) {
            eTxtCatererAddress.setError("Required");
            result = false;
        } else {
            eTxtCatererAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addCaterer();
    }

}
