package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingHistoryActivity extends AppCompatActivity {


    private ListView listView;
    private BookingListAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseDatabase myDatabase;
    private DatabaseReference bookingRef;
    private Booking booked;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        listView = findViewById(R.id.booking_history_list_view);

        final ArrayList<Booking> bookings = new ArrayList<>();
        mAdapter = new BookingListAdapter(this,bookings);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myDatabase = FirebaseDatabase.getInstance();
        bookingRef = myDatabase.getReference("bookings");



        bookingRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot obj:dataSnapshot.getChildren()){
                    String email = obj.getKey();
                    String new_email = email.replace('-','.');
                    Log.i("testing","new email :" + new_email);
                    if(new_email.equals(user.getEmail())){
                        for (DataSnapshot temp: obj.getChildren()){
                            booked = temp.getValue(Booking.class);
                            bookings.add(booked);
                        }
                        Log.i("testing","match found");
                    }
                }

                listView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(BookingHistoryActivity.this,"You have entered booking history section",Toast.LENGTH_SHORT).show();

    }
}
