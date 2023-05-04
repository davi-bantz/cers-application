package com.example.cersapplication.citizenregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.app.DatePickerDialog;
import 	android.icu.util.Calendar;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.cersapplication.R;
import com.example.cersapplication.MainParts.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_accountCitizen extends AppCompatActivity {

    private EditText editRegisterName, editdob, editPhoneNumber, editTextEmail, editRegisterPass, editConfirmPass, editUsername;
    private ProgressBar progressBar;
    private static final String TAG= "create_accountCitizen";

    private DatePickerDialog birthdatepicker;
    private Button victim;
    private Button GoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_citizen);

        getSupportActionBar().setTitle("Create an Account");

        Toast.makeText(create_accountCitizen.this, "You can Register Now", Toast.LENGTH_SHORT).show();


        progressBar = findViewById(R.id.progressBarCitizen);
        editRegisterName = findViewById(R.id.editName6);
        editdob = findViewById(R.id.editdob);
        editPhoneNumber = findViewById(R.id.editVictimPhone);
        editTextEmail = findViewById(R.id.editEmail1);
        editRegisterPass = findViewById(R.id.editPassword);
        editConfirmPass = findViewById(R.id.editConfirmPass);
        editUsername = findViewById(R.id.editUsername);

        //set up date picker
        editdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar dateofbirth = Calendar.getInstance();
                int dayofbirth = dateofbirth.get(Calendar.DAY_OF_MONTH);
                int monthofbirth = dateofbirth.get(Calendar.MONTH);
                int yearofbirth = dateofbirth.get(Calendar.YEAR);

                //datepicker dialog
                birthdatepicker = new DatePickerDialog(create_accountCitizen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editdob.setText(dayofbirth + "/"+ (monthofbirth + 1) + "/" + yearofbirth);

                    }

                }, yearofbirth, monthofbirth, dayofbirth);
                birthdatepicker.show();

            }
        });

        victim = findViewById(R.id.VictimNext1);
        victim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Obtain entered details
                String textFullName = editRegisterName.getText().toString();
                String textdob = editdob.getText().toString();
                String textPhoneNumber = editPhoneNumber.getText().toString();
                String textEmail = editTextEmail.getText().toString();
                String textPassword = editRegisterPass.getText().toString();
                String textConfirmPassword = editConfirmPass.getText().toString();
                String textUsername = editUsername.getText().toString();


                //validate the phone number
                String mobileNumber = "((^(\\+)(\\d){12}$)|(^\\d{11}$))";
                Matcher mobilematcher;
                Pattern mobilePatt = Pattern.compile(mobileNumber);
                mobilematcher = mobilePatt.matcher(textPhoneNumber);

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Full Name", Toast.LENGTH_LONG).show();
                    editRegisterName.setError("Full Name is Required");
                    editRegisterName.requestFocus();
                } else if (TextUtils.isEmpty(textdob)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Birthdate", Toast.LENGTH_LONG).show();
                    editdob.setError("Date of Birth is Required");
                    editdob.requestFocus();
                } else if (TextUtils.isEmpty(textPhoneNumber)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Phone Number", Toast.LENGTH_LONG).show();
                    editPhoneNumber.setError("Phone Number is Required");
                    editPhoneNumber.requestFocus();
                } else if (textPhoneNumber.length() != 11) {
                    Toast.makeText(create_accountCitizen.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
                    editPhoneNumber.setError("Phone Number must be 11 digits");
                    editPhoneNumber.requestFocus();
                } else if (!mobilematcher.find()) {
                    Toast.makeText(create_accountCitizen.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
                    editPhoneNumber.setError("Phone Number is not valid");
                    editPhoneNumber.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Email", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Email is Required");
                    editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(create_accountCitizen.this, "Please re-enter your Email", Toast.LENGTH_LONG).show();
                    editTextEmail.setError("Valid Email is Required");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Password", Toast.LENGTH_LONG).show();
                    editRegisterPass.setError("Password is Required");
                    editRegisterPass.requestFocus();
                } else if (textPassword.length() < 8) {
                    Toast.makeText(create_accountCitizen.this, "Password should be at least 8 digits", Toast.LENGTH_LONG).show();
                    editRegisterPass.setError("Password too weak");
                    editRegisterPass.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    Toast.makeText(create_accountCitizen.this, "Please confirm your Password", Toast.LENGTH_LONG).show();
                    editConfirmPass.setError("Password Confirmation is Required");
                    editConfirmPass.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)) {
                    Toast.makeText(create_accountCitizen.this, "Please re-confirm Password", Toast.LENGTH_LONG).show();
                    editConfirmPass.setError("Password does not match");
                    editConfirmPass.requestFocus();
                    editRegisterPass.clearComposingText();
                    editConfirmPass.clearComposingText();
                } else if (TextUtils.isEmpty(textUsername)) {
                    Toast.makeText(create_accountCitizen.this, "Please enter your Username", Toast.LENGTH_LONG).show();
                    editUsername.setError("Username is Required");
                    editUsername.requestFocus();
                }else {
                    progressBar.setVisibility(view.VISIBLE);
                    registerUser(textFullName, textdob, textUsername, textEmail, textPhoneNumber, textPassword, textConfirmPassword);

                }
            }
        });

        GoBack = findViewById(R.id.VictimBack1);
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_accountCitizen.this, Register.class);
                startActivity(intent);{
                }
            }
        });
    }

    private void registerUser(String textFullName, String textdob, String textUsername, String textEmail, String textPhoneNumber, String textPassword, String textConfirmPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //CREATE User Profile
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(create_accountCitizen.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update Display User
                    UserProfileChangeRequest profileChangeRequest  = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);


                    //Enter data into realtime database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textdob, textUsername, textPhoneNumber);

                    //database Reference
                    DatabaseReference basicDetails = FirebaseDatabase.getInstance().getReference("Citizens");

                    basicDetails.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                //send verify email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(create_accountCitizen.this, "User Successfully Registered. Please verify your email", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(create_accountCitizen.this, Address.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }else{
                                Toast.makeText(create_accountCitizen.this, "User registered failed. Please try again", Toast.LENGTH_LONG).show();

                            }
                            //Hide progressbar
                            progressBar.setVisibility(View.GONE);
                        }
                    });


                    //Exceptions in register part
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editRegisterPass.setError("Your Password is too Weak. Use a Capital Letter, small letters and numbers");
                        editRegisterPass.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editRegisterPass.setError("Your email is invalid or already in-use");
                        editRegisterPass.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        editRegisterPass.setError("User is already registered");
                        editRegisterPass.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(create_accountCitizen.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}