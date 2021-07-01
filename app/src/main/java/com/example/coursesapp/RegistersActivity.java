package com.example.coursesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Users;



public class RegistersActivity extends AppCompatActivity {
    EditText username,password,confirmepassword;
    Button register;
    DataBaseHelpers db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);

        username =(EditText) findViewById(R.id.registerusername);
        password=(EditText) findViewById(R.id.registerpassword);
        confirmepassword=(EditText) findViewById(R.id.registerconfirmepassword);
        register=(Button) findViewById(R.id.registerregister);
        db = new DataBaseHelpers(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Users user =new Users();
                // user.setUserid();
                user.setUsername(username.getText().toString());
                user.setPasseword(password.getText().toString());
                if (user.getUsername().equals("")||user.getUsername().equals("") ){

                    Toast.makeText(RegistersActivity.this,"user name or passeword is empty" ,Toast.LENGTH_LONG).show();

                }
               else if ( password.getText().toString().equals(confirmepassword.getText().toString())){
                    db.onpenDataBase();
                    db.addUser(user);
                    Toast.makeText(RegistersActivity.this,"insertion cool " +username.getText().toString(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistersActivity.this,LoginActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(RegistersActivity.this,"passeword not identic ",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}