package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    Context context;
    ArrayList<Restaurant> restaurantInternal;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurantInternal) {

        this.context=context;
        this.restaurantInternal=restaurantInternal;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Restaurant restaurant= restaurantInternal.get(position);
        holder.restaurantName.setText(restaurant.RName);
        holder.restaurantImage.setImageResource(restaurant.RImage);
    }

    @Override
    public int getItemCount() {
        return restaurantInternal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView restaurantName;
        ImageView restaurantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName= itemView.findViewById(R.id.restaurantName);
            restaurantImage= itemView.findViewById(R.id.restaurantImage);
        }
    }
}
