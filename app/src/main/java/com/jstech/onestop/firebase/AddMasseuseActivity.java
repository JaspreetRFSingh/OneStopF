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
import com.jstech.onestop.model.Masseuse;

public class AddMasseuseActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxtMasseuseName;
    EditText eTxtMasseuseExperience;
    EditText eTxtMasseuseEmail;
    EditText eTxtMasseusePhone;
    EditText eTxtMasseuseAddress;
    Switch switchLocation;
    Button btnAddMasseuse;

    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Masseuse masseuse= null;


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
        setContentView(R.layout.activity_add_masseuse);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        masseuse = new Masseuse();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationMass);
        eTxtMasseuseAddress = (EditText) findViewById(R.id.editTextAddressMass);
        eTxtMasseuseName = (EditText) findViewById(R.id.editTextNameMass);
        eTxtMasseuseEmail = (EditText) findViewById(R.id.editTextEmailMass);
        eTxtMasseuseExperience = (EditText) findViewById(R.id.editTextExperienceMass);
        eTxtMasseusePhone = (EditText) findViewById(R.id.editTextPhoneMass);
        btnAddMasseuse = (Button) findViewById(R.id.buttonAddMass);
        btnAddMasseuse.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddMasseuseActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddMasseuseActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddMasseuseActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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


    void addMasseuse() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtMasseuseExperience.getText().toString();
        String phone = eTxtMasseusePhone.getText().toString();
        String email = eTxtMasseuseEmail.getText().toString();
        String name = eTxtMasseuseName.getText().toString();
        String address = eTxtMasseuseAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".masseuse@onestop.com";
        String password = "nobody";
        masseuse.setPhone(phone);
        masseuse.setEmail(email);
        masseuse.setName(name);
        masseuse.setAddress(address);
        masseuse.setLatitude(latFetch);
        masseuse.setLongitude(longFetch);
        masseuse.setExperience(Double.parseDouble(experience));

        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), masseuse);
                } else {
                    Toast.makeText(AddMasseuseActivity.this, "Adding masseuse failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Masseuse masseuse) {
        eTxtMasseusePhone.setText("");
        eTxtMasseuseExperience.setText("");
        eTxtMasseuseEmail.setText("");
        eTxtMasseuseName.setText("");
        eTxtMasseuseAddress.setText("");
        switchLocation.setHint("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddMasseuseActivity.this, "New masseuse's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewMasseuse(fUser.getUid(), masseuse.getName(), masseuse.getExperience(), masseuse.getEmail(), masseuse.getPhone(), masseuse.getAddress(), masseuse.getLatitude(), masseuse.getLongitude());
    }

    private void writeNewMasseuse(String userId, String masseuseName, Double masseuseExperience, String masseuseEmail, String masseusePhone, String masseuseAddress, double masseuseLat, double masseuseLon) {
        Masseuse masseuse = new Masseuse(masseuseName, masseuseExperience, masseuseAddress, masseusePhone, masseuseEmail, masseuseLat, masseuseLon);
        mDatabase.child("Masseuses").child(userId).setValue(masseuse);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtMasseuseName.getText().toString())) {
            eTxtMasseuseName.setError("Required");
            result = false;
        } else {
            eTxtMasseuseName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtMasseuseEmail.getText().toString())) {
            eTxtMasseuseEmail.setError("Required");
            result = false;
        } else {
            eTxtMasseuseEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtMasseuseExperience.getText().toString())) {
            eTxtMasseuseExperience.setError("Required");
            result = false;
        } else {
            eTxtMasseuseExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtMasseusePhone.getText().toString())) {
            eTxtMasseusePhone.setError("Required");
            result = false;
        } else {
            eTxtMasseusePhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtMasseuseAddress.getText().toString())) {
            eTxtMasseuseAddress.setError("Required");
            result = false;
        } else {
            eTxtMasseuseAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addMasseuse();
    }
}
