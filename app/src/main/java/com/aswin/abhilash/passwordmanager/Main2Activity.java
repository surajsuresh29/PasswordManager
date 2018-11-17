package com.aswin.abhilash.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button storepasswords =findViewById(R.id.storepasswords);
        Button viewpasswords = findViewById(R.id.viewpasswords);
        Bundle bundle = getIntent().getExtras();
        final String pin = bundle.getString("pin");
        final String userid = bundle.getString("userid");

        storepasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storepswd=new Intent(getApplicationContext(),StorePassword.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("pin",pin);
                storepswd.putExtras(bundle);
                startActivity(storepswd);

            }
        });
        viewpasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewpswd=new Intent(getApplicationContext(),GetPassword.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("pin",pin);
                viewpswd.putExtras(bundle);
                startActivity(viewpswd);
            }
        });
    }
}
