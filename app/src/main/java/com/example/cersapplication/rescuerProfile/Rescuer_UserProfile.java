package com.example.cersapplication.rescuerProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.example.cersapplication.rescuerProfile.UpdateInfoRescuer.RescuerUpdate_Basic_Details;
import com.example.cersapplication.citizenregistration.ReadWriteExtraDetails;
import com.example.cersapplication.citizenregistration.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rescuer_UserProfile extends AppCompatActivity {

    private TextView citizenName, citizenEmail, citizenUsername, citizenPhoneNumber, citizenDoB, citizenCivilStatus, citizenGender, citizenBloodtype;

    private ProgressBar progressbaredit;

    private String fullname, email, username, phonenumber, Dob, gender, civilstatus, bloodtype;
    private FirebaseAuth authUser;

    private Button updatebasicdetails;
    private ImageButton backtoSetting;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer_user_profile);

        citizenName = findViewById(R.id.Show_Cit_NAme);
        citizenEmail = findViewById(R.id.Show_Cit_Email);
        citizenUsername = findViewById(R.id.Show_Cit_UN);
        citizenPhoneNumber = findViewById(R.id.Show_Cit_Phone_Number);
        citizenDoB = findViewById(R.id.Show_Cit_Bod);
        citizenGender = findViewById(R.id.Show_Cit_Gender);
        citizenCivilStatus = findViewById(R.id.Show_Cit_Address);
        citizenBloodtype = findViewById(R.id.Show_Cit_BloodType);
        updatebasicdetails = findViewById(R.id.Update_BasicDetails);
        progressbaredit = findViewById(R.id.progressBarEditProfile);
        backtoSetting = findViewById(R.id.imageButtonforbacktoSetting);

        //back to setting
        backtoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoSettings = new Intent(Rescuer_UserProfile.this, RescuerSettings.class);
                startActivity(backtoSettings);
                finish();
            }
        });

        //update basic details
        updatebasicdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdateDetails = new Intent(Rescuer_UserProfile.this, RescuerUpdate_Basic_Details.class);
                startActivity(gotoUpdateDetails);
            }
        });

        //get the current user
        authUser = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authUser.getCurrentUser();

        //account conditions
        if (firebaseUser == null) {
            Toast.makeText(Rescuer_UserProfile.this, "Something is wrong. User details are not available at the moment!",
                    Toast.LENGTH_LONG).show();
        } else{
            checkIfEmailIsVerified(firebaseUser);
            progressbaredit.setVisibility(View.VISIBLE);
            showCitizenProfile(firebaseUser);
        }

    }

    private void checkIfEmailIsVerified(FirebaseUser firebaseUser) {
        if(!firebaseUser.isEmailVerified()){
            verifyEmailAlert();
        }
    }

    private void verifyEmailAlert() {
        //set up alert builder
        AlertDialog.Builder verifyEmail = new AlertDialog.Builder(Rescuer_UserProfile.this);
        verifyEmail.setTitle("Email not Verified");
        verifyEmail.setMessage("Please Verify your Email Address now. You cannot login without Email Verification next time");

        //Open email app once clicked accept
        verifyEmail.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentverify = new Intent(Intent.ACTION_MAIN);
                intentverify.addCategory(Intent.CATEGORY_APP_EMAIL);
                intentverify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentverify);
            }
        });

        //Create the alert dialog box
        AlertDialog verify = verifyEmail.create();
        verify.show();
    }

    private void showCitizenProfile(FirebaseUser firebaseUser) {
        String getUserID = firebaseUser.getUid();

        //extract data from database
        DatabaseReference referenceCitizen = FirebaseDatabase.getInstance().getReference("Rescuers");
        referenceCitizen.child(getUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails basicDetails = snapshot.getValue(ReadWriteUserDetails.class);
                ReadWriteExtraDetails extraDetails = snapshot.getValue(ReadWriteExtraDetails.class);

                if (basicDetails != null) {
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    Dob = basicDetails.dob;
                    username = basicDetails.Username;
                    phonenumber = basicDetails.Phone;

                    //textView
                    citizenName.setText(fullname);
                    citizenEmail.setText(email);
                    citizenDoB.setText(Dob);
                    citizenUsername.setText(username);
                    citizenPhoneNumber.setText(phonenumber);

                } else if (extraDetails != null) {
                    gender = extraDetails.Gender;
                    bloodtype = extraDetails.BloodType;
                    civilstatus = extraDetails.CivilStatus;

                    //textView
                    citizenGender.setText(gender);
                    citizenBloodtype.setText(bloodtype);
                    citizenCivilStatus.setText(civilstatus);

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
            Intent changeP = new Intent(Rescuer_UserProfile.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(Rescuer_UserProfile.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authUser.signOut();
            Toast.makeText(Rescuer_UserProfile.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(Rescuer_UserProfile.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(Rescuer_UserProfile.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
