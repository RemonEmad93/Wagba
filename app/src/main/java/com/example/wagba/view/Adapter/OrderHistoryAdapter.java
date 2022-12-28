package com.example.wagba.view.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.example.wagba.model.CartItemModel;
import com.example.wagba.model.OrderHistoryModel;
import com.example.wagba.model.OrderModel;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    Activity activity;

//    ArrayList<CartItemModel> itemArrayList;
    ArrayList<OrderHistoryModel> orderHistoryModel;

    public OrderHistoryAdapter(Activity activity,ArrayList<OrderHistoryModel> orderHistoryModel ){
        this.activity=activity;
        this.orderHistoryModel=orderHistoryModel;
//        this.itemArrayList=itemArrayList;
    }


    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item,parent,false);

        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {

        OrderHistoryModel item=orderHistoryModel.get(position);
        holder.orderDate.setText(item.getTime());
        holder.orderStatus.setText(item.getStates());

//        Log.d("helpme",item.cartItemModelArrayList.toString());
//        CartAdapter cartAdapter= new CartAdapter(item.cartItemModelArrayList());
//        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(activity);
//        holder.orderItems.setLayoutManager(linearLayoutManager);
//        holder.orderItems.setAdapter(cartAdapter);
//        cartAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return orderHistoryModel.size();
    }


    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView orderDate;
        TextView orderStatus;
//        RecyclerView orderItems;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDate= itemView.findViewById(R.id.orderDateTextView);
            orderStatus= itemView.findViewById(R.id.orderStatusTextView);
//            orderItems= itemView.findViewById(R.id.orderRecyclerView);

        }
    }
}
