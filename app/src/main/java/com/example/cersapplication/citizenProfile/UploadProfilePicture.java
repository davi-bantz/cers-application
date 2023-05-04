package com.example.cersapplication.citizenProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cersapplication.MainParts.MainActivity;
import com.example.cersapplication.R;
import com.example.cersapplication.UpdateInfoCitizen.DeleteAccount;
import com.example.cersapplication.UpdateInfoCitizen.UpdatePassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadProfilePicture extends AppCompatActivity {

    private ProgressBar progressbarUpload;
    private ImageView viewprofilephoto;
    private FirebaseAuth authUser;

    private StorageReference storagereferenceforPhotos;
    private FirebaseUser firebaseuserPhoto;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);

        //get user
        authUser = FirebaseAuth.getInstance();
        firebaseuserPhoto = authUser.getCurrentUser();

        //firebase storage reference
        storagereferenceforPhotos = FirebaseStorage.getInstance().getReference("Citizens").child("Display Pics");

        Button chooseImage = findViewById(R.id.button_choose_photo);
        Button uploadImage = findViewById(R.id.button_upload_photo);
        progressbarUpload = findViewById(R.id.progressBarUploadProfilePhoto);
        viewprofilephoto = findViewById(R.id.imageView_Photo);

        Uri uriphoto = firebaseuserPhoto.getPhotoUrl();

        //set to image view
        Picasso.with(UploadProfilePicture.this).load(uriphoto).into(viewprofilephoto);

        //choose image now
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhotochooser();
            }
        });

        //upload image to firestore
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbarUpload.setVisibility(View.VISIBLE);
                UploadPhoto();
            }
        });

    }

    private void UploadPhoto() {
        if (uriImage !=null){
        //saveimage
        StorageReference filereference = storagereferenceforPhotos.child("Display Pics").child(authUser.getCurrentUser().getUid() +"/displaypics." + getFileExtension(uriImage));

        //upload it
        filereference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filereference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri downloaduri = uriImage;
                        firebaseuserPhoto = authUser.getCurrentUser();

                        //finally set profile
                        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(downloaduri).build();
                        firebaseuserPhoto.updateProfile(changeRequest);
                    }
                });
                progressbarUpload.setVisibility(View.GONE);
                Toast.makeText(UploadProfilePicture.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                Intent backtoSettings = new Intent(UploadProfilePicture.this, SettingsCitizenProfile.class);
                startActivity(backtoSettings);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadProfilePicture.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    } else {
            progressbarUpload.setVisibility(View.GONE);
            Toast.makeText(UploadProfilePicture.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    //get image extension
    private String getFileExtension(Uri uriImage) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uriImage));

    }

    private void openPhotochooser() {
        Intent chooseimage = new Intent();
        chooseimage.setType("image/*");
        chooseimage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseimage, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            uriImage = data.getData();
            viewprofilephoto.setImageURI(uriImage);

        }
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
            Intent changeP = new Intent(UploadProfilePicture.this, UpdatePassword.class);
            startActivity(changeP);
        }else if (id == R.id.menu_Delete_Account){
            //delete acc
            Intent deleteAcc = new Intent(UploadProfilePicture.this, DeleteAccount.class);
            startActivity(deleteAcc);
        }else if(id == R.id.menu_log_out){
            //log out
            authUser.signOut();
            Toast.makeText(UploadProfilePicture.this, "You have logged out",
                    Toast.LENGTH_LONG).show();
            Intent gobackMain = new Intent(UploadProfilePicture.this, MainActivity.class);
            gobackMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gobackMain);
            finish();
        } else {
            Toast.makeText(UploadProfilePicture.this, "Something went wrong",
                    Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
