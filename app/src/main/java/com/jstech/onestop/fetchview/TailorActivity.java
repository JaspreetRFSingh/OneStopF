package com.jstech.onestop.fetchview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jstech.onestop.R;
import com.jstech.onestop.controller.MyDistance;
import com.jstech.onestop.controller.viewholder.ViewHolderTailor;
import com.jstech.onestop.model.Tailor;


public class TailorActivity extends AppCompatActivity {
    DatabaseReference dbRef;
    Query qryTailor;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Tailor, ViewHolderTailor> adapterTailor;
    RecyclerView recyclerViewTailor;
    Context mContext;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;
    ProgressBar progressBar;
    MyDistance myDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor);
        mContext = this;
        progressBar = findViewById(R.id.progressBarTailor);
        progressBar.setVisibility(View.VISIBLE);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Tailors");
        setSupportActionBar(myToolbar);
        recyclerViewTailor = (RecyclerView) findViewById(R.id.recyclerViewTailor);
        recyclerViewTailor.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Tailors");
        mManager = new LinearLayoutManager(TailorActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewTailor.setLayoutManager(mManager);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if((int)dataSnapshot.getChildrenCount()==0)
                {
                    Toast.makeText(mContext, "No results found!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(mContext, String.valueOf(dataSnapshot.getChildrenCount()) + " results found!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latFetch = location.getLatitude();
                longFetch = location.getLongitude();
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
        if (ActivityCompat.checkSelfPermission(TailorActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TailorActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(TailorActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        qryTailor = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Tailor>()
                .setQuery(qryTailor, Tailor.class)
                .build();

        adapterTailor = new FirebaseRecyclerAdapter<Tailor, ViewHolderTailor>(options) {

            @Override
            public ViewHolderTailor onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderTailor(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }

            float ratTemp;
            @Override
            protected void onBindViewHolder(ViewHolderTailor viewHolder, int position, final Tailor model) {
                myDistance = new MyDistance(latFetch, longFetch, model.getLatitude(), model.getLongitude());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle(model.getName());
                        builder.setMessage(model.toString());
                        builder.setPositiveButton("Review " + model.getName(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog dialogRev;
                                TextView textViewR;
                                RatingBar ratingBarUser;
                                Button btnSubmitReview;
                                dialogRev = new Dialog(mContext);
                                dialogRev.setContentView(R.layout.dialog_review);
                                textViewR = (TextView) dialogRev.findViewById(R.id.text_view_dialog);
                                ratingBarUser = (RatingBar) dialogRev.findViewById(R.id.ratingBarUser);
                                btnSubmitReview = (Button)dialogRev.findViewById(R.id.submitReview);
                                textViewR.setText("Please give ratings for "+model.getName()+"!");
                                ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                        ratTemp = rating;
                                    }
                                });
                                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogRev.dismiss();
                                        Toast.makeText(mContext, "You rated "+model.getName()+" with "+ratTemp +" stars!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogRev.show();
                            }


                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
                viewHolder.bindToTailor(model, mContext, myDistance.getDistance());
            }

        };
        recyclerViewTailor.setAdapter(adapterTailor);
        recyclerViewTailor.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));

    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterTailor != null) {
            adapterTailor.startListening();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (adapterTailor != null) {
            adapterTailor.stopListening();
        }

    }
}