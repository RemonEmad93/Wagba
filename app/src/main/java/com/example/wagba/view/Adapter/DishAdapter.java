package com.example.wagba.view.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.view.RecyclerViewInterface.DishRecyclerViewInterface;
import com.example.wagba.R;
import com.example.wagba.model.DishModel;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

   Context context;
   ArrayList<DishModel> dishArrayList;
   private final DishRecyclerViewInterface dishRecyclerViewInterface;
   String num;
   public SharedPreferences sp;

    public DishAdapter(Context context, ArrayList<DishModel> dishArrayList, DishRecyclerViewInterface dishRecyclerViewInterface, String num) {
        sp=context.getSharedPreferences("orderss",0);
        this.num=num;
        this.context = context;
        this.dishArrayList = dishArrayList;
        this.dishRecyclerViewInterface=dishRecyclerViewInterface;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dish_item,parent,false);
        return new DishViewHolder(view, dishRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder holder, int position) {

        DishModel dish=dishArrayList.get(position);
        Glide.with(holder.dishImage.getContext()).load(dish.getImage()).into(holder.dishImage);
        holder.dishName.setText(dish.getName());
        holder.dishPrice.setText(String.valueOf(dish.getPrice())+" EGP");

        if(dish.getAvailability()){
            if(sp.getString(dish.getName()+num,"not found") ==  "not found"){
                holder.dishCounter.setText("0");
            }else{
                holder.dishCounter.setText(sp.getString(dish.getName()+num,"not found"));
            }
        }else{
            holder.dishCounter.setText("--");
            holder.addDish.setOnClickListener(null);
            holder.removeDish.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder{

        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        TextView addDish;
        TextView removeDish;
        TextView dishCounter;

        public static  Boolean flag;


        public DishViewHolder(@NonNull View itemView,DishRecyclerViewInterface dishRecyclerViewInterface) {
            super(itemView);

            dishImage=itemView.findViewById(R.id.dishImage);
            dishName=itemView.findViewById(R.id.dishName);
            dishPrice=itemView.findViewById(R.id.dishPrice);
            addDish=itemView.findViewById(R.id.addDish);
            removeDish= itemView.findViewById(R.id.removeDish);
            dishCounter=itemView.findViewById(R.id.dishCounter);

            addDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dishCounter.setText(String.valueOf(Integer.parseInt(dishCounter.getText().toString())+1));
                    flag=true;
                    int pos=getAbsoluteAdapterPosition();
                    dishRecyclerViewInterface.onDishClick(pos);
                }
            });

            removeDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(dishCounter.getText().toString()) > 0){
                        dishCounter.setText(String.valueOf(Integer.parseInt(dishCounter.getText().toString())-1));
                        flag=false;
                        int pos=getAbsoluteAdapterPosition();
                        dishRecyclerViewInterface.onDishClick(pos);
                    }
                }
            });
        }
    }
}
