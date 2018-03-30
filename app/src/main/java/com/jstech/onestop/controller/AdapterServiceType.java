package com.jstech.onestop.controller;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jstech.onestop.R;
import com.jstech.onestop.fetchview.DoctorActivity;
import java.util.ArrayList;



public class AdapterServiceType extends RecyclerView.Adapter<AdapterServiceType.ViewHolderServiceType>{
    private ArrayList<ListServiceType> data, tempList;
    Context context;


    public AdapterServiceType(ArrayList<ListServiceType> data, Context context){

        this.data = data;
        this.context = context;
        tempList = new ArrayList<>();
        tempList.addAll(data);
    }

    @Override
    public AdapterServiceType.ViewHolderServiceType onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.simple_list_cardview,parent,false);
        return new ViewHolderServiceType(view, context, data);
    }

    @Override
    public void onBindViewHolder(ViewHolderServiceType holder, int position) {

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

    public class ViewHolderServiceType extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtTitle;
        Context ctx;
        ArrayList<ListServiceType> listServiceTypes = new ArrayList<ListServiceType>();
        public ViewHolderServiceType(View itemView, Context ctx, ArrayList<ListServiceType> listServiceTypes) {
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
            Intent intent = new Intent(ctx, DoctorActivity.class);
            if(listServiceType.getType().equals("Addiction psychiatrist")) {
                intent.putExtra("keyShowDoctorType", "Addiction psychiatrist");
            }
            else if(listServiceType.getType().equals("Adolescent medicine specialist")){
                intent.putExtra("keyShowDoctorType", "Adolescent medicine specialist");
            }
            else if(listServiceType.getType().equals("Allergist (immunologist)")){
                intent.putExtra("keyShowDoctorType", "Allergist (immunologist)");
            }
            else if(listServiceType.getType().equals("Cardiologist")){
                intent.putExtra("keyShowDoctorType", "Cardiologist");
            }
            else if(listServiceType.getType().equals("Cardiovascular surgeon")){
                intent.putExtra("keyShowDoctorType", "Cardiovascular surgeon");
            }
            else if(listServiceType.getType().equals("Colon and rectal surgeon")){
                intent.putExtra("keyShowDoctorType", "Colon and rectal surgeon");
            }
            else if(listServiceType.getType().equals("Dermatologist")){
                intent.putExtra("keyShowDoctorType", "Dermatologist");
            }
            else if(listServiceType.getType().equals("Endocrinologist")){
                intent.putExtra("keyShowDoctorType", "Endocrinologist");
            }
            else if(listServiceType.getType().equals("Forensic pathologist")){
                intent.putExtra("keyShowDoctorType", "Forensic pathologist");
            }
            else if(listServiceType.getType().equals("Gynaecologist")){
                intent.putExtra("keyShowDoctorType", "Gynaecologist");
            }
            else if(listServiceType.getType().equals("Neurological surgeon")){
                intent.putExtra("keyShowDoctorType", "Neurological surgeon");
            }
            else if(listServiceType.getType().equals("Neurologist")){
                intent.putExtra("keyShowDoctorType", "Neurologist");
            }
            else if(listServiceType.getType().equals("Nuclear medicine specialist")){
                intent.putExtra("keyShowDoctorType", "Nuclear medicine specialist");
            }
            else if(listServiceType.getType().equals("Obstetrician")){
                intent.putExtra("keyShowDoctorType", "Obstetrician");
            }
            else if(listServiceType.getType().equals("Oncologist")){
                intent.putExtra("keyShowDoctorType", "Oncologist");
            }
            else if(listServiceType.getType().equals("Ophthalmologist")){
                intent.putExtra("keyShowDoctorType", "Ophthalmologist");
            }
            else if(listServiceType.getType().equals("Oral surgeon (maxillofacial surgeon)")){
                intent.putExtra("keyShowDoctorType", "Oral surgeon (maxillofacial surgeon)");
            }
            else if(listServiceType.getType().equals("Orthodontist")){
                intent.putExtra("keyShowDoctorType", "Orthodontist");
            }
            else if(listServiceType.getType().equals("Orthopaedic surgeon")){
                intent.putExtra("keyShowDoctorType", "Orthopaedic surgeon");
            }
            else if(listServiceType.getType().equals("Pathologist")){
                intent.putExtra("keyShowDoctorType", "Pathologist");
            }
            else if(listServiceType.getType().equals("Pediatrician")){
                intent.putExtra("keyShowDoctorType", "Pediatrician");
            }
            else if(listServiceType.getType().equals("Plastic surgeon")){
                intent.putExtra("keyShowDoctorType", "Plastic surgeon");
            }
            else if(listServiceType.getType().equals("Psychiatrist")){
                intent.putExtra("keyShowDoctorType", "Psychiatrist");
            }
            else if(listServiceType.getType().equals("Pulmonologist")){
                intent.putExtra("keyShowDoctorType", "Pulmonologist");
            }
            else if(listServiceType.getType().equals("Radiation oncologist")){
                intent.putExtra("keyShowDoctorType", "Radiation oncologist");
            }
            else if(listServiceType.getType().equals("Radiologist")){
                intent.putExtra("keyShowDoctorType", "Radiologist");
            }
            else if(listServiceType.getType().equals("Reproductive endocrinologist")){
                intent.putExtra("keyShowDoctorType", "Reproductive endocrinologist");
            }
            else if(listServiceType.getType().equals("Rheumatologist")){
                intent.putExtra("keyShowDoctorType", "Rheumatologist");
            }
            else if(listServiceType.getType().equals("Spinal cord injury specialist")){
                intent.putExtra("keyShowDoctorType", "Spinal cord injury specialist");
            }
            else if(listServiceType.getType().equals("Thoracic surgeon")){
                intent.putExtra("keyShowDoctorType", "Thoracic surgeon");
            }
            else if(listServiceType.getType().equals("Urologist")){
                intent.putExtra("keyShowDoctorType", "Urologist");
            }
            else if(listServiceType.getType().equals("Vascular surgeon")){
                intent.putExtra("keyShowDoctorType", "Vascular surgeon");
            }
            this.ctx.startActivity(intent);
        }
    }
}
