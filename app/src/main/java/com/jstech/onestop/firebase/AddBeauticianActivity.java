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
import com.jstech.onestop.model.Beautician;

public class AddBeauticianActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtBeauticianName;
    EditText eTxtBeauticianExperience;
    EditText eTxtBeauticianEmail;
    EditText eTxtBeauticianPhone;
    EditText eTxtBeauticianAddress;
    Switch switchLocation;
    Button btnAddBeautician;

    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Beautician beautician = null;


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
        setContentView(R.layout.activity_add_beautician);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        beautician = new Beautician();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationBeauty);
        eTxtBeauticianAddress = (EditText) findViewById(R.id.editTextAddressBeauty);
        eTxtBeauticianName = (EditText) findViewById(R.id.editTextNameBeauty);
        eTxtBeauticianEmail = (EditText) findViewById(R.id.editTextEmailBeauty);
        eTxtBeauticianExperience = (EditText) findViewById(R.id.editTextExperienceBeauty);
        eTxtBeauticianPhone = (EditText) findViewById(R.id.editTextPhoneBeauty);
        btnAddBeautician = (Button) findViewById(R.id.buttonAddBeauty);
        btnAddBeautician.setOnClickListener(this);
        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddBeauticianActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddBeauticianActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddBeauticianActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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


    void addBeautician() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtBeauticianExperience.getText().toString();
        String phone = eTxtBeauticianPhone.getText().toString();
        String email = eTxtBeauticianEmail.getText().toString();
        String name = eTxtBeauticianName.getText().toString();
        String address = eTxtBeauticianAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".beautician@onestop.com";
        String password = "nobody";
        beautician.setPhone(phone);
        beautician.setEmail(email);
        beautician.setName(name);
        beautician.setAddress(address);
        beautician.setLatitude(latFetch);
        beautician.setLongitude(longFetch);
        beautician.setExperience(Double.parseDouble(experience));

        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), beautician);
                } else {
                    Toast.makeText(AddBeauticianActivity.this, "Adding beautician failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Beautician beautician) {
        eTxtBeauticianPhone.setText("");
        eTxtBeauticianExperience.setText("");
        eTxtBeauticianEmail.setText("");
        eTxtBeauticianName.setText("");
        eTxtBeauticianAddress.setText("");
        switchLocation.setHint("Add Location");
        switchLocation.setChecked(false);
        Toast.makeText(AddBeauticianActivity.this, "New beautician's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewBeautician(fUser.getUid(), beautician.getName(), beautician.getExperience(), beautician.getEmail(), beautician.getPhone(), beautician.getAddress(), beautician.getLatitude(), beautician.getLongitude());
    }

    private void writeNewBeautician(String userId, String beauName, Double beauExperience, String beauEmail, String beauPhone, String beauAddress, double beauLat, double beauLon) {
        Beautician beautician = new Beautician(beauName, beauExperience, beauAddress, beauPhone, beauEmail, beauLat, beauLon);
        mDatabase.child("Beauticians").child(userId).setValue(beautician);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtBeauticianName.getText().toString())) {
            eTxtBeauticianName.setError("Required");
            result = false;
        } else {
            eTxtBeauticianName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBeauticianEmail.getText().toString())) {
            eTxtBeauticianEmail.setError("Required");
            result = false;
        } else {
            eTxtBeauticianEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtBeauticianExperience.getText().toString())) {
            eTxtBeauticianExperience.setError("Required");
            result = false;
        } else {
            eTxtBeauticianExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBeauticianPhone.getText().toString())) {
            eTxtBeauticianPhone.setError("Required");
            result = false;
        } else {
            eTxtBeauticianPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtBeauticianAddress.getText().toString())) {
            eTxtBeauticianAddress.setError("Required");
            result = false;
        } else {
            eTxtBeauticianAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addBeautician();
    }
}
