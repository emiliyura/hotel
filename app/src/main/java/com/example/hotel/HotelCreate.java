package com.example.hotel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class HotelCreate extends AppCompatActivity {

    ImageView uploadHotelImage;
    Button saveButton;
    EditText uploadHotelName, uploadHotelDescription, uploadHotelPrice;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri uri;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_create);

        uploadHotelImage = findViewById(R.id.addimagephoto);
        uploadHotelDescription = findViewById(R.id.uploadHotelDescription);
        uploadHotelName = findViewById(R.id.uploadHotelName);
        uploadHotelPrice = findViewById(R.id.uploadHotelPrice);
        saveButton = findViewById(R.id.saveHotelButton); // initialize saveButton

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadHotelImage.setImageURI(uri);
                        } else {
                            Toast.makeText(HotelCreate.this, "No image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadHotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    // move saveData() and uploadData() outside of onCreate()
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(HotelCreate.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uriImage = uriTask.getResult();
                imageURL = uriImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(HotelCreate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){
        String Name = uploadHotelName.getText().toString();
        String Desc = uploadHotelDescription.getText().toString();
        String Price = uploadHotelPrice.getText().toString();

        Hotel hotel = new Hotel(Name, Desc, Price, imageURL);

        FirebaseDatabase.getInstance().getReference("Hotel").child(Name).setValue(hotel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HotelCreate.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener(){ // addOnFailureListener() was not properly chained
            @Override
            public void onFailure(@NonNull Exception e){
                Toast.makeText(HotelCreate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
