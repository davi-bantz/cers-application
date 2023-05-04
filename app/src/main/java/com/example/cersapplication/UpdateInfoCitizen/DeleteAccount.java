package com.example.cersapplication.UpdateInfoCitizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteAccount extends AppCompatActivity {

    private FirebaseAuth authprofile;
    private FirebaseUser currentUser;
    private EditText passwordAuth;
    private Button authenticateUser, deleteUser;
    private TextView showverifiedAcc;
    private ProgressBar progressbarDeleteAcc;
    private String passwd;
    private static final String TAG = "DeleteAccount";



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        passwordAuth = findViewById(R.id.Authenticate_Current_Password_forDelete);
        authenticateUser = findViewById(R.id.Authenticate_PassWD_AccountforDelete);
        deleteUser = findViewById(R.id.Delete_User_Btn);
        progressbarDeleteAcc = findViewById(R.id.progressBar_Authenticate_PassDeleteAcc);
        showverifiedAcc = findViewById(R.id.textView_authenticated_AccforDelete);

        //disable delete button
        deleteUser.setEnabled(false);

        //get current user
        authprofile = FirebaseAuth.getInstance();
        currentUser = authprofile.getCurrentUser();

        if(currentUser.equals("")){
            Toast.makeText(this, "Something went wrong. User's details are not available!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DeleteAccount.this, CitizenProfile.class);
            startActivity(intent);
            finish();
        } else{
            reAuthenticateUser(currentUser);
        }
    }

    private void reAuthenticateUser(FirebaseUser currentUser) {
        //authenticate user before changing password
            authenticateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   passwd= passwordAuth.getText().toString();

                    if(TextUtils.isEmpty(passwd)){
                        Toast.makeText(DeleteAccount.this, "Password is required.", Toast.LENGTH_SHORT).show();
                        passwordAuth.setError("Please enter your current password to authenticate");
                        passwordAuth.requestFocus();
                    } else {
                        progressbarDeleteAcc.setVisibility(View.VISIBLE);

                        //reauthenticate user
                        AuthCredential usercredentials = EmailAuthProvider.getCredential(currentUser.getEmail(), passwd);

                        currentUser.reauthenticate(usercredentials).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressbarDeleteAcc.setVisibility(View.GONE);

                                    //disable authenticate, enable change password
                                    passwordAuth.setEnabled(false);
                                    authenticateUser.setEnabled(false);
                                    deleteUser.setEnabled(true);

                                    //setTextview
                                    showverifiedAcc.setText("Your Account has been Verified");
                                    Toast.makeText(DeleteAccount.this, "You can delete your account now!", Toast.LENGTH_SHORT).show();

                                    deleteUser.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                           deleteAccountAlertDialog();

                                        }
                                    });
                                } else {
                                    try{
                                        throw task.getException();
                                    } catch (Exception e){
                                        Toast.makeText(DeleteAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    progressbarDeleteAcc.setVisibility(View.GONE);
                                }

                            }
                        });
                    }
                }
            });
        }

    private void deleteAccountAlertDialog() {

        //set up alert builder
        AlertDialog.Builder deleteaccount = new AlertDialog.Builder(DeleteAccount.this);
        deleteaccount.setTitle("Account Deletion");
        deleteaccount.setMessage("Do you really want to delete your account and other related data? This action is irreversible");

        deleteaccount.setPositiveButton("Delete User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteRelatedUserData(currentUser);
            }
        });

        //go to profile if canceled
        deleteaccount.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent cancelDelete = new Intent(DeleteAccount.this, CitizenProfile.class);
                startActivity(cancelDelete);
                finish();

            }
        });

        //Create the alert dialog box
        AlertDialog verify = deleteaccount.create();
        verify.show();
    }

    private void finalDeleteAccount() {
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    authprofile.signOut();
                    Toast.makeText(DeleteAccount.this, "User has been deleted!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteAccount.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    try{
                        throw task.getException();
                    } catch (Exception e){
                        Toast.makeText(DeleteAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressbarDeleteAcc.setVisibility(View.GONE);
                }

            }
        });
    }
    //delete all related data of the user
    private void deleteRelatedUserData(FirebaseUser currentUser) {
        //Delete user profile pic
        if (currentUser.getPhotoUrl() != null){
            FirebaseStorage firebasestorageuser = FirebaseStorage.getInstance();
            StorageReference storageReference = firebasestorageuser.getReferenceFromUrl(currentUser.getPhotoUrl().toString());
            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "On Success: Photo Deleted");

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(DeleteAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Delete data from realtime database
        DatabaseReference deletedatareference = FirebaseDatabase.getInstance().getReference("Citizens");
        deletedatareference.child(currentUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "On Success: User Data Deleted");
                finalDeleteAccount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(DeleteAccount.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent changeP = new Intent(DeleteAccount.this, UpdatePassword.class);
            startActivity(changeP);
            finish();
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(DeleteAccount.this, DeleteAccount.class);
            startActivity(deleteAcc);
        }else if(id == R.id.menu_log_out){
            //log out
            authprofile.signOut();
            Toast.makeText(DeleteAccount.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(DeleteAccount.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(DeleteAccount.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}