package com.aswin.abhilash.passwordmanager;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import se.simbio.encryption.Encryption;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StorePassword extends Activity implements AdapterView.OnItemSelectedListener {

    String ACtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_password);
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        final EditText getuserid = findViewById(R.id.getuserid);
        final EditText getpassword = findViewById(R.id.getpassword);
        final Button encryptstore = findViewById(R.id.encrypt_store);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        encryptstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getuserid.getText()))
                {
                    getuserid.setError("Please enter a User ID");
                    return;
                }
                else if(TextUtils.isEmpty(getpassword.getText()))
                {
                    getpassword.setError("Please enter a Password");
                    return;
                }
                else if(ACtype.contentEquals("ACCOUNT--TYPE"))
                {
                    Toast.makeText(StorePassword.this, "Please select an account type.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Bundle bundle = getIntent().getExtras();
                    String userid = bundle.getString("userid");
                    String key = bundle.getString("pin");
                    String salt = key;
                    byte[] iv = new byte[16];
                    Encryption encryption = Encryption.getDefault(key, salt, iv);
                    String actuserid = getuserid.getText().toString();
                    String acpassword = getpassword.getText().toString();
                    String encrypted = encryption.encryptOrNull(acpassword);
                    DatabaseReference dbref = database.getReference(userid);
                    DatabaseReference acref = dbref.child(ACtype+" ID");
                    acref.setValue(actuserid);
                    DatabaseReference acpass = dbref.child(ACtype+" Password");
                    acpass.setValue(encrypted);
                    Toast.makeText(StorePassword.this, encrypted, Toast.LENGTH_LONG).show();
                    String decrypted = encryption.decryptOrNull(encrypted);
                    Toast.makeText(StorePassword.this, decrypted, Toast.LENGTH_LONG).show();
                    Toast.makeText(StorePassword.this, "User id : "+getuserid.getText().toString()+"\n"+"Password : "+getpassword.getText().toString()+"\n"+"Account Type : "+ACtype, Toast.LENGTH_SHORT).show();

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
