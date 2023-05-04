package com.example.cersapplication.UpdateInfoCitizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class forgotPassword extends AppCompatActivity {

    private EditText currentEmail;
    private Button sendLink;
    private ProgressBar progressBarResetPass;
    private FirebaseAuth authprofile;

    private final static String TAG = "forgorPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        currentEmail = findViewById(R.id.CurrentEmail_forResetPass);
        sendLink = findViewById(R.id.Btn_sendlinkforreset);
        progressBarResetPass = findViewById(R.id.progressBar_sendLink);

        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = currentEmail.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(forgotPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    currentEmail.setError("Email is required");
                    currentEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(forgotPassword.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    currentEmail.setError("Valid Email is required");
                    currentEmail.requestFocus();
                } else{
                    progressBarResetPass.setVisibility(View.VISIBLE);
                    resetpassword(email);
                }
            }
        });
    }

    private void resetpassword(String email) {
        authprofile = FirebaseAuth.getInstance();
        authprofile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgotPassword.this, "Please check email for password reset link", Toast.LENGTH_SHORT).show();

                    Intent gobackMain = new Intent(forgotPassword.this, MainActivity.class);
                    gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(gobackMain);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        currentEmail.setError("User does not exist. Please Register again");
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(forgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBarResetPass.setVisibility(View.GONE);
            }
        });

    }
}