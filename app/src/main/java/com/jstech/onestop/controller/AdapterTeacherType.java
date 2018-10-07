package com.jstech.onestop.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jstech.onestop.R;
import com.jstech.onestop.fetchview.TeacherActivity;

import java.util.ArrayList;

public class AdapterTeacherType extends RecyclerView.Adapter<AdapterTeacherType.ViewHolderTeacherType> {

    private ArrayList<ListServiceType> data, tempList;
    Context context;

    public AdapterTeacherType(ArrayList<ListServiceType> data, Context context)
    {
        this.data = data;
        this.context = context;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @Override
    public ViewHolderTeacherType onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_list_cardview,parent,false);
        return new AdapterTeacherType.ViewHolderTeacherType(view, context, data);
    }

    @Override
    public void onBindViewHolder(ViewHolderTeacherType holder, int position) {

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

    public class ViewHolderTeacherType extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle;
        Context ctx;
        ArrayList<ListServiceType> listServiceTypes = new ArrayList<ListServiceType>();

        public ViewHolderTeacherType(View itemView, Context ctx, ArrayList<ListServiceType> listServiceTypes) {
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
            Intent intent = new Intent(ctx,TeacherActivity.class);

            if(listServiceType.getType().equals("Accountancy")) {
                intent.putExtra("keyShowTeacherType", "Accountancy");
            }

            else if(listServiceType.getType().equals("Biology")){
                intent.putExtra("keyShowTeacherType", "Biology");
            }

            else if(listServiceType.getType().equals("Business Studies")){
                intent.putExtra("keyShowTeacherType", "Business Studies");
            }

            else if(listServiceType.getType().equals("C++")){
                intent.putExtra("keyShowTeacherType", "C++");

            }
            else if(listServiceType.getType().equals("Chemistry")){
                intent.putExtra("keyShowTeacherType", "Chemistry");

            }
            else if(listServiceType.getType().equals("Economics")){
                intent.putExtra("keyShowTeacherType", "Economics");
            }
            else if(listServiceType.getType().equals("English")){
                intent.putExtra("keyShowTeacherType", "English");
            }
            else if(listServiceType.getType().equals("Hindi")){
                intent.putExtra("keyShowTeacherType", "Hindi");
            }
            else if(listServiceType.getType().equals("Java")){
                intent.putExtra("keyShowTeacherType", "Java");
            }
            else if(listServiceType.getType().equals("Mathematics")){
                intent.putExtra("keyShowTeacherType", "Mathematics");
            }
            else if(listServiceType.getType().equals("Physics")){
                intent.putExtra("keyShowTeacherType", "Physics");
            }
            else if(listServiceType.getType().equals("Punjabi")){
                intent.putExtra("keyShowTeacherType", "Punjabi");
            }
            else if(listServiceType.getType().equals("SBI PO")){
                intent.putExtra("keyShowTeacherType", "SBI PO");
            }
            else if(listServiceType.getType().equals("SSC")){
                intent.putExtra("keyShowTeacherType", "SSC");
            }
            else if(listServiceType.getType().equals("UPSC")){
                intent.putExtra("keyShowTeacherType", "UPSC");
            }
            else if(listServiceType.getType().equals("CAT")){
                intent.putExtra("keyShowTeacherType", "CAT");
            }
            else if(listServiceType.getType().equals("GATE")){
                intent.putExtra("keyShowTeacherType", "GATE");
            }
            else if(listServiceType.getType().equals("UGC NET")){
                intent.putExtra("keyShowTeacherType", "UGC NET");
            }
            else if(listServiceType.getType().equals("IELTS")){
                intent.putExtra("keyShowTeacherType", "IELTS");
            }
            else if(listServiceType.getType().equals("GRE")){
                intent.putExtra("keyShowTeacherType", "GRE");
            }
            else if(listServiceType.getType().equals("TOEFL")){
                intent.putExtra("keyShowTeacherType", "TOEFL");
            }
            this.ctx.startActivity(intent);
        }
    }
}