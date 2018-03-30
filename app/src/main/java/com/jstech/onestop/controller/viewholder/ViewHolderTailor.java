package com.jstech.onestop.controller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Tailor;

/**
 * Created by samsung on 16-03-2018.
 */

public class ViewHolderTailor extends RecyclerView.ViewHolder {

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;

    public ViewHolderTailor(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewNameDisplay);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewExperience);
    }


    public void bindToTailor(Tailor tailor)
    {
        String name = tailor.getName();
        String phone = tailor.getPhone();
        String experience = String.valueOf(tailor.getExperience());
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
    }
}
