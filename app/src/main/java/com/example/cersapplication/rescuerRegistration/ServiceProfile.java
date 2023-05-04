package com.example.cersapplication.rescuerRegistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.cersapplication.MainParts.Register;
import com.example.cersapplication.R;
import com.example.cersapplication.citizenregistration.ReadWriteAdressDetails;
import com.example.cersapplication.citizenregistration.create_accountCitizen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class ServiceProfile extends AppCompatActivity {

    private EditText department, role, adressOfStation;

    private ProgressBar progressBar;

    CheckBox firstAid, CPR, SNR, KnotLash, CasHandle;

    DatabaseReference Rescuers;

    FirebaseDatabase database;

    FirebaseUser firebaseUser;

    FirebaseAuth auth;

    Button buttonVerify;

    Button buttonBack2;

    String basicSkills;

    int i = 0;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profile);

        buttonVerify = findViewById(R.id.RescuerNextProfessionalProfile);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth = FirebaseAuth.getInstance();
                firebaseUser = auth.getCurrentUser();
                Rescuers = database.getInstance().getReference("Rescuers").child(firebaseUser.getUid());

                basicSkills = "";
                progressBar = findViewById(R.id.progressBarRescuer2);
                department = findViewById(R.id.editRescuerDepOrg);
                role = findViewById(R.id.editRescuerSpecialization);
                adressOfStation = findViewById(R.id.editResAddressDepOrg);
                firstAid = findViewById(R.id.BasicSkillFA);
                CPR = findViewById(R.id.BasicSkillCPR);
                SNR = findViewById(R.id.BasicSkillRescueSNR);
                KnotLash = findViewById(R.id.BasicSkillKnotLash);
                CasHandle = findViewById(R.id.basicSkillCasHandle);

                String textDepartment = department.getText().toString();
                String textRole = role.getText().toString();
                String textAddressOS = adressOfStation.getText().toString();


                if (TextUtils.isEmpty(textDepartment)) {
                    Toast.makeText(ServiceProfile.this, "Please enter the Department/Organization you are assigned", Toast.LENGTH_LONG).show();
                    department.setError("Department/Organization is Required");
                    department.requestFocus();
                } else if (TextUtils.isEmpty(textRole)) {
                    Toast.makeText(ServiceProfile.this, "Please enter your Role/Specialization in your Organization/Department", Toast.LENGTH_LONG).show();
                    role.setError("Role is Required");
                    role.requestFocus();
                } else if (TextUtils.isEmpty(textAddressOS)) {
                    Toast.makeText(ServiceProfile.this, "Please enter the address of your assigned station", Toast.LENGTH_LONG).show();
                    adressOfStation.setError("Address of Station is Required");
                    adressOfStation.requestFocus();

                } else {
                    ServiceProfile(textDepartment, textRole, textAddressOS);


                    Rescuers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                i = (int) snapshot.getChildrenCount();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if (firstAid.isChecked()) {
                        basicSkills += " " + firstAid.getText().toString();
                        Rescuers.child(firebaseUser.getUid()).child("Service Profile").child("Basic Skills").child(String.valueOf(i + 1)).setValue(firstAid);

                    } else {

                    }
                    if (CPR.isChecked()) {
                        basicSkills += " " + CPR.getText().toString();
                        Rescuers.child(firebaseUser.getUid()).child("Service Profile").child("Basic Skills").child(String.valueOf(i + 1)).setValue(CPR);

                    } else {

                    }
                    if (SNR.isChecked()) {
                        basicSkills += " " + SNR.getText().toString();
                        Rescuers.child(firebaseUser.getUid()).child("Service Profile").child("Basic Skills").child(String.valueOf(i + 1)).setValue(SNR);

                    } else {

                    }
                    if (KnotLash.isChecked()) {
                        basicSkills += " " + KnotLash.getText().toString();
                        Rescuers.child(firebaseUser.getUid()).child("Service Profile").child("Basic Skills").child(String.valueOf(i + 1)).setValue(KnotLash);

                    } else {

                    }
                    if (CasHandle.isChecked()) {
                        basicSkills += " " + CasHandle.getText().toString();
                        Rescuers.child(firebaseUser.getUid()).child("Service Profile").child("Basic Skills").child(String.valueOf(i + 1)).setValue(CasHandle);

                    } else {

                    }
                    progressBar.setVisibility(View.GONE);
                }
            }


        });
        buttonBack2 = findViewById(R.id.VictimBack1);
        buttonBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceProfile.this, RescuerVerification.class);
                startActivity(intent);
                {
                }
            }
        });


    }

    @SuppressLint("NotConstructor")
    private void ServiceProfile(String textDepartment, String textRole, String textAddressOS) {
        ReadWriteServiceDetails ServiceDetails = new ReadWriteServiceDetails(textDepartment, textRole, textAddressOS);
        Rescuers.child(firebaseUser.getUid()).child("Service Profile").setValue(ServiceDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(ServiceProfile.this, RescuerVerification.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                {

                }
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}
