package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Boolean Sign_In_Active;
    private EditText email;
    private EditText password;
    private TextView primary_sign_in;
    private TextView alternate_sign_in;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sign_In_Active = true;

        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        primary_sign_in = findViewById(R.id.primary_sign_up_button);
        alternate_sign_in = findViewById(R.id.alternate_sign_up_button);

        ImageView icon = findViewById(R.id.gas_icon);

        mAuth = FirebaseAuth.getInstance();

        primary_sign_in.setOnClickListener(this);
        alternate_sign_in.setOnClickListener(this);
        email.setOnClickListener(this);
        password.setOnClickListener(this);
       icon.setOnClickListener(this);
 }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.primary_sign_up_button) {
            final String email_text = email.getText().toString().trim();
            final String password_text = password.getText().toString().trim();
            if (TextUtils.isEmpty(email_text)) {
                Toast.makeText(MainActivity.this, "Oops..! Email can't be empty", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password_text)) {
                Toast.makeText(MainActivity.this, "You forgot to enter your password..!", Toast.LENGTH_SHORT).show();
            } else {

                if (Sign_In_Active) {
                    // TODO : implement sign in method here

                    mAuth.signInWithEmailAndPassword(email_text,password_text)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                      if(task.isSuccessful()){
                                          // Open Profile Activity here
                                          startActivity(new Intent(MainActivity.this,WelcomeActivity.class));

                                      }
                                      else{
                                          Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
                                      }
                                }
                            });
                } else {
                    // Creating a new user account for this email and password

                    mAuth.createUserWithEmailAndPassword(email_text, password_text)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign In successful , open the profile activity here
                                       // startActivity(new Intent(CreateProfileActivity.this,WelcomeActivity.class));

                                       startActivity(new Intent(MainActivity.this,ProfileActivity.class));

                                    } else {
                                        Toast.makeText(MainActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }
        else if(v.getId() == R.id.alternate_sign_up_button){
            if(Sign_In_Active) {
                Sign_In_Active = false;
                primary_sign_in.setText("Sign Up");
                alternate_sign_in.setText("Already a User ! Sign In");
            }
            else{
                Sign_In_Active = true;
                primary_sign_in.setText("Sign In");
                alternate_sign_in.setText("Or , Sign Up");
            }
        }


    }
}
