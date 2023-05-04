package com.example.cersapplication.citizenregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cersapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Address extends AppCompatActivity {

    private EditText editBarangay, editStreet, editPurok, editCity, editProvince, editCivilStatus, editBloodType;

    private ProgressBar progressBar;

    private RadioGroup radioGroupGender;
    private RadioButton radioButtonSelectedGender;

    Button victimNext;

    Button victimBack;

    DatabaseReference basicDetails;

    FirebaseUser firebaseUser;

    FirebaseAuth auth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        basicDetails = FirebaseDatabase.getInstance().getReference("Citizens").child(firebaseUser.getUid());



        getSupportActionBar().setTitle("Create an Account");
        Toast.makeText(Address.this, "You can Register Now", Toast.LENGTH_SHORT).show();

        progressBar = findViewById(R.id.progressBarAddressCitizen);
        editBarangay = findViewById(R.id.editAddressBRVictim);
        editStreet = findViewById(R.id.editAddressSTVictim);
        editPurok = findViewById(R.id.editAddressHSVictim);
        editCity = findViewById(R.id.editAddressCityVictim);
        editProvince = findViewById(R.id.editAddressPRVictim2);
        editCivilStatus = findViewById(R.id.editCivilStatus);
        editBloodType = findViewById(R.id.editBloodType);
        radioGroupGender = findViewById(R.id.GenderGroup);
        radioGroupGender.clearCheck();

        victimNext = findViewById(R.id.VictimNext1);
        victimNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonSelectedGender = findViewById(selectedGenderId);

                String textPurok = editPurok.getText().toString();
                String textBarangay = editBarangay.getText().toString();
                String textStreet = editStreet.getText().toString();
                String textCity = editCity.getText().toString();
                String textProvince = editProvince.getText().toString();
                String textCivilStatus = editCivilStatus.getText().toString();
                String textBloodType = editBloodType.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textPurok)) {
                    Toast.makeText(Address.this, "Please enter your Purok/House No.", Toast.LENGTH_LONG).show();
                    editPurok.setError("Purok/House No. is Required");
                    editPurok.requestFocus();
                } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Address.this, "Please Select your Gender", Toast.LENGTH_LONG).show();
                    radioButtonSelectedGender.setError("Gender is Required");
                    radioButtonSelectedGender.requestFocus();
                } else if (TextUtils.isEmpty(textBarangay)) {
                    Toast.makeText(Address.this, "Please enter your Barangay", Toast.LENGTH_LONG).show();
                    editBarangay.setError("Barangay is Required");
                    editBarangay.requestFocus();
                } else if (TextUtils.isEmpty(textStreet)) {
                    Toast.makeText(Address.this, "Please enter your Street", Toast.LENGTH_LONG).show();
                    editStreet.setError("Street is Required");
                    editStreet.requestFocus();
                } else if (TextUtils.isEmpty(textCity)) {
                    Toast.makeText(Address.this, "Please enter your City/Municiaplity", Toast.LENGTH_LONG).show();
                    editCity.setError("City/Municipality is Required");
                    editCity.requestFocus();
                } else if (TextUtils.isEmpty(textProvince)) {
                    Toast.makeText(Address.this, "Please enter your Province", Toast.LENGTH_LONG).show();
                    editProvince.setError("Province is Required");
                    editProvince.requestFocus();
                } else if (TextUtils.isEmpty(textCivilStatus)) {
                    Toast.makeText(Address.this, "Please enter your Civil Status", Toast.LENGTH_LONG).show();
                    editCivilStatus.setError("Civil Status is Required");
                    editCivilStatus.requestFocus();
                } else if (TextUtils.isEmpty(textBloodType)) {
                    Toast.makeText(Address.this, "Please enter your Blood Type", Toast.LENGTH_LONG).show();
                    editBloodType.setError("Blood Type is Required");
                    editBloodType.requestFocus();
                } else {
                    textGender = radioButtonSelectedGender.getText().toString();
                    progressBar.setVisibility(view.VISIBLE);
                    AddressCitizen(textPurok, textBarangay, textStreet, textCity, textProvince);
                    ExtraDetails( textGender, textBloodType, textCivilStatus);
                }

            }
        });

        victimBack = findViewById(R.id.VictimBack1);
        victimBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Address.this, create_accountCitizen.class);
                startActivity(intent);
                {
                }
            }
        });
    }

    private void AddressCitizen(String textPurok, String textBarangay, String textStreet, String textCity, String textProvince) {


        //Enter data into realtime database
        ReadWriteAdressDetails writeAddressDetails = new ReadWriteAdressDetails(textPurok, textStreet, textCity, textProvince, textBarangay);

        //Database Reference
        basicDetails = FirebaseDatabase.getInstance().getReference("Citizens").child(firebaseUser.getUid()).child("Address");

        basicDetails.child(firebaseUser.getUid()).child("Address").setValue(writeAddressDetails);


    }

    private void ExtraDetails(String textGender, String textBloodType,String  textCivilStatus){

        ReadWriteExtraDetails writeExtraDetails = new ReadWriteExtraDetails(textGender, textBloodType, textCivilStatus);

        basicDetails = FirebaseDatabase.getInstance().getReference("Citizens").child(firebaseUser.getUid());

        basicDetails.child(firebaseUser.getUid()).setValue(writeExtraDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(Address.this, EmergencyContact.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        progressBar.setVisibility(View.GONE);

    }

}
