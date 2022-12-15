package com.example.wagba;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.model.Dish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {


   Context context;
   ArrayList<Dish> dishArrayList;
   private final DishRecyclerViewInterface dishRecyclerViewInterface;

   String num;

    public SharedPreferences sp;
//    SharedPreferences.Editor ed;
//    sp=getSharedPreferences("order",0);





    public DishAdapter(Context context, ArrayList<Dish> dishArrayList,DishRecyclerViewInterface dishRecyclerViewInterface,String num) {
        sp=context.getSharedPreferences("orderss",0);
        this.num=num;

        this.context = context;
        this.dishArrayList = dishArrayList;
        this.dishRecyclerViewInterface=dishRecyclerViewInterface;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        sp=getSharedPreferences("order",0);

        View view= LayoutInflater.from(context).inflate(R.layout.dish_item,parent,false);

        return new DishViewHolder(view, dishRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.DishViewHolder holder, int position) {

//        sp=getSharedPreferences("order",0);

//        ed=sp.edit();
//        ed.clear();


        Dish dish=dishArrayList.get(position);
        Glide.with(holder.dishImage.getContext()).load(dish.getImage()).into(holder.dishImage);
        holder.dishName.setText(dish.getName());
        Log.d("helpppppp", dish.getName());
        Log.d("helpppppp",sp.getString(dish.getName().toString(),"not found"));

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




//        if(sp.getString(dish.getName(), null) == null){
//            holder.dishCounter.setText("0");
//        }else{
//            holder.dishCounter.setText(sp.getString(dish.getName(), null));
//        }
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

//        FirebaseDatabase database;
//        DatabaseReference myRef;

//        public static int currentOrder=0;

        public static  Boolean flag;
//         SharedPreferences sp;
//        sp=getSharedPreferences("order",0);


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

//                    database = FirebaseDatabase.getInstance();
//                    myRef = database.getReference();
//
//                    myRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            if(snapshot.child("current order").getValue().toString().isEmpty()){
//                                snapshot.child("current order").
//                            }
////                            dishArrayList.clear();
//                            for (DataSnapshot dishes: snapshot.child("current order").getChildren()){
//
//
//                                Dish dish= new Dish(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class));
//                                dishArrayList.add(dish);
//
//                            }
////                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));
//                            binding.dishRecyclerView.setAdapter(dishAdapter);
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//
//
//                    });

//                    dishCounter.setText(String.valueOf(Integer.parseInt(dishCounter.getText().toString())+1));
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
