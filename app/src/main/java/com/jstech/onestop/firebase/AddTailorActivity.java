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
import com.jstech.onestop.model.Tailor;

public class AddTailorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxtTailorName;
    EditText eTxtTailorExperience;
    EditText eTxtTailorEmail;
    EditText eTxtTailorPhone;
    EditText eTxtTailorAddress;
    Button btnAddTailor;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Tailor tailor = null;


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
        setContentView(R.layout.activity_add_tailor);

        tailor = new Tailor();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationTailor);

        eTxtTailorAddress = (EditText) findViewById(R.id.editTextAddressTail);
        eTxtTailorName = (EditText) findViewById(R.id.editTextNameTail);
        eTxtTailorEmail = (EditText) findViewById(R.id.editTextEmailTail);
        eTxtTailorExperience = (EditText) findViewById(R.id.editTextExperienceTail);
        eTxtTailorPhone = (EditText) findViewById(R.id.editTextPhoneTail);
        btnAddTailor = (Button) findViewById(R.id.buttonAddTail);
        btnAddTailor.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddTailorActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddTailorActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddTailorActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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


    void addTailor() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtTailorExperience.getText().toString();
        String phone = eTxtTailorPhone.getText().toString();
        String email = eTxtTailorEmail.getText().toString();
        String name = eTxtTailorName.getText().toString();
        String address = eTxtTailorAddress.getText().toString();
        String password = "nobody";
        String emailf = name.substring(0,name.indexOf(" "))+ ".tailor@onestop.com";
        tailor.setPhone(phone);
        tailor.setEmail(email);
        tailor.setName(name);
        tailor.setAddress(address);
        tailor.setExperience(Double.parseDouble(experience));
        tailor.setLatitude(latFetch);
        tailor.setLongitude(longFetch);


        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), tailor);
                } else {
                    Toast.makeText(AddTailorActivity.this, "Adding tailor failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Tailor tailor) {
        eTxtTailorPhone.setText("");
        eTxtTailorExperience.setText("");
        eTxtTailorEmail.setText("");
        eTxtTailorName.setText("");
        eTxtTailorAddress.setText("");
        switchLocation.setChecked(false);
        switchLocation.setText("Add Location");
        Toast.makeText(AddTailorActivity.this, "New tailor's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewTailor(fUser.getUid(), tailor.getName(), tailor.getExperience(), tailor.getEmail(), tailor.getPhone(), tailor.getAddress(), tailor.getLatitude(), tailor.getLongitude());
    }

    private void writeNewTailor(String userId, String tailName, Double tailExperience, String tailEmail, String tailPhone, String tailAddress, double tailLat, double tailLon) {
        Tailor tailor = new Tailor(tailName, tailExperience, tailAddress, tailPhone, tailEmail, tailLat, tailLon);
        mDatabase.child("Tailors").child(userId).setValue(tailor);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtTailorName.getText().toString())) {
            eTxtTailorName.setError("Required");
            result = false;
        } else {
            eTxtTailorName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTailorEmail.getText().toString())) {
            eTxtTailorEmail.setError("Required");
            result = false;
        } else {
            eTxtTailorEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtTailorExperience.getText().toString())) {
            eTxtTailorExperience.setError("Required");
            result = false;
        } else {
            eTxtTailorExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTailorPhone.getText().toString())) {
            eTxtTailorPhone.setError("Required");
            result = false;
        } else {
            eTxtTailorPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtTailorAddress.getText().toString())) {
            eTxtTailorAddress.setError("Required");
            result = false;
        } else {
            eTxtTailorAddress.setError(null);
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        addTailor();
    }
}