package com.example.cersapplication.MainParts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cersapplication.R;
import com.example.cersapplication.citizenregistration.create_accountCitizen;
import com.example.cersapplication.rescuerRegistration.create_Rescuer;

public class Register extends AppCompatActivity {
    Button Citizen;
    Button Rescuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Citizen = findViewById(R.id.button0);
        Citizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, create_accountCitizen.class);
                startActivity(i);
            }
        });


        Rescuer = findViewById(R.id.button);
        Rescuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, create_Rescuer.class);
                startActivity(i);
            }
        });





    }
}