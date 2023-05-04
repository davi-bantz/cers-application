package com.example.cersapplication.UpdateInfoCitizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.example.cersapplication.citizenProfile.CitizenBasicDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Update_Email extends AppCompatActivity {

    private FirebaseAuth authprofile;
    private FirebaseUser firebaseUser;
    private TextView authenticated;
    private Button buttonupdateEmail;
    private EditText newEmail, passWD;
    private String updatedEmail, previousEmail, password;
    private ProgressBar progressbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        //layout variables
        newEmail = findViewById(R.id.Citizen_NewEmail);
        passWD = findViewById(R.id.Enter_Current_Password);
        progressbar = findViewById(R.id.progressBar_Authenticate);
        authenticated = findViewById(R.id.textView10);
        buttonupdateEmail = findViewById(R.id.Update_New_Email);



        //button configurations
        buttonupdateEmail.setEnabled(false);
        newEmail.setEnabled(false);

        //get user
        authprofile = FirebaseAuth.getInstance();
        firebaseUser = authprofile.getCurrentUser();

        //set old email
        previousEmail = firebaseUser.getEmail();
        TextView oldemailview = findViewById(R.id.Citizen_CurrentEmail);
        oldemailview.setText(previousEmail);

        if (firebaseUser.equals("")){
            Toast.makeText(Update_Email.this, "Something went wrong. User's details are not available!", Toast.LENGTH_LONG).show();
        } else {
            reAuthenticateUser(firebaseUser);
        }
    }

    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        Button verifyUser = findViewById(R.id.Authenticate_Account);
        verifyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Obtain password for authentication
                password = passWD.getText().toString();

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Update_Email.this, "Password is needed to Continue", Toast.LENGTH_SHORT).show();
                    passWD.setError("Please enter password for authentication");
                    passWD.requestFocus();
                } else{
                    progressbar.setVisibility(View.VISIBLE);

                    //re-authenticate
                    AuthCredential usercredentials = EmailAuthProvider.getCredential(previousEmail, password);
                    firebaseUser.reauthenticate(usercredentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressbar.setVisibility(View.GONE);

                                Toast.makeText(Update_Email.this, "You have been Verified. Proceed to edit your email", Toast.LENGTH_SHORT).show();

                                //Set text to be verified
                                authenticated.setText("You have been Verified!");

                                //disabled the things in authentication and activate the new email part
                                newEmail.setEnabled(true);
                                buttonupdateEmail.setEnabled(true);
                                passWD.setEnabled(false);
                                verifyUser.setEnabled(false);

                                //enter new email
                                buttonupdateEmail.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        updatedEmail = newEmail.getText().toString();

                                        if(TextUtils.isEmpty(updatedEmail)){
                                            Toast.makeText(Update_Email.this, "Email is Required", Toast.LENGTH_SHORT).show();
                                            newEmail.setError("Please enter your Email");
                                            newEmail.requestFocus();
                                        } else if (!Patterns.EMAIL_ADDRESS.matcher(updatedEmail).matches()){
                                            Toast.makeText(Update_Email.this, "Valid Email is required", Toast.LENGTH_SHORT).show();
                                            newEmail.setError("Please enter a valid Email");
                                            newEmail.requestFocus();
                                        } else if (previousEmail.matches(updatedEmail)){
                                            Toast.makeText(Update_Email.this, "New Email cannot be the same as old", Toast.LENGTH_SHORT).show();
                                            newEmail.setError("Please enter a new Email");
                                            newEmail.requestFocus();
                                        } else{
                                            progressbar.setVisibility(View.VISIBLE);
                                            updateUserEmail(firebaseUser);
                                        }
                                    }
                                });

                            } else{
                                try{
                                    throw task.getException();
                                } catch(Exception e){
                                    Toast.makeText(Update_Email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUserEmail(FirebaseUser firebaseUser) {
        firebaseUser.updateEmail(updatedEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){

                    //Verify Email
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(Update_Email.this, "Email has been updated. Please verify your Email!", Toast.LENGTH_SHORT).show();
                    Intent goback = new Intent(Update_Email.this, CitizenBasicDetails.class);
                    startActivity(goback);
                    finish();
                } else{
                    try{
                        throw task.getException();
                    } catch (Exception e){
                        Toast.makeText(Update_Email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressbar.setVisibility(View.GONE);
                }
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
            Intent changeP = new Intent(Update_Email.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(Update_Email.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(Update_Email.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(Update_Email.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(Update_Email.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}