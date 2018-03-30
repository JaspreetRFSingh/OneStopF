package com.jstech.onestop.controller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Mechanic;

public class ViewHolderMechanic extends RecyclerView.ViewHolder {

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;

    public ViewHolderMechanic(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = itemView.findViewById(R.id.textViewNameDisplay);
        txtExperience = itemView.findViewById(R.id.textViewExperience);
        txtPhone = itemView.findViewById(R.id.textViewPhone);
    }

    public void bindToMechanic(Mechanic mechanic)
    {
        String name = mechanic.getName();
        String phone = mechanic.getPhone();
        String experience = String.valueOf(mechanic.getExperience());
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
    }
}