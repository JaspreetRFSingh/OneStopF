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
import com.jstech.onestop.model.Carpenter;

public class AddCarpenterActivity extends AppCompatActivity implements View.OnClickListener{


    EditText eTxtCarpenterName;
    EditText eTxtCarpenterExperience;
    EditText eTxtCarpenterEmail;
    EditText eTxtCarpenterPhone;
    EditText eTxtCarpenterAddress;
    Button btnAddCarpenter;


    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Carpenter carpenter = null;


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
        setContentView(R.layout.activity_add_carpenter);
        carpenter = new Carpenter();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationCarpenter);

        eTxtCarpenterAddress = (EditText) findViewById(R.id.editTextAddressCarp);
        eTxtCarpenterName = (EditText) findViewById(R.id.editTextNameCarp);
        eTxtCarpenterEmail = (EditText) findViewById(R.id.editTextEmailCarp);
        eTxtCarpenterExperience = (EditText) findViewById(R.id.editTextExperienceCarp);
        eTxtCarpenterPhone = (EditText) findViewById(R.id.editTextPhoneCarp);
        btnAddCarpenter = (Button) findViewById(R.id.buttonAddCarp);
        btnAddCarpenter.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddCarpenterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddCarpenterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddCarpenterActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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

    void addCarpenter() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtCarpenterExperience.getText().toString();
        String phone = eTxtCarpenterPhone.getText().toString();
        String email = eTxtCarpenterEmail.getText().toString();
        String name = eTxtCarpenterName.getText().toString();
        String address = eTxtCarpenterAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".carpenter@onestop.com";
        String password = "nobody";

        carpenter.setPhone(phone);
        carpenter.setEmail(email);
        carpenter.setName(name);
        carpenter.setAddress(address);
        carpenter.setExperience(Double.parseDouble(experience));
        carpenter.setLatitude(latFetch);
        carpenter.setLongitude(longFetch);


        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), carpenter);
                } else {
                    Toast.makeText(AddCarpenterActivity.this, "Adding carpenter failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Carpenter carpenter) {
        eTxtCarpenterPhone.setText("");
        eTxtCarpenterExperience.setText("");
        eTxtCarpenterEmail.setText("");
        eTxtCarpenterName.setText("");
        eTxtCarpenterAddress.setText("");
        switchLocation.setHint("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddCarpenterActivity.this, "New carpenter's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewCarpenter(fUser.getUid(), carpenter.getName(), carpenter.getExperience(), carpenter.getEmail(), carpenter.getPhone(), carpenter.getAddress(), carpenter.getLatitude(), carpenter.getLongitude());
    }

    private void writeNewCarpenter(String userId, String carpenterName, Double carpenterExperience, String carpenterEmail, String carpenterPhone, String carpenterAddress, double carpenterLat, double carpenterLon) {
        Carpenter carpenter = new Carpenter(carpenterName, carpenterExperience, carpenterAddress, carpenterPhone, carpenterEmail, carpenterLat, carpenterLon);
        mDatabase.child("Carpenters").child(userId).setValue(carpenter);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtCarpenterName.getText().toString())) {
            eTxtCarpenterName.setError("Required");
            result = false;
        } else {
            eTxtCarpenterName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCarpenterEmail.getText().toString())) {
            eTxtCarpenterEmail.setError("Required");
            result = false;
        } else {
            eTxtCarpenterEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtCarpenterExperience.getText().toString())) {
            eTxtCarpenterExperience.setError("Required");
            result = false;
        } else {
            eTxtCarpenterExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCarpenterPhone.getText().toString())) {
            eTxtCarpenterPhone.setError("Required");
            result = false;
        } else {
            eTxtCarpenterPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtCarpenterAddress.getText().toString())) {
            eTxtCarpenterAddress.setError("Required");
            result = false;
        } else {
            eTxtCarpenterAddress.setError(null);
        }
        return result;
    }
    @Override
    public void onClick(View v) {

        addCarpenter();
    }
}
