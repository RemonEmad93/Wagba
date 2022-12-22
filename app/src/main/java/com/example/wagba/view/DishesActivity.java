package com.example.wagba.view;

import static com.example.wagba.view.Adapter.DishAdapter.DishViewHolder.flag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.view.RecyclerViewInterface.DishRecyclerViewInterface;
import com.example.wagba.databinding.ActivityDishesBinding;
import com.example.wagba.model.DishModel;
import com.example.wagba.view.Adapter.DishAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DishesActivity extends AppCompatActivity implements DishRecyclerViewInterface {

    private ActivityDishesBinding binding;
//    FirebaseAuth auth;
//    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;

    DishAdapter dishAdapter;
    ArrayList<DishModel> dishArrayList;

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

                    DishModel dish= new DishModel(dishes.child("name").getValue().toString(),dishes.child("image").getValue().toString(),dishes.child("price").getValue(Integer.class),dishes.child("availability").getValue(Boolean.class));
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
        finish();
    }



    @Override
    public void onDishClick(int position) {

        if(flag){
            ed.putString(dishArrayList.get(position).getName().toString()+num,
                    String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0"))+1));
        }else{
            if(Objects.equals(sp.getString(dishArrayList.get(position).getName() + num, "0"), "1")){
                ed.remove(dishArrayList.get(position).getName().toString()+num).commit();

            }else{
                ed.putString(dishArrayList.get(position).getName().toString()+num,
                        String.valueOf(Integer.parseInt(sp.getString(dishArrayList.get(position).getName().toString()+num,"0")) -1));
            }



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