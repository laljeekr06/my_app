package com.example.bookkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

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
    private EditText profession;
    private EditText aadhar;
    private EditText citizen;
    private EditText dob;
    private EditText n_name;
    private EditText n_relation;
    private EditText n_age;
    private EditText n_gender;
    private EditText bankName;
    private EditText bankBranch;
    private EditText accNo;
    private EditText ifsc;
    private EditText pancard;

    private Boolean editMode = false;

    private FirebaseDatabase myDatabase;
    private DatabaseReference userDatabase;
    private DatabaseReference bookingDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser cur_user;

    private TextView deleteButton;
    private ImageView personal_details_edit;
    private ImageView address_details_edit;
    private ImageView nominee_details_edit;
    private ImageView bank_details_edit;
    private RelativeLayout profile_layout;
    private ProgressBar progressBar;

    private String cur_email;
    private String password;
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
        profession = findViewById(R.id.profile_profession_text);
        aadhar = findViewById(R.id.profile_aadhar_text);
        dob = findViewById(R.id.profile_Dob_text);
        citizen = findViewById(R.id.profile_citizen_text);
        n_name = findViewById(R.id.profile_nominee_name_text);
        n_relation = findViewById(R.id.profile_nominee_relation_text);
        n_age = findViewById(R.id.profile_nominee_age_text);
        n_gender = findViewById(R.id.profile_nominee_gender_text);
        bankName = findViewById(R.id.profile_bank_name_text);
        bankBranch = findViewById(R.id.profile_bank_branch_text);
        accNo = findViewById(R.id.profile_bank_acc_text);
        ifsc = findViewById(R.id.profile_bank_ifsc_text);
        pancard = findViewById(R.id.profile_bank_pancard_text);


        profile_layout = findViewById(R.id.profile_container);
        progressBar = findViewById(R.id.profile_delete_progress_bar);

        deleteButton = findViewById(R.id.profile_delete_button);
        personal_details_edit = findViewById(R.id.edit_personal_details_button);
        address_details_edit = findViewById(R.id.edit_address_details_button);
        nominee_details_edit = findViewById(R.id.edit_nominee_details_button);
        bank_details_edit = findViewById(R.id.edit_bank_details_button);

        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance();
        userDatabase = myDatabase.getReference("users");
        bookingDatabase = myDatabase.getReference("bookings");
        cur_user = mAuth.getCurrentUser();
        cur_email = cur_user.getEmail();


        Log.i("check",cur_email);

        deleteButton.setOnClickListener(this);
        personal_details_edit.setOnClickListener(this);
        address_details_edit.setOnClickListener(this);
        nominee_details_edit.setOnClickListener(this);
        bank_details_edit.setOnClickListener(this);

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
                profession.setText(user.getProfession());
                citizen.setText(user.getCitizen());
                dob.setText(user.getDob());
                citizen.setText(user.getCitizen());
                n_name.setText(user.getNominee().getFname() + " " +user.getNominee().getLname());
                n_relation.setText(user.getNominee().getRelation());
                n_age.setText(user.getNominee().getAge());
                n_gender.setText(user.getNominee().getGender());
                bankName.setText(user.getBank().getBankName());
                bankBranch.setText(user.getBank().getBankBranch());
                pancard.setText(user.getBank().getPanCard());
                ifsc.setText(user.getBank().getIFSC_code());
                accNo.setText(user.getBank().getAccountNo());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this,"Data Fetching Failed..\n " + databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

