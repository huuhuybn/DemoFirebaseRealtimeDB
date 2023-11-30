package com.example.quanlykhohang.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhohang.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAdd.setOnClickListener(v -> {
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cars");
            Car car  = new Car(1,"Toyota");
            int idRandom = new Random().nextInt(1000);
            myRef.child("" + idRandom)
                    .setValue(car, (error, ref) -> {
                Toast.makeText(this, "Them Thanh Cong!!",
                        Toast.LENGTH_SHORT).show();
            });
        });

        binding.btnDelete.setOnClickListener(v ->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cars");
            myRef.child("802").removeValue((error, ref)->{
                Toast.makeText(this, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            });
        });

        binding.btnUpdate.setOnClickListener(v -> {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cars");
            Car car  = new Car(1,"Honda");
            int idRandom = 694;
            myRef.child("" + idRandom)
                    .setValue(car, (error, ref) -> {
                        Toast.makeText(this, "Them Thanh Cong!!",
                                Toast.LENGTH_SHORT).show();
                    });

        });


        binding.btnGetList.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("cars");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        List<Car> cars = new ArrayList<>();

                        for (DataSnapshot snap : snapshot.getChildren()){
                            Car car = snap.getValue(Car.class);
                            cars.add(car);
                        }

                        Toast.makeText(MainActivity.this,
                                "" + cars.size(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
    }


}

