package com.jstech.onestop.controller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Caterer;

/**
 * Created by samsung on 25-03-2018.
 */

public class ViewHolderCaterer extends RecyclerView.ViewHolder{

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;

    public ViewHolderCaterer(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewNameDisplay);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewExperience);

    }

    public void bindToCaterer(Caterer caterer)
    {
        String name = caterer.getName();
        String phone = caterer.getPhone();
        String experience = String.valueOf(caterer.getExperience());
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
    }
}
