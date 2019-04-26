package com.example.bookkar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class TempActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView icon;
    private Handler handler1;
    private Handler handler2;
    private TextView loading_text;
    private TextView dots;
    private int count = 0;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        progressBar = findViewById(R.id.temp_progress_bar);
        icon = findViewById(R.id.rotating_icon);
        loading_text = findViewById(R.id.loading_text);
        dots = findViewById(R.id.loading_dots);

        firebaseAuth = FirebaseAuth.getInstance();

        handler1 = new Handler();
        handler2 = new Handler();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{

                    //do your code here
                    count++;
                    if(count > 5) {
                        if (dots.getText().toString().matches(" ...")) {
                            dots.setText(" .");
                        } else {
                            dots.setText(dots.getText().toString() + ".");
                        }
                    }

                    else if(count == 5){
                        loading_text.setText("Loading ");
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally{
                    //also call the same runnable to call it at regular interval
                    handler1.postDelayed(this, 500);
                }
            }
        };

//runnable must be execute once
        handler1.post(runnable);

       handler2.postDelayed(new Runnable() {
           @Override
           public void run() {

               if(firebaseAuth.getCurrentUser() != null){
                   // If User has already logged in, show him his profile
                   startActivity(new Intent(TempActivity.this,WelcomeActivity.class));
               }
                else{
                    // if user hasn't logged in , send him to Login Page
                    startLoginPage();
               }
             //  startActivity(new Intent(TempActivity.this,MainActivity.class));
           }
       },4000);

    }

    public void startLoginPage(){
        startActivity(new Intent(TempActivity.this,MainActivity.class));
    }
}
