package com.example.cersapplication.rescuerProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cersapplication.R;
import com.example.cersapplication.rescuerProfile.UpdateInfoRescuer.Update_ServiceProfile;
import com.example.cersapplication.rescuerRegistration.ReadWriteServiceDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rescuer_ServiceProfile extends AppCompatActivity {

    private FirebaseAuth authprofile;
    private TextView resOrg, resRole, resAddress, resSkills;
    private ImageButton backtoSettings;
    private Button updateService;
    private ProgressBar progressBarService;
    private String OrgName, RescuerRole, OrgAddress, RescuerSkills;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer_service_profile);

        resOrg = findViewById(R.id.Show_Rescuer_Organization);
        resRole = findViewById(R.id.Show_Rescuer_Role);
        resAddress = findViewById(R.id.Show_AddressOS);
        resSkills = findViewById(R.id.Show_Res_Basic_Skill);
        updateService = findViewById(R.id.Update_Service_Profile_Btn);
        backtoSettings = findViewById(R.id.imageButtonforbacktoSetting_SERProf);
        progressBarService = findViewById(R.id.progressBarService_Profile);

        //back to settings
        backtoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoSettings = new Intent(Rescuer_ServiceProfile.this, RescuerSettings.class);
                startActivity(backtoSettings);
                finish();
            }
        });

        //update service profile
        updateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoUpdate = new Intent(Rescuer_ServiceProfile.this, Update_ServiceProfile.class);
                startActivity(gotoUpdate);
            }
        });

        //get current user
        authprofile = FirebaseAuth.getInstance();
        FirebaseUser rescuerUser = authprofile.getCurrentUser();

        if (rescuerUser != null) {
            Toast.makeText(Rescuer_ServiceProfile.this, "Something is wrong. User details are not available at the moment!",
                    Toast.LENGTH_LONG).show();
        } else {
            progressBarService.setVisibility(View.VISIBLE);
            showServiceProfile(rescuerUser);

        }
    }

    private void showServiceProfile(FirebaseUser rescuerUser) {
        String getUserId = rescuerUser.getUid();

        //extract data from database
        DatabaseReference referenceRescuer = FirebaseDatabase.getInstance().getReference("Rescuers");
        referenceRescuer.child(getUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteServiceDetails serviceInfo = snapshot.getValue(ReadWriteServiceDetails.class);

                if (serviceInfo != null){

                    OrgName = serviceInfo.department;
                    RescuerRole = serviceInfo.role;
                    OrgAddress = serviceInfo.addresOS;
                    //Add here for basic skills

                    //set to textview
                    resOrg.setText(OrgName);
                    resRole.setText(RescuerRole);
                    resAddress.setText(OrgAddress);
                    //set textView for rescuer skills here

                }
                progressBarService.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Rescuer_ServiceProfile.this, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
                progressBarService.setVisibility(View.GONE);
            }
        });
    }
}
