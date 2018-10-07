package com.jstech.onestop.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jstech.onestop.R;
import com.jstech.onestop.fetchview.BabysitterActivity;
import com.jstech.onestop.fetchview.BeauticianActivity;
import com.jstech.onestop.fetchview.CarpenterActivity;
import com.jstech.onestop.fetchview.CatererActivity;
import com.jstech.onestop.fetchview.CookActivity;
import com.jstech.onestop.fetchview.DoctorActivity;
import com.jstech.onestop.fetchview.DriverActivity;
import com.jstech.onestop.fetchview.ElectricianActivity;
import com.jstech.onestop.fetchview.EventPlannerActivity;
import com.jstech.onestop.fetchview.MasseuseActivity;
import com.jstech.onestop.fetchview.MechanicActivity;
import com.jstech.onestop.fetchview.PainterActivity;
import com.jstech.onestop.fetchview.PlumberActivity;
import com.jstech.onestop.fetchview.TailorActivity;
import com.jstech.onestop.fetchview.TeacherActivity;
import com.jstech.onestop.fetchview.category.ActivityShowDoctorType;
import com.jstech.onestop.fetchview.category.ActivityShowElectricianType;
import com.jstech.onestop.fetchview.category.ActivityShowEventPlannerType;
import com.jstech.onestop.fetchview.category.ActivityShowMechanicType;
import com.jstech.onestop.fetchview.category.ActivityShowTeacherType;
import com.jstech.onestop.firebase.AddEventPlannerActivity;
import com.jstech.onestop.model.Carpenter;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolderMain>{


    private ArrayList<ListMain> data, tempList;
    Context context;

    public AdapterMain(ArrayList<ListMain> data, Context context){

        this.data = data;
        this.context = context;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @Override
    public ViewHolderMain onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_main_cardview,parent,false);
        return new ViewHolderMain(view, context, data);
    }


    @Override
    public void onBindViewHolder(ViewHolderMain holder, int position) {

        String title = data.get(position).getName();
        int icon = data.get(position).getIcon();
        holder.txtTitle.setText(title);
        holder.imgIcon.setImageResource(icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void filter(String str){

        data.clear();
        if(str.length()==0){
            data.addAll(tempList);
        }else{
            for(ListMain listMain : tempList){
                if(listMain.getName().toLowerCase().contains(str.toLowerCase())){
                    data.add(listMain);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolderMain extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgIcon;
        TextView txtTitle;
        Context ctx;
        ArrayList<ListMain> listMains = new ArrayList<ListMain>();
        public ViewHolderMain(View itemView, Context ctx, ArrayList<ListMain> listMains) {
            super(itemView);
            this.ctx = ctx;
            this.listMains = listMains;
            itemView.setOnClickListener(this);
            imgIcon = (ImageView)itemView.findViewById(R.id.imageView);
            imgIcon.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.bounce));
            txtTitle = (TextView) itemView.findViewById(R.id.textViewName);
            txtTitle.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.slide_left));

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            ListMain listMain = this.listMains.get(pos);
            Intent intent = new Intent();
            if(listMain.getName().equals("Doctor")){
                intent = new Intent(ctx, ActivityShowDoctorType.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Electrician")){
                intent = new Intent(ctx, ActivityShowElectricianType.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Plumber")){
                intent = new Intent(ctx, PlumberActivity.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Driver")){
                intent = new Intent(ctx, DriverActivity.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Mechanic")){
                intent = new Intent(ctx, ActivityShowMechanicType.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Tailor")){
                intent = new Intent(ctx, TailorActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Teacher")){
                intent = new Intent(ctx, ActivityShowTeacherType.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Cook")){
                intent = new Intent(ctx, CookActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Masseuse")){
                intent = new Intent(ctx, MasseuseActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Beautician")){
                intent = new Intent(ctx, BeauticianActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Caterer")){
                intent = new Intent(ctx, CatererActivity.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Baby Sitter")){
                intent = new Intent(ctx, BabysitterActivity.class);
                intent.putExtra("keyId", listMain.getName());

            }
            else if(listMain.getName().equals("Carpenter")){
                intent = new Intent(ctx, CarpenterActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Event Planner")){
                intent = new Intent(ctx, ActivityShowEventPlannerType.class);
                intent.putExtra("keyId", listMain.getName());
            }
            else if(listMain.getName().equals("Painter")){
                intent = new Intent(ctx, PainterActivity.class);
                intent.putExtra("keyId", listMain.getName());
            }
            this.ctx.startActivity(intent);
        }
    }
}