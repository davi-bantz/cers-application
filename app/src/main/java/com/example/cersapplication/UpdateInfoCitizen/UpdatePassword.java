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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.citizenProfile.CitizenProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private FirebaseAuth authprofile;
    private EditText editCurrentPass, editNewPassword, editConfirmPass;
    private String currentPassWD;
    private TextView accountverified;
    private Button authenticatePass, changePasswordBtn;
    private ProgressBar progressBarPasswrd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        //layout declare
        editCurrentPass = findViewById(R.id.Authenticate_Current_Password);
        editNewPassword = findViewById(R.id.Citizen_NewPassword);
        editConfirmPass = findViewById(R.id.Citizen_Confirm_NewPassword);
        accountverified = findViewById(R.id.textView_authenticated_Pass);
        authenticatePass = findViewById(R.id.Authenticate_PassWD_Account);
        changePasswordBtn =  findViewById(R.id.Update_New_Password);
        progressBarPasswrd = findViewById(R.id.progressBar_Authenticate_Pass);




        //disable new password part
        editConfirmPass.setEnabled(false);
        editNewPassword.setEnabled(false);
        changePasswordBtn.setEnabled(false);

        //get user
        authprofile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authprofile.getCurrentUser();

        //check if user is null
        if (firebaseUser.equals("")){
            Toast.makeText(this, "Something went wrong. User's details are not available!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdatePassword.this, CitizenProfile.class);
            startActivity(intent);
            finish();
        } else{
            reAuthenticateUser(firebaseUser);
            
        }


    }
    //authenticate user before changing password
    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        authenticatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPassWD = editCurrentPass.getText().toString();
                
                if(TextUtils.isEmpty(currentPassWD)){
                    Toast.makeText(UpdatePassword.this, "Password is required.", Toast.LENGTH_SHORT).show();
                    editCurrentPass.setError("Please enter your current password to authenticate");
                    editCurrentPass.requestFocus();
                } else {
                    progressBarPasswrd.setVisibility(View.VISIBLE);

                    //reauthenticate user
                    AuthCredential usercredentials = EmailAuthProvider.getCredential(firebaseUser.getEmail(), currentPassWD);

                    firebaseUser.reauthenticate(usercredentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBarPasswrd.setVisibility(View.GONE);

                                //disable authenticate, enable change password
                                editCurrentPass.setEnabled(false);
                                authenticatePass.setEnabled(false);
                                editNewPassword.setEnabled(true);
                                editConfirmPass.setEnabled(true);
                                changePasswordBtn.setEnabled(true);

                                //setTextview
                                accountverified.setText("Your Account has been Verified");
                                Toast.makeText(UpdatePassword.this, "Password is authenticated." + "Change Password Now", Toast.LENGTH_SHORT).show();

                                changePasswordBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        changePass(firebaseUser);

                                    }
                                });
                            } else {
                                try{
                                    throw task.getException();
                                } catch (Exception e){
                                    Toast.makeText(UpdatePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressBarPasswrd.setVisibility(View.GONE);
                            }
                            
                        }
                    });
                }
            }
        });
    }

    private void changePass(FirebaseUser firebaseUser) {
        String usernewPass = editNewPassword.getText().toString();
        String confirmnewPass = editConfirmPass.getText().toString();

     if (TextUtils.isEmpty(usernewPass)) {
        Toast.makeText(UpdatePassword.this, "Please enter your Password", Toast.LENGTH_LONG).show();
        editNewPassword.setError("Password is Required");
        editNewPassword.requestFocus();
    } else if (usernewPass.length() < 8) {
        Toast.makeText(UpdatePassword.this, "Password should be at least 8 digits", Toast.LENGTH_LONG).show();
        editNewPassword.setError("Password too weak");
        editNewPassword.requestFocus();
    } else if (TextUtils.isEmpty(confirmnewPass)) {
        Toast.makeText(UpdatePassword.this, "Please confirm your Password", Toast.LENGTH_LONG).show();
        editConfirmPass.setError("Password Confirmation is Required");
        editConfirmPass.requestFocus();
    } else if (usernewPass.matches(currentPassWD)){
         editNewPassword.setError("New Password cannot be the same as old");
         editNewPassword.requestFocus();
     } else if (!usernewPass.equals(confirmnewPass)) {
        Toast.makeText(UpdatePassword.this, "Please re-confirm Password", Toast.LENGTH_LONG).show();
        editConfirmPass.setError("Password does not match");
        editConfirmPass.requestFocus();
        editNewPassword.clearComposingText();
        editConfirmPass.clearComposingText();
    } else {
         progressBarPasswrd.setVisibility(View.VISIBLE);

         firebaseUser.updatePassword(usernewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()){
                     Toast.makeText(UpdatePassword.this, "Password successfully changed.", Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(UpdatePassword.this, CitizenProfile.class);
                     startActivity(intent);
                     finish();
                 } else {
                     try{
                         throw task.getException();
                     } catch (Exception e){
                         Toast.makeText(UpdatePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                     progressBarPasswrd.setVisibility(View.GONE);
                 }
             }
         });
     }
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
            Intent changeP = new Intent(UpdatePassword.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(UpdatePassword.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(UpdatePassword.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(UpdatePassword.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(UpdatePassword.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}