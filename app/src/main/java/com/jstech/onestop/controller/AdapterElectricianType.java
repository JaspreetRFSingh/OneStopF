package com.jstech.onestop.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.controller.viewholder.ViewHolderElectrician;
import com.jstech.onestop.fetchview.ElectricianActivity;
import com.jstech.onestop.fetchview.MechanicActivity;

import java.util.ArrayList;


public class AdapterElectricianType extends RecyclerView.Adapter<AdapterElectricianType.ViewHolderElectricianType>{
    private ArrayList<ListServiceType> data, tempList;
    Context context;


    public AdapterElectricianType(ArrayList<ListServiceType> data, Context context){

        this.data = data;
        this.context = context;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @Override
    public AdapterElectricianType.ViewHolderElectricianType onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_list_cardview,parent,false);
        return new ViewHolderElectricianType(view, context, data);
    }

    @Override
    public void onBindViewHolder(ViewHolderElectricianType holder, int position) {

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

    public class ViewHolderElectricianType extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtTitle;
        Context ctx;
        ArrayList<ListServiceType> listServiceTypes = new ArrayList<ListServiceType>();
        public ViewHolderElectricianType(View itemView, Context ctx, ArrayList<ListServiceType> listServiceTypes) {
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
            Intent intent = new Intent(ctx, ElectricianActivity.class);
            if(listServiceType.getType().equals("AC")) {
                intent.putExtra("keyShowElecType", "AC");
            }

            else if(listServiceType.getType().equals("Geysers")){
                intent.putExtra("keyShowElecType", "Geysers");
            }

            else if(listServiceType.getType().equals("Refrigerators")){
                intent.putExtra("keyShowElecType", "Refrigerators");
            }

            else if(listServiceType.getType().equals("Other Appliances")){
                intent.putExtra("keyShowElecType", "Other Appliances");
            }
            this.ctx.startActivity(intent);
        }
    }
}