package com.example.cersapplication.citizenProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.example.cersapplication.citizenregistration.ReadWriteUserDetails;
import com.example.cersapplication.rescuerProfile.RescuerSettings;
import com.example.cersapplication.rescuerProfile.Rescuer_Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SettingsCitizenProfile extends AppCompatActivity {

    private ImageView profilephoto;

    private FirebaseAuth authPhoto ;

    private TextView CitizenNameSettings;

    private String settingsCitizenName;

    private ProgressBar progressBarSettings;

    private ImageButton nexttoUserProf, nexttoCitAddress, nexttoEmerContact;

    private Button updatePhoto, backtoUserProfile;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_citizen_profile);

        //get current user
        authPhoto = FirebaseAuth.getInstance();
        FirebaseUser CitizenProfilePhoto = authPhoto.getCurrentUser();

        //layout declaration
        CitizenNameSettings = findViewById(R.id.FullNameCitizen);
        progressBarSettings = findViewById(R.id.progressBarSettingsCitizen);
        nexttoUserProf = findViewById(R.id.imageButtonforCitizenProfile);
        nexttoCitAddress = findViewById(R.id.imageButtonforCitizenAddress);
        nexttoEmerContact = findViewById(R.id.imageButtonforEmergencyContact);
        updatePhoto = findViewById(R.id.Update_Profile_Picture);
        backtoUserProfile =  findViewById(R.id.back_to_user_profile_btn);

        //back to profile
        backtoUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoSettings = new Intent(SettingsCitizenProfile.this, CitizenProfile.class);
                startActivity(backtoSettings);
                finish();
            }
        });

        //update profile picture
        updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePhoto = new Intent(SettingsCitizenProfile.this, UploadProfilePicture.class);
                startActivity(changePhoto);
                finish();
            }
        });

        //button to user profile
        nexttoUserProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUserProf = new Intent(SettingsCitizenProfile.this, CitizenBasicDetails.class);
                startActivity(gotoUserProf);
            }
        });

        //button to user address
        nexttoCitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoAddress = new Intent(SettingsCitizenProfile.this, Citizen_Address.class);
                startActivity(gotoAddress);
            }
        });

        //button to emergency contact
        nexttoEmerContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoEmerCon = new Intent(SettingsCitizenProfile.this, Citizen_Emergency_Contact.class);
                startActivity(gotoEmerCon);
            }
        });


        profilephoto =findViewById(R.id.ProfilePhoto);
        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadPhoto = new Intent(SettingsCitizenProfile.this, UploadProfilePicture.class);
                startActivity(uploadPhoto);
            }
        });

        if(CitizenProfilePhoto !=null){
            Toast.makeText(SettingsCitizenProfile.this, "Something is wrong. User details are not available at the moment!",
                    Toast.LENGTH_LONG).show();
        } else{
            progressBarSettings.setVisibility(View.VISIBLE);
            showCitizenPhotorofile(CitizenProfilePhoto);
    }
}
    private void showCitizenPhotorofile(FirebaseUser CitizenProfilePhoto) {
        String getSUserIDforPhoto = CitizenProfilePhoto.getUid();

        //extractdata from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Citizens").child("Display Pics");
        reference.child(getSUserIDforPhoto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails namedetails = snapshot.getValue(ReadWriteUserDetails.class);

                if (namedetails != null) {
                    settingsCitizenName = CitizenProfilePhoto.getDisplayName();

                    //set to textview
                    CitizenNameSettings.setText(settingsCitizenName);

                    //view image
                    Uri uriImageViwew = CitizenProfilePhoto.getPhotoUrl();
                    Picasso.with(SettingsCitizenProfile.this).load(uriImageViwew).into(profilephoto);
                } else{
                    Toast.makeText(SettingsCitizenProfile.this, "Something went wrong.",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
            Intent changeP = new Intent(SettingsCitizenProfile.this, UpdatePassword.class);
            startActivity(changeP);
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(SettingsCitizenProfile.this, DeleteAccount.class);
            startActivity(deleteAcc);
        }else if(id == R.id.menu_log_out){
            //log out
            authPhoto.signOut();
            Toast.makeText(SettingsCitizenProfile.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(SettingsCitizenProfile.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(SettingsCitizenProfile.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
