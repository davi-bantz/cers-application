package com.example.cersapplication.rescuerRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.cersapplication.R;
import com.example.cersapplication.rescuerProfile.RescuerProfile;

import android.view.View;
import android.widget.Button;

public class StartProfileRescuer extends AppCompatActivity {

    Button startprofRes;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_profile_rescuer2);

        startprofRes = findViewById(R.id.continueToProfileRescuer);
        startprofRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartProfileRescuer.this, RescuerProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}