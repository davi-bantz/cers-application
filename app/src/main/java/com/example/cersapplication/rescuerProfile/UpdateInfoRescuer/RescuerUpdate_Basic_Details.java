package com.example.cersapplication.rescuerProfile.UpdateInfoRescuer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.example.cersapplication.UpdateInfoCitizen.Update_Email;
import com.example.cersapplication.citizenProfile.CitizenBasicDetails;
import com.example.cersapplication.citizenregistration.ReadWriteExtraDetails;
import com.example.cersapplication.citizenregistration.ReadWriteUserDetails;
import com.example.cersapplication.rescuerProfile.Rescuer_UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RescuerUpdate_Basic_Details extends AppCompatActivity {

    private EditText updateName, updateBoD, updateUsername, updatebloodType, updatecivilStatus, updatephonenumber;
    private RadioGroup updateGender;
    private RadioButton updateGenderSelected;
    private String fullname, Bod, username,  bloodtype, civilstatus, phonenumber, gender;
    private FirebaseAuth authprofile;
    private ProgressBar progressBar_updatebasicdetails;
    private ImageButton backtoUserProf;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer_update_basic_details);

        //layout declaration
        updateName = findViewById(R.id.Edit_Citizen_Name);
        updateBoD = findViewById(R.id.Edit_Citizen_Birthdate);
        updateUsername = findViewById(R.id.Edit_Citizen_UserName);
        updatephonenumber = findViewById(R.id.Edit_Citizen_PhoneNumber);
        updatebloodType = findViewById(R.id.Edit_Citizen_BloodTpe);
        updatecivilStatus = findViewById(R.id.Edit_Citizen_CivilStatus);
        progressBar_updatebasicdetails = findViewById(R.id.progressBar_UpdateBD);
        backtoUserProf = findViewById(R.id.imageButtonforbacktoSetting);

        //back to profile
        backtoUserProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoUserProfile = new Intent(RescuerUpdate_Basic_Details.this, Rescuer_UserProfile.class);
                startActivity(backtoUserProfile);
                finish();
            }
        });

        //declare radio group
        updateGender = findViewById(R.id.Edit_GenderGroup);

        //Firebase auth
        authprofile = FirebaseAuth.getInstance();
        FirebaseUser currentaccount = authprofile.getCurrentUser();

        //Show profile data
        showbasicdetails(currentaccount);

        //set up date picker
        updateBoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extracting saved dd/mm/y into different variables
                String textDoB[] = Bod.split("/");

                int dayofbirth = Integer.parseInt(textDoB[0]);
                int monthofbirth = Integer.parseInt(textDoB[1]) - 1;
                int yearofbirth = Integer.parseInt(textDoB[2]);

                DatePickerDialog birthdatepicker;

                //datepicker dialog
                birthdatepicker = new DatePickerDialog(RescuerUpdate_Basic_Details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        updateBoD.setText(dayofbirth + "/"+ (monthofbirth + 1) + "/" + yearofbirth);

                    }

                }, yearofbirth, monthofbirth, dayofbirth);
                birthdatepicker.show();

            }
        });

        //update profile
        Button updateBasicDetails = findViewById(R.id.Update_Profile_BasicDetails);
        updateBasicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedetails(currentaccount);
            }
        });

        //update email
        Button updateEmail = findViewById(R.id.Update_Email);
        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoEditEmail = new Intent(RescuerUpdate_Basic_Details.this, Update_Email.class);
                startActivity(gotoEditEmail);
                finish();
            }
        });
    }
    //update profile
    private void updatedetails(FirebaseUser currentaccount) {
        int updatedGender = updateGender.getCheckedRadioButtonId();
        updateGenderSelected = findViewById(updatedGender);

        //validate the phone number
        String mobileNumber = "((^(\\+)(\\d){12}$)|(^\\d{11}$))";
        Matcher mobilematcher;
        Pattern mobilePatt = Pattern.compile(mobileNumber);
        mobilematcher = mobilePatt.matcher(phonenumber);

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Full Name", Toast.LENGTH_LONG).show();
            updateName.setError("Full Name is Required");
            updateName.requestFocus();
        } else if (TextUtils.isEmpty(Bod)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Birthdate", Toast.LENGTH_LONG).show();
            updateBoD.setError("Date of Birth is Required");
            updateBoD.requestFocus();
        } else if (TextUtils.isEmpty(phonenumber)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Phone Number is Required");
            updatephonenumber.requestFocus();
        } else if (phonenumber.length() != 11) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Phone Number must be 11 digits");
            updatephonenumber.requestFocus();
        } else if (!mobilematcher.find()) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please re-enter your Phone Number", Toast.LENGTH_LONG).show();
            updatephonenumber.setError("Phone Number is not valid");
            updatephonenumber.requestFocus();
        } else if (TextUtils.isEmpty(username)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Username", Toast.LENGTH_LONG).show();
            updateUsername.setError("Username is Required");
            updateUsername.requestFocus();
        } else if (TextUtils.isEmpty(civilstatus)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Civil Status", Toast.LENGTH_LONG).show();
            updatecivilStatus.setError("Civil Status is Required");
            updatecivilStatus.requestFocus();
        } else if (TextUtils.isEmpty(bloodtype)) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please enter your Blood Type", Toast.LENGTH_LONG).show();
            updatebloodType.setError("Blood Type is Required");
            updatebloodType.requestFocus();
        } else if (TextUtils.isEmpty(updateGenderSelected.getText())) {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Please Select your Gender", Toast.LENGTH_LONG).show();
            updateGenderSelected.setError("Gender is Required");
            updateGenderSelected.requestFocus();
        }else {
            //Obtain data input by user
            gender = updateGenderSelected.getText().toString();
            fullname = updateName.getText().toString();
            Bod= updateBoD.getText().toString();
            username = updateUsername.getText().toString();
            bloodtype = updatebloodType.getText().toString();
            civilstatus = updatecivilStatus.getText().toString();
            phonenumber = updatephonenumber.getText().toString();

            //enter data into database
            ReadWriteUserDetails newUserDetails = new ReadWriteUserDetails(fullname, username,phonenumber);
            ReadWriteExtraDetails newExtraDetails = new ReadWriteExtraDetails(bloodtype, gender, civilstatus);

            //get database reference
            DatabaseReference referenceUser = FirebaseDatabase.getInstance().getReference("Rescuers");

            //Get User Id
            String UserID = currentaccount.getUid();
            progressBar_updatebasicdetails.setVisibility(View.VISIBLE);

            //enter through child
            referenceUser.child(UserID).setValue(newUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        //Set new display name
                        UserProfileChangeRequest profileUpdates =  new UserProfileChangeRequest.Builder().
                                setDisplayName(fullname).build();

                        Toast.makeText(RescuerUpdate_Basic_Details.this, "Update Succesfull!", Toast.LENGTH_LONG).show();
                        Intent goCitBD = new Intent(RescuerUpdate_Basic_Details.this, CitizenBasicDetails.class);
                        goCitBD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    }else{
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(RescuerUpdate_Basic_Details.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                    progressBar_updatebasicdetails.setVisibility(View.GONE);

                }
            });
            referenceUser.child(UserID).setValue(newExtraDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(RescuerUpdate_Basic_Details.this, "Update Succesfull!", Toast.LENGTH_LONG).show();
                        Intent goCitBD = new Intent(RescuerUpdate_Basic_Details.this, CitizenBasicDetails.class);
                        goCitBD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    } else{
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(RescuerUpdate_Basic_Details.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                    progressBar_updatebasicdetails.setVisibility(View.GONE);

                }
            });
        }

    }

    //fetch data from database and display in edittext
    private void showbasicdetails(FirebaseUser currentaccount) {
        String userID = currentaccount.getUid();

        //Database Reference path
        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Citizens");
        progressBar_updatebasicdetails.setVisibility(View.VISIBLE);

        //mention the child by UID
        referenceprofile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readbasicdetails = snapshot.getValue(ReadWriteUserDetails.class);
                ReadWriteExtraDetails readextradetails = snapshot.getValue(ReadWriteExtraDetails.class);

                if (readbasicdetails != null) {
                    fullname = currentaccount.getDisplayName();
                    Bod = readbasicdetails.dob;
                    phonenumber = readbasicdetails.Phone;
                    username = readbasicdetails.Username;

                    updateName.setText(fullname);
                    updatephonenumber.setText(phonenumber);
                    updateBoD.setText(Bod);
                    updateUsername.setText(username);

                    if (readextradetails != null) {
                        gender = readextradetails.Gender;
                        civilstatus = readextradetails.CivilStatus;
                        bloodtype = readextradetails.Gender;


                        updatebloodType.setText(bloodtype);
                        updatecivilStatus.setText(civilstatus);

                        //show gender
                        if (gender.equals("Male")) {
                            updateGenderSelected = findViewById(R.id.Update_Male);
                        } else {
                            updateGenderSelected = findViewById(R.id.Update_Female);
                        }
                        updateGenderSelected.setChecked(true);
                    } else{
                        Toast.makeText(RescuerUpdate_Basic_Details.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                    }
                    progressBar_updatebasicdetails.setVisibility(View.GONE);

                } else{
                    Toast.makeText(RescuerUpdate_Basic_Details.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
                progressBar_updatebasicdetails.setVisibility(View.GONE);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RescuerUpdate_Basic_Details.this, "Something went wrong!", Toast.LENGTH_LONG).show();
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
            Intent changeP = new Intent(RescuerUpdate_Basic_Details.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(RescuerUpdate_Basic_Details.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(RescuerUpdate_Basic_Details.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(RescuerUpdate_Basic_Details.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(RescuerUpdate_Basic_Details.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}

