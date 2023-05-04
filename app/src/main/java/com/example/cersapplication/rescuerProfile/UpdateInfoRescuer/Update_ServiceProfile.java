package com.example.cersapplication.rescuerProfile.UpdateInfoRescuer;

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
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.example.cersapplication.rescuerProfile.Rescuer_Address;
import com.example.cersapplication.rescuerProfile.Rescuer_UserProfile;
import com.example.cersapplication.rescuerRegistration.ReadWriteServiceDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_ServiceProfile extends AppCompatActivity {

    //CODE THE UPDATE FOR BASIC SKILLS

    private FirebaseAuth authuser;
    private EditText editRescuerOrg, editRescuerRole, editRescuerOrgAddress;
    private String org, role, orgaddress;
    private ProgressBar progressBar_UpdateRescuer;
    private Button updateServiceProfile;
    private ImageButton goback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service_profile);

        editRescuerOrg = findViewById(R.id.Edit_Rescuer_Organization);
        editRescuerRole = findViewById(R.id.Edit_Rescuer_Role);
        editRescuerOrgAddress = findViewById(R.id.Edit_Rescuer_OrgAddress);
        progressBar_UpdateRescuer =  findViewById(R.id.progressBar_UpdateServiceProf);
        goback =  findViewById(R.id.imageButtonforbacktoSetting_SERProfUp);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtoUserProfile = new Intent(Update_ServiceProfile.this, Rescuer_UserProfile.class);
                startActivity(backtoUserProfile);
                finish();
            }
        });

        //define user prof
        authuser = FirebaseAuth.getInstance();
        FirebaseUser rescuerUser =  authuser.getCurrentUser();

        //show Service profile
        ShowRescuerServiceProfile(rescuerUser);

        //updatedata
        Button button_Update_ServProf = findViewById(R.id.Update_Rescuer_ServiceProfileBTN);
        button_Update_ServProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateService_Profile(rescuerUser);
            }
        });
    }

    private void updateService_Profile(FirebaseUser rescuerUser) {

        if (TextUtils.isEmpty(org)) {
            Toast.makeText(Update_ServiceProfile.this, "Please enter the Department/Organization you are assigned", Toast.LENGTH_LONG).show();
            editRescuerOrg.setError("Department/Organization is Required");
            editRescuerOrg.requestFocus();
        } else if (TextUtils.isEmpty(role)) {
            Toast.makeText(Update_ServiceProfile.this, "Please enter your Role/Specialization in your Organization/Department", Toast.LENGTH_LONG).show();
            editRescuerRole.setError("Role is Required");
            editRescuerRole.requestFocus();
        } else if (TextUtils.isEmpty(orgaddress)) {
            Toast.makeText(Update_ServiceProfile.this, "Please enter the address of your assigned station", Toast.LENGTH_LONG).show();
            editRescuerOrgAddress.setError("Address of Station is Required");
            editRescuerOrgAddress.requestFocus();

        } else {

            //obtain data
            org = editRescuerOrg.getText().toString();
            role = editRescuerRole.getText().toString();
            orgaddress = editRescuerOrgAddress.getText().toString();

            //update to database
            ReadWriteServiceDetails updateServProf = new ReadWriteServiceDetails(org, role, orgaddress);

            //get database reference
            DatabaseReference rescuerDatabase = FirebaseDatabase.getInstance().getReference("Rescuers");

            //rescuerID
            String rescueriD = rescuerUser.getUid();
            progressBar_UpdateRescuer.setVisibility(View.VISIBLE);

            //enter the data
            rescuerDatabase.child(rescueriD).child("Service Profile").setValue(updateServProf).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(Update_ServiceProfile.this, "Update Succesfull!", Toast.LENGTH_LONG).show();
                        Intent goCitBD = new Intent(Update_ServiceProfile.this, Rescuer_Address.class);
                        goCitBD.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            Toast.makeText(Update_ServiceProfile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar_UpdateRescuer.setVisibility(View.GONE);
                    }

                }
            });
        }
    }



    private void ShowRescuerServiceProfile(FirebaseUser rescuerUser) {
        String rescuerID = rescuerUser.getUid();

        //database reference
        DatabaseReference rescuerReference = FirebaseDatabase.getInstance().getReference("Rescuers");
        progressBar_UpdateRescuer.setVisibility(View.VISIBLE);

        //get data from database and show in textview
        rescuerReference.child(rescuerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteServiceDetails serviceDetails = snapshot.getValue(ReadWriteServiceDetails.class);

                if (serviceDetails != null) {
                    org = serviceDetails.department;
                    role = serviceDetails.role;
                    orgaddress = serviceDetails.addresOS;

                    //set to textview
                    editRescuerOrg.setText(org);
                    editRescuerRole.setText(role);
                    editRescuerOrgAddress.setText(orgaddress);
                } else {
                    Toast.makeText(Update_ServiceProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
                progressBar_UpdateRescuer.setVisibility(View.GONE);

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
            Intent changeP = new Intent(Update_ServiceProfile.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(Update_ServiceProfile.this, DeleteAccount.class);
            startActivity(deleteAcc);
            finish();
        }else if(id == R.id.menu_log_out){
            //log out
            authuser.signOut();
            Toast.makeText(Update_ServiceProfile.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(Update_ServiceProfile.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(Update_ServiceProfile.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}