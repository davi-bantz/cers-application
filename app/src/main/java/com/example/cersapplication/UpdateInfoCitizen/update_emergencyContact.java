package com.example.cersapplication.UpdateInfoCitizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.citizenProfile.Citizen_Emergency_Contact;
import com.example.cersapplication.citizenregistration.EmergencyC;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class update_emergencyContact extends AppCompatActivity {

    private EditText updateECName,  updatephonenumber, updateECPurok, updateECStreet, updateECBarangay, updateECCity, updateECProvince;
    private String ecName, ecPhone, ecPurok, ecStreet, ecBarangay, ecCity, ecProvince;
    private FirebaseAuth authprofile;
    private ProgressBar progressBar_updateECdetails;

    private ImageButton backtoEC;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_emergency_contact);

        updateECName = findViewById(R.id.Edit_Emergency_Name);
        updatephonenumber = findViewById(R.id.Edit_Emergency_Phone);
        updateECPurok = findViewById(R.id.Edit_Emergency_Purok);
        updateECStreet = findViewById(R.id.Edit_Emergency_Street);
        updateECBarangay =  findViewById(R.id.Edit_Emergency_Barangay);
        updateECCity =  findViewById(R.id.Edit_Emergency_City);
        updateECProvince = findViewById(R.id.Edit_Emergency_Province);
        progressBar_updateECdetails = findViewById(R.id.progressBar_UpdateEC);
        backtoEC = findViewById(R.id.imageButtonforbacktoEc);

        backtoEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoSettings = new Intent(update_emergencyContact.this, Citizen_Emergency_Contact.class);
                startActivity(backtoSettings);
                finish();
            }
        });

        //get current user
        authprofile = FirebaseAuth.getInstance();
        FirebaseUser presentUser = authprofile.getCurrentUser();

        //Button to update
        Button updateEC = findViewById(R.id.Update_ECDetails);
        updateEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmergencyContact(presentUser);
            }
        });

        //show data in edit text
        showEmergencyContact(presentUser);

    }
    //update emergency contact
    private void updateEmergencyContact(FirebaseUser presentUser) {

        //validate the phone number
        String mobileNumber = "((^(\\+)(\\d){12}$)|(^\\d{11}$))";
        Matcher mobilematcher;
        Pattern mobilePatt = Pattern.compile(mobileNumber);
        mobilematcher = mobilePatt.matcher(ecPhone);

        if (TextUtils.isEmpty(ecName)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Name", Toast.LENGTH_LONG).show();
            updateECName.setError("Emergency Contact's Name is Required");
            updateECName.requestFocus();
        } else if (TextUtils.isEmpty(ecPhone)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Emergency Contact's Number is Required");
            updatephonenumber.requestFocus();
        } else if (ecPhone.length() != 11) {
            Toast.makeText(update_emergencyContact.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Phone Number must be 11 digits");
            updatephonenumber.requestFocus();
        } else if (!mobilematcher.find()) {
            Toast.makeText(update_emergencyContact.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Phone Number is not valid");
            updatephonenumber.requestFocus();
        } else if (TextUtils.isEmpty(ecPurok)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Purok/House No.", Toast.LENGTH_LONG).show();
            updateECPurok.setError("Emergency Contact's Purok/House No. is Required");
            updateECPurok.requestFocus();
        } else if (TextUtils.isEmpty(ecStreet)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Street", Toast.LENGTH_LONG).show();
            updateECStreet.setError("Emergency Contact's Street is Required");
            updateECStreet.requestFocus();
        } else if (TextUtils.isEmpty(ecBarangay)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Barangay", Toast.LENGTH_LONG).show();
            updateECBarangay.setError("Emergency Contact's Barangay is Required");
            updateECBarangay.requestFocus();
        } else if (TextUtils.isEmpty(ecCity)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's City/Municipality", Toast.LENGTH_LONG).show();
            updateECCity.setError("Emergency Contact's City/Municipality is Required");
            updateECCity.requestFocus();
        } else if (TextUtils.isEmpty(ecProvince)) {
            Toast.makeText(update_emergencyContact.this, "Please enter your Emergency Contact's Province}", Toast.LENGTH_LONG).show();
            updateECProvince.setError("Emergency Contact's Province is Required");
            updateECProvince.requestFocus();
        } else {
            ecName = updateECName.getText().toString();
            ecPhone =  updatephonenumber.getText().toString();
            ecPurok = updateECPurok.getText().toString();
            ecStreet = updateECStreet.getText().toString();
            ecBarangay = updateECStreet.getText().toString();
            ecCity = updateECCity.getText().toString();
            ecProvince = updateECProvince.getText().toString();

            EmergencyC updatedEC = new EmergencyC(ecName, ecPhone, ecPurok, ecStreet,ecBarangay,ecCity,ecProvince);

            //get Database Reference
            DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Citizens");

            //Get UID of current user
            String UserID = authprofile.getUid();
            progressBar_updateECdetails.setVisibility(View.VISIBLE);

            //enter data into database
            referenceUser.child(UserID).child("Emergency Contact").setValue(updatedEC).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(update_emergencyContact.this, "Update Succesfull!", Toast.LENGTH_LONG).show();
                        Intent goCitBD = new Intent(update_emergencyContact.this, Citizen_Emergency_Contact.class);
                        goCitBD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(update_emergencyContact.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar_updateECdetails.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    //fetch data from database
    private void showEmergencyContact(FirebaseUser presentUser) {
        String presentUID = authprofile.getUid();

        //databaseReference
        DatabaseReference emergencyReference = FirebaseDatabase.getInstance().getReference("Citizens");
        progressBar_updateECdetails.setVisibility(View.VISIBLE);

        emergencyReference.child(presentUID).child("Emergency Contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EmergencyC emergencyContact = snapshot.getValue(EmergencyC.class);

                if (emergencyContact !=null ){
                    ecName = emergencyContact.ECName;
                    ecPhone = emergencyContact.ECPhone;
                    ecPurok = emergencyContact.ECPurok;
                    ecStreet = emergencyContact.ECStreet;
                    ecBarangay = emergencyContact.ECBarangay;
                    ecCity = emergencyContact.ECCity;
                    ecProvince = emergencyContact.ECProvince;

                    //set text to textviews
                    updateECName.setText(ecName);
                    updatephonenumber.setText(ecPhone);
                    updateECPurok.setText(ecPurok);
                    updateECStreet.setText(ecStreet);
                    updateECBarangay.setText(ecBarangay);
                    updateECCity.setText(ecCity);
                    updateECProvince.setText(ecProvince);

                }else{
                    Toast.makeText(update_emergencyContact.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
                progressBar_updateECdetails.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_emergencyContact.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });



    }

    //create action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_refresh){
            //Refresh page
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        } else if (id == R.id.menu_password){
            //change pass
            Intent changeP = new Intent(update_emergencyContact.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(update_emergencyContact.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(update_emergencyContact.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(update_emergencyContact.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(update_emergencyContact.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }

}