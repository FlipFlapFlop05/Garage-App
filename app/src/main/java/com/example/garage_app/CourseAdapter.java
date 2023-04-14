package com.example.garage_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    Context context;
    ArrayList<CourseModel> arrList;

    CourseAdapter(Context context, ArrayList<CourseModel> arrList){
        this.context = context;
        this.arrList = arrList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=  LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.car_make.setText(arrList.get(position).car_make);
        holder.car_model.setText(arrList.get(position).car_model);

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView car_make, car_model;
        ImageView img;
        public ViewHolder(View itemView){
            super(itemView);

            car_make = itemView.findViewById(R.id.car_make);
            car_model = itemView.findViewById(R.id.car_model);
            img = itemView.findViewById(R.id.img);

            itemView.findViewById(R.id.del_car).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int abc = getAdapterPosition();
                    Log.d("Position", ""+abc);
                    arrList.remove(abc);
                    Toast.makeText(context.getApplicationContext(), "Car Deleted! Please refresh..", Toast.LENGTH_SHORT).show();
                    Log.d("ArrList", ""+arrList.size());

                }
            });
        }

    }


}
