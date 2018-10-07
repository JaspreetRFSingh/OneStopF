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
import com.jstech.onestop.model.Electrician;

public class AddElectricianActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtElectricianName;
    EditText eTxtElectricianExperience;
    EditText eTxtElectricianEmail;
    EditText eTxtElectricianPhone;
    EditText eTxtElectricianAddress;
    Button btnAddElec;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationElectrician);

        eTxtElectricianAddress = (EditText)findViewById(R.id.editTextAddressElec);
        eTxtElectricianName = (EditText)findViewById(R.id.editTextNameElec);
        eTxtElectricianEmail = (EditText)findViewById(R.id.editTextEmailElec);
        eTxtElectricianExperience = (EditText)findViewById(R.id.editTextExperienceElec);
        eTxtElectricianPhone = (EditText)findViewById(R.id.editTextPhoneElec);
        btnAddElec = (Button)findViewById(R.id.buttonAddElec);
        btnAddElec.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddElectricianActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddElectricianActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddElectricianActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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
        electrician.setLatitude(latFetch);
        electrician.setLongitude(longFetch);


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
        switchLocation.setChecked(false);
        switchLocation.setText("Add Location");
        Toast.makeText(AddElectricianActivity.this, "New electrician's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewElectrician(fUser.getUid(),electrician.getName(), electrician.getExperience(), electrician.getEmail(), electrician.getPhone(), electrician.getAddress(), electrician.getLatitude(), electrician.getLongitude());
    }

    private void writeNewElectrician(String userId, String elecName, Double elecExperience, String elecEmail ,String elecPhone, String elecAddress, double elecLat, double elecLon) {
        Electrician electrician = new Electrician(elecName, elecExperience, elecAddress, elecPhone, elecEmail, elecLat, elecLon);
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
