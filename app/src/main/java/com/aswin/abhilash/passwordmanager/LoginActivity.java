package com.aswin.abhilash.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginbutton =findViewById(R.id.loginbutton);
        TextView forgotpassword = findViewById(R.id.forgotpassword);
        TextView newregister = findViewById(R.id.register);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userid = findViewById(R.id.userid);
                final EditText loginpswd = findViewById(R.id.loginpassword);
                final String uid = userid.getText().toString();
                final String pass = loginpswd.getText().toString();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference dbref = database.getReference(uid);
                final DatabaseReference usref = dbref.child("pin");

                usref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                       String pin  = String.valueOf(snapshot.getValue());
                        if(TextUtils.isEmpty(uid))
                        {
                            userid.setError("Please enter your User ID.");
                        }
                        if(pass.length()<8)
                        {
                            loginpswd.setError("Please enter an 8 digit password.");
                        }
                        if(TextUtils.isEmpty(pass))
                        {
                            loginpswd.setError("Please enter your PIN.");
                        }
                        else if(( uid.contentEquals(dbref.getKey())&& pass.contentEquals(pin)))
                        {
                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(getApplicationContext(),Main2Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userid",uid);
                            bundle.putString("pin",pin);
                            login.putExtras(bundle);
                            startActivity(login);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Please enter correct credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotpswd = new Intent(getApplicationContext(),Forgot.class);
                startActivity(forgotpswd);

            }
        });

        newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newregister = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(newregister);
            }
        });
    }


}
