package com.example.android.FriendsFinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    EditText userEmail, userPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userEmail = findViewById(R.id.et_email_in);
        userPassword = findViewById(R.id.et_password_in);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server ....");

        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        String pass = sharedPreferences.getString("password", "");


        if (!name.equals("") && !pass.equals("") )
        {
            signin(name, pass);
        }

    }

    public void signin(View view) {

        String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();

        if (email.length() == 0){
            userEmail.setError("please enter your email");
        }
        if (password.length() == 0){
            userPassword.setError("please enter your password");
        } else {

            progressDialog.show();


            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SignIn.this,"Authentication Successful.",
                                        Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userName",userEmail.getText().toString());
                                editor.putString("password",userPassword.getText().toString());
                                editor.commit();

                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(getApplicationContext(),FriendSearch.class));
//                            updateUI(user);
                            } else {
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignIn.this,"Authentication failed.",
                                        Toast.LENGTH_LONG).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
    }


    public void signin(String userName, String userPass) {

        progressDialog.show();

        mAuth.signInWithEmailAndPassword(userName, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignIn.this, "Authentication Successful.",
                                    Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), FriendSearch.class));
//                            updateUI(user);
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
