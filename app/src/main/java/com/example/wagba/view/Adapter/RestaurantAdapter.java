package com.example.wagba.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.example.wagba.view.RecyclerViewInterface.RestaurantRecyclerViewInterface;
import com.example.wagba.model.RestaurantModel;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    Context context;
    ArrayList<RestaurantModel> restaurantArrayList;
    private final RestaurantRecyclerViewInterface restaurantRecyclerViewInterface;

    public RestaurantAdapter(Context context, ArrayList<RestaurantModel> restaurantArrayList, RestaurantRecyclerViewInterface restaurantRecyclerViewInterface) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
        this.restaurantRecyclerViewInterface=restaurantRecyclerViewInterface;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);
        return new RestaurantViewHolder(view, restaurantRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        RestaurantModel restaurant=restaurantArrayList.get(position);
        holder.resName.setText(restaurant.getRestaurantName());
        Glide.with(holder.resImage.getContext()).load(restaurant.getLogo()).into(holder.resImage);
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{

        TextView resName;
        ImageView resImage;

        public RestaurantViewHolder(@NonNull View itemView,RestaurantRecyclerViewInterface restaurantRecyclerViewInterface) {
            super(itemView);

            resName= itemView.findViewById(R.id.restaurantItemName);
            resImage= itemView.findViewById(R.id.restaurantItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(restaurantRecyclerViewInterface != null){

                        int pos=getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            restaurantRecyclerViewInterface.onRestaurantClick(pos);
                        }

                    }
                }
            });

        }
    }
}
