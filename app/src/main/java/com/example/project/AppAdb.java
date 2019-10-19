package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppAdb extends RecyclerView.Adapter<AppAdb.MyViewHolder> {
    ArrayList<String> adpidArray;
    ArrayList<String> adpmobileArray;
    ArrayList<String> adpamountArray;
    ArrayList<String> adpstypeArray;
    ArrayList<String> adpdatetimeArray;
    ArrayList<String> adpenddateArray;
    ArrayList<String> adpreasonArray;
    ArrayList<String> adpstatusArray;
    Context context;
    public AppAdb(ShowAll showAll,
                  ArrayList<String> idArray,
                  ArrayList<String> mobileArray,
                  ArrayList<String> amountArray,
                  ArrayList<String> stypeArray,
                  ArrayList<String> datetimeArray,
                  ArrayList<String> enddateArray,
                  ArrayList<String> reasonArray,
                  ArrayList<String> statusArray) {

        context=showAll;
        adpidArray=idArray;
        adpmobileArray=mobileArray;
        adpamountArray=amountArray;
        adpstypeArray=stypeArray;
        adpdatetimeArray=datetimeArray;
        adpenddateArray=enddateArray;
        adpreasonArray=reasonArray;
        adpstatusArray=statusArray;

    }

    @NonNull
    @Override
    public AppAdb.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.listdata,null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppAdb.MyViewHolder holder, int position) {
        holder.tv.setText(adpidArray.get(position));
        holder.tv1.setText(adpmobileArray.get(position));
        holder.tv2.setText(adpamountArray.get(position));
        holder.tv3.setText(adpstypeArray.get(position));
        holder.tv4.setText(adpdatetimeArray.get(position));
        holder.tv5.setText(adpenddateArray.get(position));
        holder.tv6.setText(adpreasonArray.get(position));
        holder.tv7.setText(adpstatusArray.get(position));

    }

    @Override
    public int getItemCount() {
        return adpidArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.ID);
            tv1=itemView.findViewById(R.id.mobile);
            tv2=itemView.findViewById(R.id.amount);
            tv3=itemView.findViewById(R.id.stype);
            tv4=itemView.findViewById(R.id.datetime);
            tv5=itemView.findViewById(R.id.endtime);
            tv6=itemView.findViewById(R.id.reason);
            tv7=itemView.findViewById(R.id.status);
        }
    }
}
