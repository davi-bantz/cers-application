package com.example.cersapplication.citizenregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cersapplication.R;
import com.example.cersapplication.citizenProfile.CitizenProfile;

import android.view.View;
import android.widget.Button;


public class StartProfileCitizen extends AppCompatActivity {

    Button startprof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_profile);

        startprof = findViewById(R.id.continueToProfile);

        startprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartProfileCitizen.this, CitizenProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
    }
}