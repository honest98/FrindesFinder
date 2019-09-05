package com.example.android.FriendsFinder;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    private final int GALLERY_UPLOAD = 1;
    private FirebaseAuth mAuth;

    @BindView(R.id.et_name)
    EditText userName;

    @BindView(R.id.et_email)
    EditText userEmail;

    @BindView(R.id.et_password)
    EditText userPassword;

    @BindView(R.id.profile_image)
    CircleImageView circleImageView;

    ProgressDialog progressDialog;
    Uri imageUri;

    private StorageReference mStorageRef;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server ....");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("userProfile");


    }

    @OnClick(R.id.bt_signup)
    public void signup() {

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.length() == 0){
            userEmail.setError("Please enter your email");
        }
        if (password.length() == 0){
            userPassword.setError("Please enter your password");
        }
        if (userName.getText().toString().length() == 0){
            userName.setError("Please enter your name");
        } else {

            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignUp.this,"Successful sign up",Toast.LENGTH_LONG).show();

                                user = mAuth.getCurrentUser();

                                uploadData();

//                            updateUI(user);
                            } else {
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUp.this,"Authentication failed.",
                                        Toast.LENGTH_LONG).show();
//                            updateUI(null);
                            }

                        }
                    });
        }


    }

    public void uploadData(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_UPLOAD);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == GALLERY_UPLOAD && resultCode == RESULT_OK) {

            if (data != null) {
                this.imageUri = data.getData();
                circleImageView.setImageURI(imageUri);
            }
        }
    }


    public void uploadData() {

        progressDialog.setMessage("Image uploading ....");
        progressDialog.show();

        final StorageReference ref = mStorageRef.child("images/" + user.getUid());

        if (imageUri == null) {
            imageUri = Uri.parse("android.resource://com.example.android.findfriends/" + R.drawable.userprofile);
        }

        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"upload success ",Toast.LENGTH_LONG).show();

                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("imageUrl",uri.toString());
                        map.put("userName",userName.getText().toString());
                        map.put("email",userEmail.getText().toString());
                        myRef.getRef().child(user.getUid()).setValue(map);

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Exception: " + e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }


}


