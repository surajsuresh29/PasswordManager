package com.aswin.abhilash.passwordmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userid = findViewById(R.id.userid);
        final EditText pin = findViewById(R.id.pin);
        final EditText secque = findViewById(R.id.securityquestion);
        final EditText secans = findViewById(R.id.securityanswer);
        final Button register = findViewById(R.id.register);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbref = database.getReference("userno");
        final DatabaseReference checkref = database.getReference();

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Confirm");
        alertDialogBuilder.setMessage("Confirm to submit Form");
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                register.setClickable(false);
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int nousr = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                        dbref.setValue(nousr+1);
                        DatabaseReference usrRef = database.getReference(userid.getText().toString());
                        usrRef.child("pin").setValue(pin.getText().toString());
                        usrRef.child("secque").setValue(secque.getText().toString());
                        usrRef.child("secans").setValue(secans.getText().toString());
                        Toast.makeText(MainActivity.this, "Registration Succesfull", Toast.LENGTH_LONG).show();
                        Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(login);
                        register.setClickable(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Unable to connect. Please try again later.", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Registration Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(userid.getText()))
                {
                    userid.setError("Please enter a user id.");
                    return;
                }
                if(TextUtils.isEmpty(pin.getText()))
                {
                    pin.setError("Please enter a PIN.");
                    return;
                }
                if(pin.length()<8)
                {
                    pin.setError("Please enter an 8 digit PIN.");
                    return;
                }
                if(TextUtils.isEmpty(secque.getText()))
                {
                    secque.setError("Please enter a Security Question.");
                    return;
                }
                if(TextUtils.isEmpty(secans.getText()))
                {
                    secans.setError("Please enter a Security Answer.");
                    return;
                }
                checkref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(userid.getText().toString())) {
                            userid.setError("User id already exists.");
                            return;
                        }
                        else
                        {
                            AlertDialog dialog = alertDialogBuilder.create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
