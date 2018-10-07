package com.jstech.onestop.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.controller.viewholder.ViewHolderEventPlanner;
import com.jstech.onestop.fetchview.EventPlannerActivity;
import com.jstech.onestop.fetchview.MechanicActivity;

import java.util.ArrayList;


public class AdapterEventPlannerType extends RecyclerView.Adapter<AdapterEventPlannerType.ViewHolderEventPlannerType>{
    private ArrayList<ListServiceType> data, tempList;
    Context context;


    public AdapterEventPlannerType(ArrayList<ListServiceType> data, Context context){

        this.data = data;
        this.context = context;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @Override
    public AdapterEventPlannerType.ViewHolderEventPlannerType onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_list_cardview,parent,false);
        return new ViewHolderEventPlannerType(view, context, data);
    }

    @Override
    public void onBindViewHolder(ViewHolderEventPlannerType holder, int position) {

        String title = data.get(position).getType();
        holder.txtTitle.setText(title);

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
            for(ListServiceType listServiceType : tempList){
                if(listServiceType.getType().toLowerCase().contains(str.toLowerCase())){
                    data.add(listServiceType);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolderEventPlannerType extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtTitle;
        Context ctx;
        ArrayList<ListServiceType> listServiceTypes = new ArrayList<ListServiceType>();
        public ViewHolderEventPlannerType(View itemView, Context ctx, ArrayList<ListServiceType> listServiceTypes) {
            super(itemView);
            this.ctx = ctx;
            this.listServiceTypes = listServiceTypes;
            itemView.setOnClickListener(this);
            txtTitle = (TextView) itemView.findViewById(R.id.textViewTypeName);
        }
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            ListServiceType listServiceType = this.listServiceTypes.get(pos);
            Intent intent = new Intent(ctx, EventPlannerActivity.class);
            if(listServiceType.getType().equals("Award Ceremonies")){
                intent.putExtra("keyShowEventType", "Award Ceremonies");
            }
            else if(listServiceType.getType().equals("Birthday Parties")) {
                intent.putExtra("keyShowEventType", "Birthday Parties");
            }

            else if(listServiceType.getType().equals("Conferences")){
                intent.putExtra("keyShowEventType", "Conferences");

            }

            else if(listServiceType.getType().equals("Fund Raiser Events")){
                intent.putExtra("keyShowEventType", "Fund Raiser Events");

            }

            else if(listServiceType.getType().equals("Kitty Parties")){
                intent.putExtra("keyShowEventType", "Kitty Parties");
            }

            else if(listServiceType.getType().equals("Meetings")){
                intent.putExtra("keyShowEventType", "Meetings");

            }
            else if(listServiceType.getType().equals("Opening Ceremonies")){
                intent.putExtra("keyShowEventType", "Opening Ceremonies");

            }
            else if(listServiceType.getType().equals("Reception Parties")){
                intent.putExtra("keyShowEventType", "Reception Parties");
            }

            else if(listServiceType.getType().equals("Seminars")){
                intent.putExtra("keyShowEventType", "Seminars");

            }
            else if(listServiceType.getType().equals("Weddings")){
                intent.putExtra("keyShowEventType", "Weddings");
            }
            this.ctx.startActivity(intent);
        }
    }
}