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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.net.InterfaceAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    // private DatabaseReference mDatabase;
    private DrawerLayout dl;
    private NavigationView navigation_view;
    private View mHeaderView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase myDatabase;
    private DatabaseReference bookingDatabase;
    private DatabaseReference userDatabase;
    private FloatingActionButton addButton;
    private FirebaseUser curUser;

    private String curEmail;
    private User current_User;
    private TextView Drawer_Name;
    private TextView Drawer_consumer_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance();
        bookingDatabase = myDatabase.getReference("bookings");
        userDatabase = myDatabase.getReference("users");
        bookingDatabase = FirebaseDatabase.getInstance().getReference();
        curUser = mAuth.getCurrentUser();
        curEmail = curUser.getEmail();
        current_User = new User();
        addButton = findViewById(R.id.new_booking_fab);
        addButton.setOnClickListener(this);

        dl = findViewById(R.id.activity_welcome_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigation_view = findViewById(R.id.navigation_drawer);
        mHeaderView = navigation_view.getHeaderView(0);
        Drawer_consumer_no = mHeaderView.findViewById(R.id.nav_header_consumer_no);
        Drawer_Name = mHeaderView.findViewById(R.id.nav_header_consumer_name);
        LinearLayout log_out= navigation_view.findViewById(R.id.logout_layout);
        log_out.setOnClickListener(this);
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.profile:
//                        Toast.makeText(WelcomeActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WelcomeActivity.this,ProfileActivity.class));
                        dl.closeDrawers();
                        break;

                    case R.id.booking_history:
//                        Toast.makeText(WelcomeActivity.this, "Booking History Selected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(WelcomeActivity.this,BookingHistoryActivity.class));
                        dl.closeDrawers();
                        break;

                }
                return true;
            }
        });

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(User user) {

                  Drawer_consumer_no.setText(user.getConsumer_no());
                  Drawer_Name.setText(user.getFirst_name() + " " + user.getLast_name());
            }
        });

      //  Log.i("testing : out readData",current_User.getConsumer_no());
    }
//
    private void readData(final FirebaseCallback firebaseCallback) {
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    if (obj.child("email").getValue().toString().equals(curEmail)) {
                        current_User = obj.getValue(User.class);
                        break;
                    }

                }

                firebaseCallback.onCallback(current_User);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WelcomeActivity.this, "FAILED : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
//
    private interface FirebaseCallback {
        void onCallback(User user);
    }

    private void makeABooking(){
        Booking booking = new Booking();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        booking.setBookingDate(sdf.format(c.getTime()));
        c.add(Calendar.DATE,7);
        final String delivery_date = sdf.format(c.getTime());
        booking.setDeliveryDate(delivery_date);

        booking.setAmount(750);
        booking.setBookingId(bookingDatabase.push().getKey());
        booking.setConsumerId(Drawer_consumer_no.getText().toString());

        booking.setBooking_status("PENDING");
        String key_email = curEmail.replace('.','-');
        String id = bookingDatabase.push().getKey();
        bookingDatabase.child("bookings").child(key_email).child(id).setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(WelcomeActivity.this,"Booking Applied : \n Expected Delivery date  :" + delivery_date,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        switch (v.getId()) {
            case R.id.new_booking_fab:
//                Snackbar.make(v, "New booking will be added", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                makeABooking();
                break;
            case R.id.logout_layout:
                dl.closeDrawers();

                mAuth.signOut();

                Toast.makeText(WelcomeActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();

                break;
        }
    }
}