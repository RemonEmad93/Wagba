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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    Context context;
    ArrayList<Restaurant> restaurantArrayList;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurantArrayList) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant=restaurantArrayList.get(position);
        holder.resName.setText(restaurant.getRestaurantName());
        holder.resImage.setImageResource(restaurant.getRestaurantImage());
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{

        TextView resName;
        ImageView resImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            resName= itemView.findViewById(R.id.restaurantItemName);
            resImage= itemView.findViewById(R.id.restaurantItemImage);

        }
    }
}
