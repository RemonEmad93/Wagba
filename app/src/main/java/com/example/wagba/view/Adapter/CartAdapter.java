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
import com.example.wagba.model.CartItem;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<CartItem> itemArrayList;


    public CartAdapter(Context context, ArrayList<CartItem> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {

        CartItem item=itemArrayList.get(position);
        Glide.with(holder.cartItemImage.getContext()).load(item.getImage()).into(holder.cartItemImage);
        holder.cartItemName.setText(item.getName());
        holder.cartItemPrice.setText(String.valueOf(item.getPrice())+"EGP");
        holder.cartItemCount.setText(item.getCount());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView cartItemImage;
        TextView cartItemName;
        TextView cartItemPrice;
        TextView cartItemCount;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartItemImage=itemView.findViewById(R.id.cartItemImage);
            cartItemName=itemView.findViewById(R.id.cartItemName);
            cartItemPrice=itemView.findViewById(R.id.cartItemPrice);
            cartItemCount=itemView.findViewById(R.id.cartItemCount);



        }
    }
}
