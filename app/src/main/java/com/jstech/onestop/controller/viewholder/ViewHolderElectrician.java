package com.jstech.onestop.controller.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Electrician;

import java.util.ArrayList;

/**
 * Created by samsung on 16-03-2018.
 */

public class ViewHolderElectrician extends RecyclerView.ViewHolder{

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;

    public ViewHolderElectrician(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewNameDisplay);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewExperience);
    }

    public void bindToElectrician(Electrician electrician)
    {
        String name = electrician.getName();
        String phone = electrician.getPhone();
        String experience = String.valueOf(electrician.getExperience());
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
    }

}
