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
                EditText loginpswd = findViewById(R.id.loginpassword);
                String pass = loginpswd.getText().toString();
                if(TextUtils.isEmpty(pass))
                {
                    loginpswd.setError("Please enter your pin.");
                }
                else if(pass.contentEquals("123456")) {
                    Intent login = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(login);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "PLEASE ENTER CORRECT PIN", Toast.LENGTH_LONG).show();
                }

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
