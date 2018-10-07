package com.jstech.onestop.controller.viewholder;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jstech.onestop.R;
import com.jstech.onestop.model.BabySitter;

public class ViewHolderBabySitter  extends RecyclerView.ViewHolder{

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;
    public TextView txtDirection;
    public RatingBar ratingBar;

    public ViewHolderBabySitter(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewNameDisplay);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewExperience);
        txtDirection = (TextView)itemView.findViewById(R.id.txtDirection);
        ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

    }

    public void bindToBabySitter(final BabySitter model, final Context ctx, final String distance)
    {
        String name = model.getName();
        String phone = model.getPhone();
        String experience = String.valueOf(model.getExperience());
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience+" years");
        ratingBar.setRating(model.computeRatings());
        txtDirection.setPaintFlags(txtDirection.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtDirection.setText(distance+"km");
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + model.getPhone();
                ctx.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
        txtDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uri = "http://maps.google.com/maps?daddr=" + model.getLatitude() + "," + model.getLongitude() + " (" + model.getName()+" is at" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try {
                    ctx.startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(ctx, "Please install Google Maps to navigate to "+model.getName()+"'s location!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
