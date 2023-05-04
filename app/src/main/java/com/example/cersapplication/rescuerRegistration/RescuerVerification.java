package com.example.cersapplication.rescuerRegistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.os.Handler;
import android.widget.EditText;

import android.app.ProgressDialog;

import com.example.cersapplication.R;
import com.example.cersapplication.citizenregistration.Address;
import com.example.cersapplication.citizenregistration.EmergencyContact;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;;
import java.util.UUID;




public class RescuerVerification extends AppCompatActivity {

    private static final int Pick_Image_Request = 1;

    private Uri verifyUri;

    private StorageReference RescuerReference;

    private DatabaseReference Rescuersreference;

    ProgressBar progressBar;

    FirebaseUser firebaseUser;

    FirebaseAuth auth;

    ImageView imageVerify;

    private EditText filename;
    Button selectPhoto, uploadPhoto, nextStartProfile, backServiceProfile;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer_verification);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        imageVerify = findViewById(R.id.imageVerify3);
        uploadPhoto = findViewById(R.id.UploadPhotobtn);
        progressBar = findViewById(R.id.progressBarRescuer3);
        filename = findViewById(R.id.editFileName);
        RescuerReference = FirebaseStorage.getInstance().getReference("Rescuer ID");
        Rescuersreference = FirebaseDatabase.getInstance().getReference("Rescuers").child(firebaseUser.getUid()).child("Service Profile");

        selectPhoto =findViewById(R.id.RSelectPhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        nextStartProfile = findViewById(R.id.RescuerNextConfirmation);
        nextStartProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentConfirm = new Intent(RescuerVerification.this, EmergencyContact.class);
                intentConfirm.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentConfirm);
            }
        });
        backServiceProfile = findViewById(R.id.RescuerBack3);
        backServiceProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentService = new Intent(RescuerVerification.this, ServiceProfile.class);
                startActivity(intentService);
            }
        });

    }


    private void chooseFile(){
        Intent view = new Intent();
        view.setType("Image/*");
        view.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(view, Pick_Image_Request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Pick_Image_Request && resultCode == RESULT_OK && data != null && data.getData() != null){
                    verifyUri = data.getData();
            Picasso.with(this).load(verifyUri).into(imageVerify);
        }
    }

    private void uploadFile() {
        if (verifyUri != null){
            ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference IDreference = RescuerReference.child(System.currentTimeMillis() + UUID.randomUUID().toString());
            IDreference.putFile(verifyUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Handler handler = new Handler();
                           handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   progressBar.setProgress(0);
                               }
                           }, 5000);
                            Toast.makeText(RescuerVerification.this, "Upload Successfully", Toast.LENGTH_LONG).show();
                            UploadRescuerVerification upload = new UploadRescuerVerification(filename.getText().toString().trim());
                            String uploadID = Rescuersreference.push().getKey();
                            Rescuersreference.child("Identification").setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RescuerVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(RescuerVerification.this, "No Image Selected", Toast.LENGTH_LONG).show();

        }


    }


}