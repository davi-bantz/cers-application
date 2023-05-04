package com.example.cersapplication.MainParts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.util.Log;
import android.widget.ImageView;
import android.text.method.HideReturnsTransformationMethod;
import 	android.text.method.PasswordTransformationMethod;

import com.example.cersapplication.UpdateInfoCitizen.forgotPassword;
import com.example.cersapplication.citizenProfile.CitizenProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import android.text.TextUtils;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.cersapplication.R;



public class LogIn extends AppCompatActivity {

    //Define the parts of activity
    private EditText emailAdd, PassWD;
    private ProgressBar progressBarLogIn;
    private FirebaseAuth authLogIn;

     private Button login;



    private static final String TAG = "LogIn";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //find views of activity
        emailAdd = findViewById(R.id.editLoginEmailAddress);
        PassWD = findViewById(R.id.editLoginPassword);
        progressBarLogIn = findViewById(R.id.progressBarLogIn);
        login = findViewById(R.id.Login);

        //firebase Authentication
        authLogIn = FirebaseAuth.getInstance();

        //forgotPass
        Button forgotPass =  findViewById(R.id.buttonForgotPassEmail);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogIn.this, "You can reset your password now", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this, forgotPassword.class));
            }
        });

        //showhidePassword
        ImageView imageviewHidepassword = findViewById(R.id.Hide_ShowPassword);
        imageviewHidepassword.setImageResource(R.drawable.ic_hide_pwd);
        imageviewHidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PassWD.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){

                    //If the password is visible then hide it
                    PassWD.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //Change Icon
                    imageviewHidepassword.setImageResource(R.drawable.ic_hide_pwd);
                }else{
                    PassWD.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageviewHidepassword.setImageResource(R.drawable.ic_show_pwd);

                }
            }
        });



        //Define button for log in
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textemail = emailAdd.getText().toString();
                String textPwd = PassWD.getText().toString();

                //if the user has not entered any data
                if(TextUtils.isEmpty(textemail)) {
                    Toast.makeText(LogIn.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                    emailAdd.setError("Email is required");
                    emailAdd.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()) {
                    Toast.makeText(LogIn.this, "Please re-enter your Email", Toast.LENGTH_LONG).show();
                    emailAdd.setError("Valid Email is Required");
                    emailAdd.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(LogIn.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    PassWD.setError("Password is required");
                    PassWD.requestFocus();
                }else{
                    progressBarLogIn.setVisibility(View.VISIBLE);
                    loginUser(textemail, textPwd);
                }

            }
        });
    }
    //authenticate the information and sign in
    private void loginUser(String email, String Pwd) {
        authLogIn.signInWithEmailAndPassword(email, Pwd).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Get instance of current user
                    FirebaseUser firebaseUser = authLogIn.getCurrentUser();

                    //Check if email is verified
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LogIn.this, "You are logged in now", Toast.LENGTH_SHORT).show();

                    }else{
                        firebaseUser.sendEmailVerification();
                        authLogIn.signOut();
                        verifyAlertDialog();
                    }
                }else{
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e){
                        emailAdd.setError("User does not exist or invalid. Please register again");
                        emailAdd.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        emailAdd.setError("Invalid credentials. Kindly re-enter");
                        emailAdd.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                progressBarLogIn.setVisibility(View.GONE);
            }
        });


    }

    private void verifyAlertDialog() {
        //set up alert builder
        AlertDialog.Builder verifyEmail = new AlertDialog.Builder(LogIn.this);
        verifyEmail.setTitle("Email not Verified");
        verifyEmail.setMessage("Please Verify your Email Address now. You cannot login without Email Verification");

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
//check if user is already logged in
    @Override
    protected void onStart() {
        super.onStart();
        if (authLogIn.getCurrentUser() != null){
            Toast.makeText(LogIn.this, "Already Logged In.", Toast.LENGTH_SHORT).show();

            //Start User Profile
            startActivity(new Intent(LogIn.this, CitizenProfile.class));
            finish();

        } else{
            Toast.makeText(LogIn.this, "You can Log In Now.", Toast.LENGTH_SHORT).show();
        }

    }
}



