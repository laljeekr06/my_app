package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private ProgressBar sign_progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sign_In_Active = true;

        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        primary_sign_in = findViewById(R.id.primary_sign_up_button);
        alternate_sign_in = findViewById(R.id.alternate_sign_up_button);
        sign_progress = findViewById(R.id.sign_up_progress);
        ImageView icon = findViewById(R.id.gas_icon);

        mAuth = FirebaseAuth.getInstance();

        primary_sign_in.setOnClickListener(this);
        alternate_sign_in.setOnClickListener(this);
        email.setOnClickListener(this);
        password.setOnClickListener(this);
       icon.setOnClickListener(this);

       password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
               if(actionId == EditorInfo.IME_ACTION_DONE ||
                       event.getAction() == KeyEvent.ACTION_DOWN ||
                       event.getKeyCode() == KeyEvent.KEYCODE_ENTER){

                   startSignIn(email.getText().toString(),password.getText().toString());
               }
               return true;
           }
       });
 }


    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.gas_icon){
            hideKeyboard(MainActivity.this);
        }
        if(v.getId() == R.id.primary_sign_up_button) {

            final String email_text= email.getText().toString().trim();
            final String password_text = password.getText().toString().trim();

            if (TextUtils.isEmpty(email_text)) {
                Toast.makeText(MainActivity.this, "Oops..! Email can't be empty", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password_text)) {
                Toast.makeText(MainActivity.this, "You forgot to enter your password..!", Toast.LENGTH_SHORT).show();
            } else {
                startSignIn(email_text,password_text);
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

    public void startSignIn(String email_text,String password_text){
        hideKeyboard(this);
        sign_progress.setVisibility(View.VISIBLE);
        primary_sign_in.setVisibility(View.GONE);
        alternate_sign_in.setVisibility(View.GONE);

        if (Sign_In_Active) {

            mAuth.signInWithEmailAndPassword(email_text,password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            sign_progress.setVisibility(View.GONE);

                            if(task.isSuccessful()){
                                // Open Profile Activity here
                                startActivity(new Intent(MainActivity.this,WelcomeActivity.class));

                            }
                            else{
                                String msg = task.getException().getMessage();
                                Log.i("signUp",msg);
                                primary_sign_in.setVisibility(View.VISIBLE);
                                alternate_sign_in.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this,"Sign In Failed : \n" +msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Creating a new user account for this email and password

            mAuth.createUserWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            sign_progress.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign In successful , open the profile activity here
                                // startActivity(new Intent(CreateProfileActivity.this,WelcomeActivity.class));
                                startActivity(new Intent(MainActivity.this, SubmitProfileActivity.class));

                            } else {
                                String msg = task.getException().getMessage();
                                primary_sign_in.setVisibility(View.VISIBLE);
                                alternate_sign_in.setVisibility(View.VISIBLE);
                                Log.i("signUp",msg);
                                Toast.makeText(MainActivity.this, "Sign Up Failed :" + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if(view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
