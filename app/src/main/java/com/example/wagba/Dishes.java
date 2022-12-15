package com.example.wagba;

import static com.example.wagba.DishAdapter.DishViewHolder.flag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wagba.databinding.ActivityDishesBinding;
import com.example.wagba.model.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dishes extends AppCompatActivity implements DishRecyclerViewInterface {

    private ActivityDishesBinding binding;
//    FirebaseAuth auth;
//    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;

    DishAdapter dishAdapter;
    ArrayList<Dish> dishArrayList;

    private String num,name;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    String[] dishes={};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDishesBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);


        num= getIntent().getStringExtra("place");
        name=getIntent().getStringExtra("name");

        sp=getSharedPreferences("orderss",0);
        ed=sp.edit();
//        ed.clear();
//        ed.commit();

//        ed.putString("restaurant",name);




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();



        binding.dishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dishRecyclerView.setHasFixedSize(true);


        dishArrayList= new ArrayList<>();

        dishAdapter= new DishAdapter(this, dishArrayList, this,num);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dishArrayList.clear();
                for (DataSnapshot dishes: snapshot.child("restaurants/res"+num+"/dishes").getChildren()){

                    Dish dish= new Dish(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class));
                    dishArrayList.add(dish);

                }
//                binding.dishRecyclerView.setAdapter(new DishAdapter(Dishes.this,dishArrayList));
                binding.dishRecyclerView.setAdapter(dishAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });




    }
    @Override
    public void onBackPressed(){
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        finish();
    }



    @Override
    public void onDishClick(int position) {

        if(flag){
            ed.putString(dishArrayList.get(position).getName().toString()+num,
                    String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0"))+1));
        }else{
            ed.putString(dishArrayList.get(position).getName().toString()+num,
                    String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0")) -1));
        }

        ed.commit();



//        for(int i=0;i<dishes.length;i++){
//            if(dishArrayList.get(position).getName().toString() != dishes[i] ){
//                continue;
//            }else{
//                if(flag== true){
//                    ed.putString(dishArrayList.get(position).getName().toString(),
//                            String.valueOf(sp.getString(dishArrayList.get(position).getName().toString(),null)+1));
//                }else{
////                    ed.putString(dishArrayList.get(position).getName().toString(),
////                            String.valueOf(sp.getString(Integer.parseInt(dishArrayList.get(position).getName().toString(),null))-1));
//                }
//
//                sp.getAll();
//                return;
//            }
//        }


    }
}