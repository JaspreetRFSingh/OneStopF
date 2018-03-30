package com.jstech.onestop.controller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Driver;

/**
 * Created by samsung on 16-03-2018.
 */

public class ViewHolderDriver extends RecyclerView.ViewHolder{

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;


    public ViewHolderDriver(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewNameDisplay);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewExperience);
    }

    public void bindToDriver(Driver driver)
    {
        String name = driver.getName();
        String phone = driver.getPhone();
        String verified;
        String experience = String.valueOf(driver.getExperience());

        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
    }

}
