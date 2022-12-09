package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.model.Dish;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

   Context context;
   ArrayList<Dish> dishArrayList;

    public DishAdapter(Context context, ArrayList<Dish> dishArrayList) {
        this.context = context;
        this.dishArrayList = dishArrayList;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dish_item,parent,false);

        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder holder, int position) {
        Dish dish=dishArrayList.get(position);
        Glide.with(holder.dishImage.getContext()).load(dish.getImage()).into(holder.dishImage);
        holder.dishName.setText(dish.getName());
        holder.dishPrice.setText(String.valueOf(dish.getPrice())+" EGP");
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder{

        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);

            dishImage=itemView.findViewById(R.id.dishImage);
            dishName=itemView.findViewById(R.id.dishName);
            dishPrice=itemView.findViewById(R.id.dishPrice);
        }
    }
}
