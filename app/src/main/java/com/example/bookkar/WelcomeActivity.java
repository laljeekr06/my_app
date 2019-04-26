package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

   // private DatabaseReference mDatabase;
    private DrawerLayout dl;
    private NavigationView navigation_view;
    private TextView consumer_no;
    private FirebaseAuth mAuth;
  //  private DatabaseReference bookingDatabase;
    private DatabaseReference userDatabase;
    private FloatingActionButton addButton;
    private FirebaseUser curUser;
    private String userId;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
//        bookingDatabase = FirebaseDatabase.getInstance().getReference("bookings");
        userDatabase = FirebaseDatabase.getInstance().getReference();
        curUser = mAuth.getCurrentUser();
        userId = curUser.getUid();


        addButton = findViewById(R.id.new_booking_fab);
        addButton.setOnClickListener(this);

        dl = findViewById(R.id.activity_welcome_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


//        userDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                userDatabase.orderByChild("userId").equalTo(userId).addValueEventListener(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        }
//                );
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(WelcomeActivity.this,"FAILED : " + databaseError.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });


      //  Toast.makeText(getApplicationContext(),newUser.getUser_id() + "   " + newUser.getFirst_name() +  "   " + newUser.getLast_name(),Toast.LENGTH_SHORT).show();

        navigation_view = findViewById(R.id.navigation_drawer);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.account:
                        Toast.makeText(WelcomeActivity.this, "Account Selected", Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(true);
                        dl.closeDrawers();
                        break;

                    case R.id.settings:
                        Toast.makeText(WelcomeActivity.this, "Settings Selected", Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(true);
                        dl.closeDrawers();
                        break;
                    case R.id.mycart:
                        Toast.makeText(WelcomeActivity.this, "My Cart Selected", Toast.LENGTH_SHORT).show();
                        menuItem.setChecked(true);
                        dl.closeDrawers();
                        break;
                    case R.id.logout:
                        menuItem.setChecked(true);
                        dl.closeDrawers();

                        mAuth.signOut();

                        Toast.makeText(WelcomeActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                navigation_view.bringToFront();
                dl.requestLayout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.new_booking_fab){
            Snackbar.make(v,"New booking will be added",Snackbar.LENGTH_SHORT).setAction("Action",null).show();

        }
    }
}
