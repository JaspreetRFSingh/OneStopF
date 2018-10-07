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
import com.jstech.onestop.model.Painter;

public class AddPainterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText eTxtPainterName;
    EditText eTxtPainterExperience;
    EditText eTxtPainterEmail;
    EditText eTxtPainterPhone;
    EditText eTxtPainterAddress;
    Button btnAddPainter;

    Switch switchLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Painter painter = null;


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
        setContentView(R.layout.activity_add_painter);


        painter = new Painter();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        switchLocation = (Switch) findViewById(R.id.addLocationPainter);

        eTxtPainterAddress = (EditText) findViewById(R.id.editTextAddressPaint);
        eTxtPainterName = (EditText) findViewById(R.id.editTextNamePaint);
        eTxtPainterEmail = (EditText) findViewById(R.id.editTextEmailPaint);
        eTxtPainterExperience = (EditText) findViewById(R.id.editTextExperiencePaint);
        eTxtPainterPhone = (EditText) findViewById(R.id.editTextPhonePaint);
        btnAddPainter = (Button) findViewById(R.id.buttonAddPaint);
        btnAddPainter.setOnClickListener(this);

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ActivityCompat.checkSelfPermission(AddPainterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddPainterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(AddPainterActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
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

    void addPainter() {
        if (!validateEntries()) {
            return;
        }
        showProgressDialog();
        String experience = eTxtPainterExperience.getText().toString();
        String phone = eTxtPainterPhone.getText().toString();
        String email = eTxtPainterEmail.getText().toString();
        String name = eTxtPainterName.getText().toString();
        String address = eTxtPainterAddress.getText().toString();
        String emailf = name.substring(0,name.indexOf(" "))+ ".painter@onestop.com";
        String password = "nobody";

        painter.setPhone(phone);
        painter.setEmail(email);
        painter.setName(name);
        painter.setAddress(address);
        painter.setExperience(Double.parseDouble(experience));
        painter.setLatitude(latFetch);
        painter.setLongitude(longFetch);


        mAuth.createUserWithEmailAndPassword(emailf, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser(), painter);
                } else {
                    Toast.makeText(AddPainterActivity.this, "Adding painter failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void onAuthSuccess(FirebaseUser fUser, Painter painter) {
        eTxtPainterPhone.setText("");
        eTxtPainterExperience.setText("");
        eTxtPainterEmail.setText("");
        eTxtPainterName.setText("");
        eTxtPainterAddress.setText("");
        switchLocation.setChecked(false);
        switchLocation.setText("Add Location");
        Toast.makeText(AddPainterActivity.this, "New painter's entry has been added!", Toast.LENGTH_LONG).show();
        writeNewPainter(fUser.getUid(), painter.getName(), painter.getExperience(), painter.getEmail(), painter.getPhone(), painter.getAddress(), painter.getLatitude(), painter.getLongitude());
    }

    private void writeNewPainter(String userId, String painterName, Double painterExperience, String painterEmail, String painterPhone, String painterAddress, double painterLat, double painterLon) {
        Painter painter = new Painter(painterName, painterExperience, painterAddress, painterPhone, painterEmail, painterLat, painterLon);
        mDatabase.child("Painters").child(userId).setValue(painter);
    }

    private boolean validateEntries() {
        boolean result = true;
        if (TextUtils.isEmpty(eTxtPainterName.getText().toString())) {
            eTxtPainterName.setError("Required");
            result = false;
        } else {
            eTxtPainterName.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPainterEmail.getText().toString())) {
            eTxtPainterEmail.setError("Required");
            result = false;
        } else {
            eTxtPainterEmail.setError(null);
        }

        if (TextUtils.isEmpty(eTxtPainterExperience.getText().toString())) {
            eTxtPainterExperience.setError("Required");
            result = false;
        } else {
            eTxtPainterExperience.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPainterPhone.getText().toString())) {
            eTxtPainterPhone.setError("Required");
            result = false;
        } else {
            eTxtPainterPhone.setError(null);
        }
        if (TextUtils.isEmpty(eTxtPainterAddress.getText().toString())) {
            eTxtPainterAddress.setError("Required");
            result = false;
        } else {
            eTxtPainterAddress.setError(null);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        addPainter();
    }
}
