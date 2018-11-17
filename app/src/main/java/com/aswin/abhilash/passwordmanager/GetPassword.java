package com.aswin.abhilash.passwordmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import se.simbio.encryption.Encryption;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetPassword extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String ACtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);
        Button showcredentials = findViewById(R.id.show_button);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setPrompt("SELECT_TYPE");
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("ACCOUNT--TYPE");
        categories.add("Google");
        categories.add("Facebook");
        categories.add("Instagram");
        categories.add("LinkedIn");
        categories.add("Twitter");
        categories.add("SHOW ALL");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Bundle bundle = getIntent().getExtras();
        final String userid = bundle.getString("userid");
        final DatabaseReference dbref = database.getReference(userid);
        final TextView t1 = findViewById(R.id.credential1);
        final TextView t2 = findViewById(R.id.credential2);
        final TextView t3 = findViewById(R.id.credential3);
        final TextView t4 = findViewById(R.id.credential4);
        final TextView t5 = findViewById(R.id.credential5);

        showcredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ACtype.contentEquals("ACCOUNT--TYPE"))
                {
                    Toast.makeText(GetPassword.this, "Please select an account type.", Toast.LENGTH_SHORT).show();
                }
                else if(ACtype.contentEquals("Google"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("Google ID"))
                            {
                                String acid = String.valueOf(dataSnapshot.child("Google ID").getValue());
                                String acpin = String.valueOf(dataSnapshot.child("Google Password").getValue());
                                Bundle bundle = getIntent().getExtras();
                                String key = bundle.getString("pin");
                                String salt = key;
                                byte[] iv = new byte[16];
                                Encryption encryption = Encryption.getDefault(key, salt, iv);
                                String decrypted = encryption.decryptOrNull(acpin);
                                t1.setText("GOOGLE : "+acid + " : " + decrypted);
                                t1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(GetPassword.this, "No Google Credentials stored.", Toast.LENGTH_SHORT).show();
                                t1.setVisibility(View.INVISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if(ACtype.contentEquals("Facebook"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Facebook ID"))
                        {
                            String acid = String.valueOf(dataSnapshot.child("Facebook ID").getValue());
                            String acpin = String.valueOf(dataSnapshot.child("Facebook Password").getValue());
                            Bundle bundle = getIntent().getExtras();
                            String key = bundle.getString("pin");
                            String salt = key;
                            byte[] iv = new byte[16];
                            Encryption encryption = Encryption.getDefault(key, salt, iv);
                            String decrypted = encryption.decryptOrNull(acpin);
                            t1.setText("FACEBOOK : "+acid + " : " + decrypted);
                            t1.setVisibility(View.VISIBLE);
                            t2.setVisibility(View.INVISIBLE);
                            t3.setVisibility(View.INVISIBLE);
                            t4.setVisibility(View.INVISIBLE);
                            t5.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Toast.makeText(GetPassword.this, "No Facebook Credentials stored.", Toast.LENGTH_SHORT).show();
                            t1.setVisibility(View.INVISIBLE);
                            t2.setVisibility(View.INVISIBLE);
                            t3.setVisibility(View.INVISIBLE);
                            t4.setVisibility(View.INVISIBLE);
                            t5.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                }
                else if(ACtype.contentEquals("Instagram"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("Instagram ID"))
                            {
                                String acid = String.valueOf(dataSnapshot.child("Instagram ID").getValue());
                                String acpin = String.valueOf(dataSnapshot.child("Instagram Password").getValue());
                                Bundle bundle = getIntent().getExtras();
                                String key = bundle.getString("pin");
                                String salt = key;
                                byte[] iv = new byte[16];
                                Encryption encryption = Encryption.getDefault(key, salt, iv);
                                String decrypted = encryption.decryptOrNull(acpin);
                                t1.setText("INSTAGRAM : "+acid + " : " + decrypted);
                                t1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(GetPassword.this, "No Instagram Credentials stored.", Toast.LENGTH_SHORT).show();
                                t1.setVisibility(View.INVISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if(ACtype.contentEquals("LinkedIn"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("LinkedIn ID"))
                            {
                                String acid = String.valueOf(dataSnapshot.child("LinkedIn ID").getValue());
                                String acpin = String.valueOf(dataSnapshot.child("LinkedIn Password").getValue());
                                Bundle bundle = getIntent().getExtras();
                                String key = bundle.getString("pin");
                                String salt = key;
                                byte[] iv = new byte[16];
                                Encryption encryption = Encryption.getDefault(key, salt, iv);
                                String decrypted = encryption.decryptOrNull(acpin);
                                t1.setText("LINKED IN : "+acid + " : " + decrypted);
                                t1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(GetPassword.this, "No LinkedIn Credentials stored.", Toast.LENGTH_SHORT).show();
                                t1.setVisibility(View.INVISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if(ACtype.contentEquals("Twitter"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("Twitter ID"))
                            {
                                String acid = String.valueOf(dataSnapshot.child("Twitter ID").getValue());
                                String acpin = String.valueOf(dataSnapshot.child("Twitter Password").getValue());
                                Bundle bundle = getIntent().getExtras();
                                String key = bundle.getString("pin");
                                String salt = key;
                                byte[] iv = new byte[16];
                                Encryption encryption = Encryption.getDefault(key, salt, iv);
                                String decrypted = encryption.decryptOrNull(acpin);
                                t1.setText("TWITTER : "+acid + " : " + decrypted);
                                t1.setVisibility(View.VISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                Toast.makeText(GetPassword.this, "No Twitter Credentials stored.", Toast.LENGTH_SHORT).show();
                                t1.setVisibility(View.INVISIBLE);
                                t2.setVisibility(View.INVISIBLE);
                                t3.setVisibility(View.INVISIBLE);
                                t4.setVisibility(View.INVISIBLE);
                                t5.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if(ACtype.contentEquals("SHOW ALL"))
                {
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Bundle bundle = getIntent().getExtras();
                            String key = bundle.getString("pin");
                            String salt = key;
                            byte[] iv = new byte[16];
                            Encryption encryption = Encryption.getDefault(key, salt, iv);
                            String gid = String.valueOf(dataSnapshot.child("Google ID").getValue());
                            String gpin = String.valueOf(dataSnapshot.child("Google Password").getValue());
                            String gdecrypted = encryption.decryptOrNull(gpin);
                            t1.setText("GOOGLE : "+gid + " : " + gdecrypted);
                            String fid = String.valueOf(dataSnapshot.child("Facebook ID").getValue());
                            String fpin = String.valueOf(dataSnapshot.child("Facebook Password").getValue());
                            String fdecrypted = encryption.decryptOrNull(fpin);
                            t2.setText("FACEBOOOK : "+fid + " : " + fdecrypted);
                            String iid = String.valueOf(dataSnapshot.child("Instagram ID").getValue());
                            String ipin = String.valueOf(dataSnapshot.child("Instagram Password").getValue());
                            String idecrypted = encryption.decryptOrNull(ipin);
                            t3.setText("INSTAGRAM : "+iid + " : " + idecrypted);
                            String lid = String.valueOf(dataSnapshot.child("LinkedIn ID").getValue());
                            String lpin = String.valueOf(dataSnapshot.child("LinkedIn Password").getValue());
                            String ldecrypted = encryption.decryptOrNull(lpin);
                            t4.setText("LINKED IN : "+lid + " : " + ldecrypted);
                            String tid = String.valueOf(dataSnapshot.child("Twitter ID").getValue());
                            String tpin = String.valueOf(dataSnapshot.child("Twitter Password").getValue());
                            String tdecrypted = encryption.decryptOrNull(tpin);
                            t5.setText("TWITTER : "+tid + " : " + tdecrypted);
                            t1.setVisibility(View.VISIBLE);
                            t2.setVisibility(View.VISIBLE);
                            t3.setVisibility(View.VISIBLE);
                            t4.setVisibility(View.VISIBLE);
                            t5.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ACtype = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
