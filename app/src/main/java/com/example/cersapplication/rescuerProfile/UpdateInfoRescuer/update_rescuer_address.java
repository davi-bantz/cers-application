package com.example.cersapplication.rescuerProfile.UpdateInfoRescuer;

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
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.example.cersapplication.citizenProfile.Citizen_Address;
import com.example.cersapplication.citizenregistration.ReadWriteAdressDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update_rescuer_address extends AppCompatActivity {

    private EditText updatepurok, updateStreet, updateBarangay, updateCity, updateProvince;
    private String purok, street, barangay, city, province;
    private FirebaseAuth authprofile;
    private ProgressBar progressBar_addressProg;
    private ImageButton backtoaddressProf;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rescuer_address);


        //layout declaration
        updatepurok = findViewById(R.id.Edit_Rescuer_Purok);
        updateStreet = findViewById(R.id.Edit_Rescuer_Street);
        updateBarangay = findViewById(R.id.Edit_Rescuer_Barangay);
        updateCity = findViewById(R.id.Edit_Rescuer_City);
        updateProvince = findViewById(R.id.Edit_Rescuer_Province);
        progressBar_addressProg = findViewById(R.id.progressBar_UpdateADDRes);
        backtoaddressProf =  findViewById(R.id.imageButtonforbacktoSetting);

        //back to address
        backtoaddressProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoUserAddress = new Intent(update_rescuer_address.this, Citizen_Address.class);
                startActivity(backtoUserAddress);
                finish();
            }
        });

        //firebase user
        authprofile = FirebaseAuth.getInstance();
        FirebaseUser current_address = authprofile.getCurrentUser();

        //show address data
        showCitizenAddress(current_address);

        Button updateADDDetails = findViewById(R.id.Update_AddResBtn);
        updateADDDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateaddress(current_address);
            }
        });
    }

    private void updateaddress(FirebaseUser current_address) {

        if (TextUtils.isEmpty(purok)) {
            Toast.makeText(update_rescuer_address.this, "Please enter your Purok/House No.", Toast.LENGTH_LONG).show();
            updatepurok.setError("Purok/House No. is Required");
            updatepurok.requestFocus();
        } else if (TextUtils.isEmpty(barangay)) {
            Toast.makeText(update_rescuer_address.this, "Please enter your Barangay", Toast.LENGTH_LONG).show();
            updateBarangay.setError("Barangay is Required");
            updateBarangay.requestFocus();
        } else if (TextUtils.isEmpty(street)) {
            Toast.makeText(update_rescuer_address.this, "Please enter your Street", Toast.LENGTH_LONG).show();
            updateStreet.setError("Street is Required");
            updateStreet.requestFocus();
        } else if (TextUtils.isEmpty(city)) {
            Toast.makeText(update_rescuer_address.this, "Please enter your City/Municiaplity", Toast.LENGTH_LONG).show();
            updateCity.setError("City/Municipality is Required");
            updateCity.requestFocus();
        } else if (TextUtils.isEmpty(province)) {
            Toast.makeText(update_rescuer_address.this, "Please enter your Province", Toast.LENGTH_LONG).show();
            updateProvince.setError("Province is Required");
            updateProvince.requestFocus();
        } else {
            //obtain info
            purok = updatepurok.getText().toString();
            street = updateStreet.getText().toString();
            barangay = updateBarangay.getText().toString();
            city = updateCity.getText().toString();
            province = updateProvince.getText().toString();

            //update data in realtime database
            ReadWriteAdressDetails updateaddress = new ReadWriteAdressDetails(purok, street, barangay, city, province);

            //get database reference
            DatabaseReference referenceuser = FirebaseDatabase.getInstance().getReference("Citizens");

            //get User id
            String userAddressID = current_address.getUid();
            progressBar_addressProg.setVisibility(View.VISIBLE);

            referenceuser.child(userAddressID).child("Address").setValue(updateaddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(update_rescuer_address.this, "Update Succesfull!", Toast.LENGTH_LONG).show();
                        Intent goCitBD = new Intent(update_rescuer_address.this, Citizen_Address.class);
                        goCitBD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(update_rescuer_address.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar_addressProg.setVisibility(View.GONE);
                    }
                }
            });



        }
    }

    private void showCitizenAddress(FirebaseUser current_address) {
        String UserID = current_address.getUid();

        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Citizens");
        progressBar_addressProg.setVisibility(View.VISIBLE);

        referenceprofile.child(UserID).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteAdressDetails citizenaddress = snapshot.getValue(ReadWriteAdressDetails.class);

                if (citizenaddress != null){
                    purok = citizenaddress.Purok;
                    street = citizenaddress.Street;
                    barangay = citizenaddress.Barangay;
                    city = citizenaddress.City;
                    province = citizenaddress.Province;

                    //set text view
                    updatepurok.setText(purok);
                    updateStreet.setText(street);
                    updateBarangay.setText(barangay);
                    updateCity.setText(city);
                    updateProvince.setText(province);

                }else{
                    Toast.makeText(update_rescuer_address.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
                progressBar_addressProg.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(update_rescuer_address.this, "Something went wrong!", Toast.LENGTH_LONG).show();
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
            Intent changeP = new Intent(update_rescuer_address.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(update_rescuer_address.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(update_rescuer_address.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(update_rescuer_address.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(update_rescuer_address.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}