//        Toast.makeText(ProfileActivity.this,"You have entered profile section",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.edit_personal_details_button){
            if(!editMode){
                editMode = true;
                editPersonal();
            }

            //            Toast.makeText(ProfileActivity.this,"Start Ediiting personal details",Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.edit_address_details_button){
           if(!editMode) {
               editMode = true;
               editAddress();
           }

            //            Toast.makeText(ProfileActivity.this,"Start Ediiting address details",Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.edit_nominee_details_button){
            if(!editMode){
                editMode = true;
                editNominee();
            }
            //            Toast.makeText(ProfileActivity.this,"Start Ediiting nominee details",Toast.LENGTH_SHORT).show();
        }

        if(v.getId() == R.id.edit_bank_details_button){
            if(!editMode) {
                editMode = true;
                editBank();
            }

            //            Toast.makeText(ProfileActivity.this,"Start Editing bank Details",Toast.LENGTH_SHORT).show();

        }
        if(v.getId() == R.id.profile_delete_button){
            String key_email = cur_email.replace('.','-');

            deleteBookings(key_email);
            deleteUserDetails(key_email);
            deleteUser();
        }
    }

    public void deleteBookings(String key_id){
        bookingDatabase.child(key_id).removeValue();
        Log.i("check","bookings deleted");
    }

    public void deleteUserDetails(String key_id){
        userDatabase.child(key_id).removeValue();
        Log.i("check","user database deleted");
    }

    public void deleteUser(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);

        dialog.setTitle("Are you Sure ?");

        final EditText input = new EditText(this);
        input.setHint("Enter your password to continue");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        dialog.setView(input);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressBar.setVisibility(View.VISIBLE);
                profile_layout.setAlpha(0.5f);

                password = input.getText().toString();

                AuthCredential credential = EmailAuthProvider.getCredential(cur_email, password);
                cur_user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cur_user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(GONE);
                                        profile_layout.setAlpha(1f);
                                        Toast.makeText(ProfileActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        progressBar.setVisibility(GONE);
                                        profile_layout.setAlpha(1f);
                                        Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            progressBar.setVisibility(GONE);
                            profile_layout.setAlpha(1f);
                            Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void editPersonal(){
        profession.setEnabled(true);
        aadhar.setEnabled(true);
        citizen.setEnabled(true);
        dob.setEnabled(true);

        deleteButton.setVisibility(GONE);

//        String profession_text = profession.getText().toString();
//        String aadhar_text = aadhar.getText().toString();
//        String citizen_text = citizen.getText().toString();
//        String dob_text = dob.getText().toString();

        final LinearLayout profile = findViewById(R.id.personal_detail_layout);
        profile.setBackgroundColor(getResources().getColor(R.color.editing));
        profession.setText("");
        aadhar.setText("");
        citizen.setText("");
        citizen.setHint("TYPE 'IN 'for INDIAN / 'NRI' for others ");
        dob.setText("");
        dob.setHint("DD/MM/YYYY");

         final String email = cur_email.replace('.','-');
         final FloatingActionButton personal_fab = getFab(ProfileActivity.this);

        personal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profession.setEnabled(false);
                aadhar.setEnabled(false);
                citizen.setEnabled(false);
                dob.setEnabled(false);
                profile.setBackgroundColor(getResources().getColor(R.color.non_editing));
                profile.setElevation(0);

                userDatabase.child(email).child("profession").setValue(profession.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Values Updated",Toast.LENGTH_SHORT).show();
                        }
                        if(task.isCanceled()){
                            Toast.makeText(ProfileActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                userDatabase.child(email).child("aadhar").setValue(aadhar.getText().toString());
                userDatabase.child(email).child("citizen").setValue(citizen.getText().toString());
                userDatabase.child(email).child("dob").setValue(dob.getText().toString());

                deleteButton.setVisibility(View.VISIBLE);
                personal_fab.setAlpha(0f);
                editMode = false;
            }
        });

    }

    public void editAddress(){

        flat.setEnabled(true);
        street.setEnabled(true);
        city.setEnabled(true);
        state.setEnabled(true);
        pincode.setEnabled(true);

        deleteButton.setVisibility(GONE);

        final LinearLayout profile = findViewById(R.id.profile_address_details_layout);
        profile.setBackgroundColor(getResources().getColor(R.color.editing));

        flat.setText("");
        street.setText("");
        city.setText("");
        state.setText("");
        pincode.setText("");

        final String email = cur_email.replace('.','-');
        final FloatingActionButton personal_fab = getFab(ProfileActivity.this);

        personal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flat.setEnabled(false);
                street.setEnabled(false);
                city.setEnabled(false);
                state.setEnabled(false);
                pincode.setEnabled(false);
                profile.setBackgroundColor(getResources().getColor(R.color.non_editing));
                profile.setElevation(0);

                userDatabase.child(email).child("address").child("flat_no").setValue(flat.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Values Updated",Toast.LENGTH_SHORT).show();
                        }
                        if(task.isCanceled()){
                            Toast.makeText(ProfileActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                userDatabase.child(email).child("address").child("street_name").setValue(street.getText().toString());
                userDatabase.child(email).child("address").child("city").setValue(city.getText().toString());
                userDatabase.child(email).child("address").child("state").setValue(state.getText().toString());
                userDatabase.child(email).child("address").child("pincode").setValue(pincode.getText().toString());

                deleteButton.setVisibility(View.VISIBLE);
                personal_fab.setAlpha(0f);
                editMode = false;
            }
        });

    }


    public void editNominee(){

        n_name.setEnabled(true);
        n_relation.setEnabled(true);
        n_age.setEnabled(true);
        n_gender.setEnabled(true);

        deleteButton.setVisibility(GONE);

        final LinearLayout profile = findViewById(R.id.profile_nominee_layout);
        profile.setBackgroundColor(getResources().getColor(R.color.editing));

        n_name.setText("");
        n_name.setHint("First_Name  Last_Name");
        n_relation.setText("");
        n_age.setText("");
        n_gender.setText("");
        n_gender.setHint("M/F/O");

        final String email = cur_email.replace('.','-');
        final FloatingActionButton personal_fab = getFab(ProfileActivity.this);

        personal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_name.setEnabled(false);
                n_relation.setEnabled(false);
                n_age.setEnabled(false);
                n_gender.setEnabled(false);
                profile.setBackgroundColor(getResources().getColor(R.color.non_editing));
                profile.setElevation(0);

                String temp_name[] = n_name.getText().toString().split(" ");


                userDatabase.child(email).child("nominee").child("fname").setValue(temp_name[0]).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Values Updated",Toast.LENGTH_SHORT).show();
                        }
                        if(task.isCanceled()){
                            Toast.makeText(ProfileActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                userDatabase.child(email).child("nominee").child("lname").setValue(temp_name[1]);
                userDatabase.child(email).child("nominee").child("relation").setValue(n_relation.getText().toString());
                userDatabase.child(email).child("nominee").child("age").setValue(n_age.getText().toString());
                userDatabase.child(email).child("nominee").child("gender").setValue(n_gender.getText().toString());

                deleteButton.setVisibility(View.VISIBLE);
                personal_fab.setAlpha(0f);
                editMode = false;
            }
        });

    }


    public void editBank(){

        bankName.setEnabled(true);
        bankBranch.setEnabled(true);
        accNo.setEnabled(true);
        ifsc.setEnabled(true);
        pancard.setEnabled(true);

        deleteButton.setVisibility(GONE);

        final LinearLayout profile = findViewById(R.id.profile_bank_details_layout);
        profile.setBackgroundColor(getResources().getColor(R.color.editing));

        bankName.setText("");
        bankBranch.setText("");
        accNo.setText("");
        ifsc.setText("");
        pancard.setText("");

        final String email = cur_email.replace('.','-');
        final FloatingActionButton personal_fab = getFab(ProfileActivity.this);

        personal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankName.setEnabled(false);
                bankBranch.setEnabled(false);
                accNo.setEnabled(false);
                ifsc.setEnabled(false);
                pancard.setEnabled(false);

                profile.setBackgroundColor(getResources().getColor(R.color.non_editing));
                profile.setElevation(0);

                userDatabase.child(email).child("bank").child("bankName").setValue(bankName.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this,"Values Updated",Toast.LENGTH_SHORT).show();
                        }
                        if(task.isCanceled()){
                            Toast.makeText(ProfileActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                userDatabase.child(email).child("bank").child("bankBranch").setValue(bankBranch.getText().toString());
                userDatabase.child(email).child("bank").child("ifsc_code").setValue(ifsc.getText().toString());
                userDatabase.child(email).child("bank").child("accountNo").setValue(accNo.getText().toString());
                userDatabase.child(email).child("bank").child("panCard").setValue(pancard.getText().toString());

                deleteButton.setVisibility(View.VISIBLE);
                personal_fab.setAlpha(0f);
                editMode = false;
            }
        });

    }

    public FloatingActionButton getFab(Context context){
        FloatingActionButton fab = new FloatingActionButton(this);
        fab.setImageResource(R.drawable.save);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        fab.setElevation(6);
        fab.setSize(FloatingActionButton.SIZE_NORMAL);
        fab.setFocusable(true);

        RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lay.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lay.setMargins(2,2,2,16);
        fab.setLayoutParams(lay);

        RelativeLayout rl = findViewById(R.id.profile_container);
        if(rl!=null){
            rl.addView(fab);
        }

        return fab;
    }
}
