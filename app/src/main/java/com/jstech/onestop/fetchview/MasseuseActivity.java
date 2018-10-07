package com.jstech.onestop.fetchview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.jstech.onestop.controller.viewholder.ViewHolderMasseuse;
import com.jstech.onestop.model.Masseuse;

public class MasseuseActivity extends AppCompatActivity {

    DatabaseReference dbRef;
    Query qryMasseuses;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<Masseuse, ViewHolderMasseuse> adapterMasseuse;
    RecyclerView recyclerViewMasseuse;

    ProgressBar progressBar;
    Context mContext;
    LocationManager locationManager;
    LocationListener locationListener;
    double latFetch;
    double longFetch;

    MyDistance myDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masseuse);
        mContext = this;
        progressBar = findViewById(R.id.progressBarMasseuse);
        progressBar.setVisibility(View.VISIBLE);
        android.support.v7.widget.Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Masseuses");
        setSupportActionBar(myToolbar);
        recyclerViewMasseuse = (RecyclerView) findViewById(R.id.recyclerViewMasseuse);
        recyclerViewMasseuse.setHasFixedSize(true);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Masseuses");
        mManager = new LinearLayoutManager(MasseuseActivity.this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        recyclerViewMasseuse.setLayoutManager(mManager);
        //to count results
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(mContext, String.valueOf(dataSnapshot.getChildrenCount())+" results found!",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

        //start location code
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
        if (ActivityCompat.checkSelfPermission(MasseuseActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MasseuseActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MasseuseActivity.this, "Please grant location permissions in settings", Toast.LENGTH_LONG).show();
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        //end location code


        qryMasseuses = dbRef.limitToFirst(100);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Masseuse>()
                .setQuery(qryMasseuses, Masseuse.class)
                .build();

        adapterMasseuse = new FirebaseRecyclerAdapter<Masseuse, ViewHolderMasseuse>(options) {

            @Override
            public ViewHolderMasseuse onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new ViewHolderMasseuse(inflater.inflate(R.layout.item_post_cardview, viewGroup, false));
            }


            float ratTemp = 0;

            @Override
            protected void onBindViewHolder(final ViewHolderMasseuse viewHolder, final int position, final Masseuse model) {
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
                                /*AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                                builder1.setTitle("Please give your review for the service provider");
                                builder1.setMessage("We will come with this feature shortly.\nPlease stay tuned!");
                                AlertDialog dialog1 = builder1.create();
                                dialog1.show();*/
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
                viewHolder.bindToMasseuse(model, mContext, myDistance.getDistance());
            }
        };
        recyclerViewMasseuse.setAdapter(adapterMasseuse);
        recyclerViewMasseuse.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
    }
    @Override
    public void onStart() {
        super.onStart();
        if (adapterMasseuse != null) {
            adapterMasseuse.startListening();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (adapterMasseuse != null) {
            adapterMasseuse.stopListening();
        }
    }
}