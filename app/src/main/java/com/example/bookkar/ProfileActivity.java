package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView consumer_no;
    private TextView name_field;
    private EditText email;
    private EditText phone;
    private EditText Gender;
    private EditText flat;
    private EditText street;
    private EditText city;
    private EditText state;
    private EditText pincode;

    private FirebaseDatabase myDatabase;
    private DatabaseReference userDatabase;
    private DatabaseReference bookingDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser cur_user;

    private TextView deleteButton;
    private ImageView user_details_edit;
    private ImageView personal_details_edit;
    private ImageView address_details_edit;
    private ImageView nominee_details_edit;

    private String cur_email;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        consumer_no = findViewById(R.id.user_profile_consumer_no);
        name_field = findViewById(R.id.user_profile_name);
        email = findViewById(R.id.profile_email_text);
        phone = findViewById(R.id.profile_phone_no_text);
        Gender = findViewById(R.id.profile_gender_text);
        flat = findViewById(R.id.profile_flat_no_text);
        street = findViewById(R.id.profile_street_no_text);
        city = findViewById(R.id.profile_city_text);
        state = findViewById(R.id.profile_state_text);
        pincode = findViewById(R.id.profile_pincode_text);
        deleteButton = findViewById(R.id.profile_delete_button);
        user_details_edit = findViewById(R.id.edit_user_details_button);
        personal_details_edit = findViewById(R.id.edit_personal_details_button);
        address_details_edit = findViewById(R.id.edit_address_details_button);
        nominee_details_edit = findViewById(R.id.edit_nominee_details_button);

        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance();
        userDatabase = myDatabase.getReference("users");
        bookingDatabase = myDatabase.getReference("bookings");
        cur_user = mAuth.getCurrentUser();
        cur_email = cur_user.getEmail();


        deleteButton.setOnClickListener(this);
        user_details_edit.setOnClickListener(this);
        personal_details_edit.setOnClickListener(this);
        address_details_edit.setOnClickListener(this);
        nominee_details_edit.setOnClickListener(this);

        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot obj : dataSnapshot.getChildren()) {

                    if (obj.child("email").getValue().toString().equals(cur_email)) {
                        user = obj.getValue(User.class);
                        break;
                    }
                }

                consumer_no.setText(user.getConsumer_no());
                name_field.setText(user.getFirst_name() + " " + user.getLast_name());
                email.setText(user.getEmail());
                phone.setText(user.getPhone_no());
                Gender.setText(user.getGender());
                flat.setText(user.getAddress().getFlat_no());
                street.setText(user.getAddress().getStreet_name());
                city.setText(user.getAddress().getCity());
                state.setText(user.getAddress().getState());
                pincode.setText(user.getAddress().getPincode());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Data Fetching Failed..\n Try Again After some time",Toast.LENGTH_LONG).show();
            }
        });

//        Toast.makeText(ProfileActivity.this,"You have entered profile section",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_user_details_button){

        }
        if(v.getId() == R.id.edit_personal_details_button){

        }
        if(v.getId() == R.id.edit_address_details_button){

        }
        if(v.getId() == R.id.edit_nominee_details_button){

        }
        if(v.getId() == R.id.profile_delete_button){

            String key_email = cur_email.replace('-','.');
//            Toast.makeText(ProfileActivity.this,"User will be deleted :" + key_email ,Toast.LENGTH_SHORT).show();
            deleteBookings(key_email);
//            deleteUserDetails(key_email);
//            deleteUser();
        }
    }

    public void deleteBookings(String key_id){
        bookingDatabase.child(key_id).removeValue();
    }

    public void deleteUserDetails(String key_id){
        userDatabase.child(key_id).removeValue();
    }

    public void deleteUser(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);

        dialog.setTitle("Are you Sure ?");
        dialog.setMessage("This will result in deleting all your information stored within this application"
                + "all informations will be removed from the system and you will not be able to access the application further"
                + "To continue , press delete or to withdraw press cancel");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cur_user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
