package com.example.cersapplication.citizenProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.update_address;
import com.example.cersapplication.citizenregistration.EmergencyC;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Citizen_Emergency_Contact extends AppCompatActivity {

    private TextView emergencyName, emergencyPhone, emergencyPurok, emergencyStreet, emergencyBarangay, emergencyCity, emergencyProvince;

    private String fullnameEC, phonenumberEC, purokEC, streetEC, barangayEC, cityEC, provinceEC;

    private ProgressBar updateECprogressbar;

    private FirebaseAuth firebaseEmergency;

    private Button  UpdateEc;

    private ImageButton backtosetting;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_emergency_contact);

        emergencyName = findViewById(R.id.Show_EmerName);
        emergencyPhone = findViewById(R.id.Show_EmerPhoneNumber);
        emergencyPurok = findViewById(R.id.Show_Purok);
        emergencyStreet = findViewById(R.id.Show_EmerStreet);
        emergencyBarangay = findViewById(R.id.Show_EmerBarangay);
        emergencyCity = findViewById(R.id.Show_EmerCity);
        emergencyProvince = findViewById(R.id.Show_EmerProvince);
        updateECprogressbar = findViewById(R.id.progressBarECCitUpdate);
        backtosetting = findViewById(R.id.imageButtonforbacktoSetting_EC);
        UpdateEc = findViewById(R.id.ToUpdate_Cit_EmergencContact);


        //go back to setting
        backtosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtosetting = new Intent(Citizen_Emergency_Contact.this, SettingsCitizenProfile.class);
                startActivity(backtosetting);
                finish();
            }
        });

        //proceed to update EC
        UpdateEc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(Citizen_Emergency_Contact.this, update_address.class);
                startActivity(gotoUpdate);
            }
        });

        //Database
        firebaseEmergency = FirebaseAuth.getInstance();
        FirebaseUser emergencyContactCit = firebaseEmergency.getCurrentUser();

        if (emergencyContactCit != null){
            Toast.makeText(Citizen_Emergency_Contact.this, "Something is wrong. User details are not available at the moment!", Toast.LENGTH_LONG).show();

        } else {
            updateECprogressbar.setVisibility(View.VISIBLE);
            showEmergencyContactDetails(emergencyContactCit);
        }

    }

    private void showEmergencyContactDetails(FirebaseUser emergencyContactCit) {
        String getUserID = emergencyContactCit.getUid();

        //Extract Data
        DatabaseReference referenceCitizenEmergencyCon = FirebaseDatabase.getInstance().getReference("Citizens");
        referenceCitizenEmergencyCon.child(getUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EmergencyC emergencyContactDetails = snapshot.getValue(EmergencyC.class);

                if (emergencyContactDetails !=null){
                    fullnameEC = emergencyContactDetails.ECName;
                    phonenumberEC = emergencyContactDetails.ECPhone;
                    purokEC = emergencyContactDetails.ECPhone;
                    streetEC = emergencyContactDetails.ECStreet;
                    barangayEC = emergencyContactDetails.ECBarangay;
                    cityEC = emergencyContactDetails.ECCity;
                    provinceEC = emergencyContactDetails.ECProvince;

                    //set to textView
                    emergencyName.setText(fullnameEC);
                    emergencyPhone.setText(phonenumberEC);
                    emergencyPurok.setText(purokEC);
                    emergencyStreet.setText(streetEC);
                    emergencyBarangay.setText(barangayEC);
                    emergencyCity.setText(cityEC);
                    emergencyProvince.setText(provinceEC);

                }
                updateECprogressbar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citizen_Emergency_Contact.this, "Something went wrong", Toast.LENGTH_LONG).show();
                updateECprogressbar.setVisibility(View.GONE);

            }
        });

    }
}