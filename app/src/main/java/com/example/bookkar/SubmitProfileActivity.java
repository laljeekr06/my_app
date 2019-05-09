package com.example.bookkar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SubmitProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener {

    private EditText phone_no;
    private EditText email;
    private EditText consumer_no;
    private EditText first_name;
    private EditText middle_name;
    private EditText last_name;
    private EditText flat_no;
    private EditText street_name;
    private EditText city;
    private EditText state;
    private EditText pincode;
    private ToggleButton connection_type;
    private TextView submit_button;

    private String user_id;
    private String phone_no_text;
    private String email_text;
    private String consumer_no_text;
    private String first_name_text;
    private String middle_name_text;
    private String last_name_text;
    private String flat_no_text;
    private String street_name_text;
    private String city_text;
    private String state_text;
    private String pincode_text;
    private String connection_type_text;
    private String gender_text;

    private DatabaseReference UserDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_profile);

        UserDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        Spinner genderSpinner =  findViewById(R.id.gender_spinner);
        genderSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the gender list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        genderSpinner.setAdapter(adapter);

        consumer_no = findViewById(R.id.consumer_no_field);
        email = findViewById(R.id.email_field);
        phone_no = findViewById(R.id.phone_no_field);
        first_name = findViewById(R.id.consumer_first_name_field);
        middle_name = findViewById(R.id.consumer_middle_name_field);
        last_name = findViewById(R.id.consumer_last_name_field);
        flat_no = findViewById(R.id.address_flat_no_field);
        street_name = findViewById(R.id.address_street_name_field);
        city = findViewById(R.id.address_city_name_field);
        state = findViewById(R.id.address_state_name_field);
        pincode = findViewById(R.id.address_pincode_field);
        connection_type = findViewById(R.id.connection_type_toggle);

        submit_button = findViewById(R.id.profile_submit_button);
        submit_button.setOnClickListener(this);

        email.setText(mAuth.getCurrentUser().getEmail());
        email.setEnabled(false);

        String number = generateConsumerNo();
        consumer_no.setText(number);
        consumer_no.setEnabled(false);

    }

    private String generateConsumerNo() {
        Long tslong = System.currentTimeMillis()/1000;
        String ts =  tslong.toString();

        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);

        if(month < 9){
            return "BR0" + (month+1) + ts;
        }
        else{
            return "BR" + (month+1) + ts;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(SubmitProfileActivity.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        gender_text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender_text = "other";
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_submit_button){
            submitProfile();
        }
    }

    private void submitProfile() {
            phone_no_text = phone_no.getText().toString();
            consumer_no_text = consumer_no.getText().toString();
            email_text = email.getText().toString();
            first_name_text = first_name.getText().toString();
            middle_name_text = middle_name.getText().toString();
            last_name_text = last_name.getText().toString();
            flat_no_text = flat_no.getText().toString();
            street_name_text = street_name.getText().toString();
            city_text = city.getText().toString();
            state_text = state.getText().toString();
            pincode_text = pincode.getText().toString();
            connection_type_text = connection_type.getText().toString();

            if(TextUtils.isEmpty(phone_no_text) ||
                    TextUtils.isEmpty(consumer_no_text) ||
                    TextUtils.isEmpty(first_name_text) ||
                    TextUtils.isEmpty(last_name_text) ||
                    TextUtils.isEmpty(flat_no_text) ||
                    TextUtils.isEmpty(street_name_text) ||
                    TextUtils.isEmpty(city_text) ||
                    TextUtils.isEmpty(state_text) ||
                    TextUtils.isEmpty(pincode_text))
                    {
                        Toast.makeText(SubmitProfileActivity.this,">> Fields Missing ..!",Toast.LENGTH_LONG).show();
            }

            else{
               // Toast.makeText(SubmitProfileActivity.this,"User Activity will be created",Toast.LENGTH_SHORT).show();

//                user_id = UserDatabase.push().getKey();

//                Toast.makeText(SubmitProfileActivity.this,"User ID created :  " + user_id ,Toast.LENGTH_SHORT).show();


                Address address = new Address(city_text,flat_no_text,pincode_text,state_text,street_name_text);
                User newUser = new User(address,connection_type_text,consumer_no_text,email_text,first_name_text,gender_text,last_name_text,middle_name_text,phone_no_text,user_id);

                String key_email = email_text.replace('.','-');
                UserDatabase.child("users").child(key_email).setValue(newUser);

                Intent intent = new Intent(SubmitProfileActivity.this,WelcomeActivity.class);
               // intent.putExtra("consumer_no",consumer_no_text);
             //   Toast.makeText(SubmitProfileActivity.this,consumer_no_text,Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
    }
}
