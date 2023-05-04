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
import com.example.cersapplication.citizenregistration.ReadWriteAdressDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Citizen_Address extends AppCompatActivity {

    //FirebaseUser Var
    private FirebaseAuth authAddress ;

    private TextView CitPurok, CitStreet, CitBarangay, CitCity, CitProvince;

    private String purok, street, barangay, city, province;

    private ProgressBar progressBarAddressCitizen;

    //Layout Variable
    private Button  addressupdate;

    private ImageButton buttonbacktosettings;



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_address);



        //Layout variable
        buttonbacktosettings = findViewById(R.id.imageButtonforbacktoSetting_AD);
        addressupdate = findViewById(R.id.Save_Changes_Cit_Address);
        CitPurok = findViewById(R.id.Show_Purok);
        CitStreet = findViewById(R.id.Show_Street);
        CitBarangay = findViewById(R.id.Show_Barangay);
        CitCity = findViewById(R.id.Show_City);
        CitProvince = findViewById(R.id.Show_Province);
        progressBarAddressCitizen = findViewById(R.id.progressBarAddressCitUpdate);


        buttonbacktosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoSettings = new Intent(Citizen_Address.this, SettingsCitizenProfile.class);
                startActivity(backtoSettings);
                finish();
            }
        });

        //go to update
        addressupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(Citizen_Address.this, update_address.class);
                startActivity(gotoUpdate);
            }
        });

        //Get current user
        authAddress = FirebaseAuth.getInstance();
        FirebaseUser CitizenAddress = authAddress.getCurrentUser();

        if(CitizenAddress !=null){
            Toast.makeText(Citizen_Address.this, "Something is wrong. User details are not available at the moment!",
                    Toast.LENGTH_LONG).show();
        } else{
            progressBarAddressCitizen.setVisibility(View.VISIBLE);
            showCitizenAddressProfile(CitizenAddress);
        }
        }

    private void showCitizenAddressProfile(FirebaseUser CitizenAddress) {
        String getUserId = CitizenAddress.getUid();

        //extract data from database
        DatabaseReference referenceCitizen = FirebaseDatabase.getInstance().getReference("Citizens").child("Address");
        referenceCitizen.child(getUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteAdressDetails addressdetails = snapshot.getValue(ReadWriteAdressDetails.class);

                if (addressdetails !=null){
                    purok = addressdetails.Purok;
                    street = addressdetails.Street;
                    barangay = addressdetails.Barangay;
                    city = addressdetails.City;
                    province = addressdetails.Province;

                    //set to textView
                    CitPurok.setText(purok);
                    CitStreet.setText(street);
                    CitBarangay.setText(barangay);
                    CitCity.setText(city);
                    CitProvince.setText(province);
                }
                progressBarAddressCitizen.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citizen_Address.this, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
                progressBarAddressCitizen.setVisibility(View.GONE);

            }
        });



    }

}

