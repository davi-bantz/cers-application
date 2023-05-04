package com.example.cersapplication.citizenProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import 	android.icu.util.Calendar;
import 	android.icu.text.DateFormat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;
import 	android.widget.EditText;
import android.content.DialogInterface;


import com.example.cersapplication.R;


public class CitizenProfile extends AppCompatActivity {

    //Declare the functions in the activity
    Button sendMessage, updateProfile, others;

    private CheckBox wounded, stuck, critical, dead, emergency5, emergency6;

    private RadioGroup radioGroupNumber;

    private RadioButton radioButtonSelectedNumber;

    String emergency;
    String textNumber;

    private String otherEmergencies;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_profile);

        updateProfile = findViewById(R.id.buttonEditProfilAct);
        sendMessage = findViewById(R.id.buttonSendMessage);

        //set date and time
        Calendar calendarUser = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendarUser.getTime());
        TextView viewDate = findViewById(R.id.Date);
        viewDate.setText(currentdate);

        //when user clicks setting button
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateprofile = new Intent(CitizenProfile.this, SettingsCitizenProfile.class);
                startActivity(updateprofile);
            }
        });


        //When the user click send
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendemergency();
                Intent intent = new Intent(CitizenProfile.this, Sent_emergency_activity.class);
                startActivity(intent);

            }

            private void sendemergency() {

                //Declare the variables with find view
                emergency = "";
                wounded = findViewById(R.id.checkBox_Emergency1);
                stuck = findViewById(R.id.checkBox_Emergency2);
                critical = findViewById(R.id.checkBox_Emergency3);
                dead = findViewById(R.id.checkBox_Emergency4);
                emergency5 = findViewById(R.id.checkBox_Emergency5);
                emergency6 = findViewById(R.id.checkBox_Emergency6);
                others = findViewById(R.id.checkBox_Emergency8);

                //Declare the radiogroup
                radioGroupNumber = findViewById(R.id.NumberofCasualty);
                radioGroupNumber.clearCheck();

                int selectedNumberId = radioGroupNumber.getCheckedRadioButtonId();
                radioButtonSelectedNumber = findViewById(selectedNumberId);

                if (wounded.isChecked()) {
                    emergency += " " + wounded.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Wounded is Chosen", Toast.LENGTH_SHORT).show();

                } else {

                }
                if (stuck.isChecked()) {
                    emergency += " " + stuck.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Stuck is Chosen", Toast.LENGTH_SHORT).show();
                } else {

                }
                if (critical.isChecked()) {
                    emergency += " " + critical.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Critical is Chosen", Toast.LENGTH_SHORT).show();
                } else {

                }
                if (dead.isChecked()) {
                    emergency += " " + dead.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Dead is Chosen", Toast.LENGTH_SHORT).show();
                } else {

                }
                if (emergency5.isChecked()) {
                    emergency += " " + emergency5.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Emergency5 is Chosen", Toast.LENGTH_SHORT).show();
                } else {

                }
                if (emergency6.isChecked()) {
                    emergency += " " + emergency6.getText().toString();
                    Toast.makeText(CitizenProfile.this, "Emergency6 is Chosen", Toast.LENGTH_SHORT).show();
                } else if (radioGroupNumber.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(CitizenProfile.this, "Please choose if Solo or Group", Toast.LENGTH_LONG).show();
                    radioButtonSelectedNumber.setError("This field is Required");
                    radioButtonSelectedNumber.requestFocus();
                } else {
                    textNumber = radioButtonSelectedNumber.getText().toString();

                }
                //if there are other emergencies
                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder othersAlertdialog = new AlertDialog.Builder(CitizenProfile.this);
                        othersAlertdialog.setTitle("Unsa imong ubang Emergency?");

                        //input other emergencies
                        final EditText otherEmergency = new EditText(CitizenProfile.this);
                        othersAlertdialog.setView(otherEmergency);

                        //Positive button
                        othersAlertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                otherEmergencies = otherEmergency.getText().toString();
                                Toast.makeText(CitizenProfile.this, "Other Emergency recorded", Toast.LENGTH_SHORT).show();

                            }
                        });
                        //negative button of the dialog
                        othersAlertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        //create and show the dialog
                        AlertDialog othersAlert = othersAlertdialog.create();
                        othersAlert.show();
                    }
                });
            }
        });
    }
}



