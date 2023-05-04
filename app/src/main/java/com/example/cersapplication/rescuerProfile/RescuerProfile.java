package com.example.cersapplication.rescuerProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cersapplication.R;

public class RescuerProfile extends AppCompatActivity {

    private Button respondbtn, cancelbtn, updateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer_profile);

        respondbtn = findViewById(R.id.accept_Emergency);
        cancelbtn = findViewById(R.id.deny_Emergency);
        updateProfile = findViewById(R.id.buttonEditProfilAct);

        //set date and time
        Calendar calendarUser = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendarUser.getTime());
        TextView viewDate = findViewById(R.id.Date);
        viewDate.setText(currentdate);

        //Disable respond and cancel
        respondbtn.setEnabled(false);
        cancelbtn.setEnabled(false);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoSettings = new Intent(RescuerProfile.this, RescuerSettings.class);
                startActivity(gotoSettings);
            }
        });
    }
}