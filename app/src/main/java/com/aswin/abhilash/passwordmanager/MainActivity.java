package com.aswin.abhilash.passwordmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        Button register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(userid.getText()))
                {
                    userid.setError("Please a userid.");
                    return;
                }
                if(TextUtils.isEmpty(pin.getText()))
                {
                    pin.setError("Please a PIN.");
                    return;
                }
                if(TextUtils.isEmpty(secque.getText()))
                {
                    secque.setError("Please a Security Question.");
                    return;
                }
                if(TextUtils.isEmpty(secans.getText()))
                {
                    secans.setError("Please a Security Answer.");
                    return;
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Confirm");
                alertDialogBuilder.setMessage("Confirm to submit Form");
                alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference dbref = database.getReference("userno");

                        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int nousr = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                dbref.setValue(nousr+1);
                                DatabaseReference patRef = database.getReference(userid.getText().toString());
                                patRef.child("password").setValue(pin.getText().toString());
                                patRef.child("secque").setValue(secque.getText().toString());
                                patRef.child("secans").setValue(secans.getText().toString());
                                Toast.makeText(MainActivity.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(MainActivity.this, "Unable to fetch value.please restart app.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Registration Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });

    }
}
