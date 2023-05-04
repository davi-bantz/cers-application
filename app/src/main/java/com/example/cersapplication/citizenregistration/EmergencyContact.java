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
import android.widget.Toast;

import com.example.cersapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmergencyContact extends AppCompatActivity {

    private EditText editECName, editECNumber, editECPurok, editECStreet, editECBarangay, editECCity, editECProvince;

    private ProgressBar progressBar;

    //clarify the present address issue

    Button Confirm;
    Button Return;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        editECName = findViewById(R.id.editEContactName);
        editECNumber = findViewById(R.id.editEPhoneNumber);
        editECPurok = findViewById(R.id.editAddressHSEmerCon);
        editECStreet = findViewById(R.id.editAddressSTEmerCon);
        editECBarangay = findViewById(R.id.editAddressBREmerCON);
        editECCity = findViewById(R.id.editAddressCityEmerCon);
        editECProvince = findViewById(R.id.editAddressPREmerCon);
        progressBar = findViewById(R.id.progressBarEmerCon);

        Confirm = findViewById(R.id.proceedConfirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyContact.this, Confirmation.class);
                startActivity(intent);

                    String textEmerName = editECName.getText().toString();
                    String textEmerNumber = editECNumber.getText().toString();
                    String textEmerPurok = editECPurok.getText().toString();
                    String textEmerStreet = editECStreet.getText().toString();
                    String textEmerBarangay = editECBarangay.getText().toString();
                    String textEmerCity = editECCity.getText().toString();
                    String textEmerProvince = editECProvince.getText().toString();

                    if (TextUtils.isEmpty(textEmerName)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Name", Toast.LENGTH_LONG).show();
                        editECName.setError("Emergency Contact's Name is Required");
                        editECName.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerNumber)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Phone Number", Toast.LENGTH_LONG).show();
                        editECNumber.setError("Emergency Contact's Number is Required");
                        editECNumber.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerPurok)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Purok/House No.", Toast.LENGTH_LONG).show();
                        editECPurok.setError("Emergency Contact's Purok/House No. is Required");
                        editECPurok.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerStreet)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Street", Toast.LENGTH_LONG).show();
                        editECStreet.setError("Emergency Contact's Street is Required");
                        editECStreet.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerBarangay)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Barangay", Toast.LENGTH_LONG).show();
                        editECBarangay.setError("Emergency Contact's Barangay is Required");
                        editECBarangay.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerCity)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's City/Municipality", Toast.LENGTH_LONG).show();
                        editECCity.setError("Emergency Contact's City/Municipality is Required");
                        editECCity.requestFocus();
                    } else if (TextUtils.isEmpty(textEmerProvince)) {
                        Toast.makeText(EmergencyContact.this, "Please enter your Emergency Contact's Province}", Toast.LENGTH_LONG).show();
                        editECProvince.setError("Emergency Contact's Province is Required");
                        editECProvince.requestFocus();
                    } else{
                        progressBar.setVisibility(view.VISIBLE);
                        registerUser(textEmerName, textEmerNumber, textEmerPurok, textEmerStreet, textEmerBarangay, textEmerNumber, textEmerProvince);
                    }

            }
        });

        Return = findViewById(R.id.ReturnHP);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmergencyContact.this, Address.class);
                startActivity(intent);{
                }
            }
        });


    }
    private void registerUser(String textEmerName, String textEmerNumber, String textEmerPurok, String textEmerStreet, String textEmerBarangay, String textEmerCity, String textEmerProvince) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        firebaseUser.getUid();


        //Enter data into realtime database
        EmergencyC writeEmerDetails = new EmergencyC(textEmerName, textEmerNumber, textEmerPurok, textEmerStreet, textEmerBarangay, textEmerCity, textEmerProvince);

        //database Reference
        final DatabaseReference basicDetails = FirebaseDatabase.getInstance().getReference("Citizens");

        basicDetails.child(firebaseUser.getUid()).setValue(writeEmerDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(EmergencyContact.this, Confirmation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                {

                }

                progressBar.setVisibility(View.GONE);
            }


        });
    }




}

