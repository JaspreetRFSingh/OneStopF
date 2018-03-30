package com.jstech.onestop.controller.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.model.Teacher;

public class ViewHolderTeacher extends RecyclerView.ViewHolder {

    View mView;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtExperience;
    public TextView txtQualification;

    public ViewHolderTeacher(View itemView) {
        super(itemView);
        mView = itemView;
        txtName = (TextView)itemView.findViewById(R.id.textViewTeacherDisplayName);
        txtPhone = (TextView)itemView.findViewById(R.id.textViewTeacherDisplayPhone);
        txtExperience = (TextView)itemView.findViewById(R.id.textViewTeacherDisplayExperience);
        txtQualification = (TextView)itemView.findViewById(R.id.textViewTeacherDisplayQualification);
    }

    public void bindToTeacher(Teacher teacher)
    {
        String name = teacher.getName();
        String phone = teacher.getPhone();
        String experience = String.valueOf(teacher.getExperience());
        String qualification = teacher.getQualification();
        txtName.setText(name);
        txtPhone.setText(phone);
        txtExperience.setText(experience);
        txtQualification.setText(qualification);
    }
}