package com.example.coursesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.userNameLogin);
        password = (EditText) findViewById(R.id.loginPasserword);
        login = (Button) findViewById(R.id.loginLogin);
        DataBaseHelpers db = new DataBaseHelpers(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")||pass.equals("")){
                    Toast.makeText(LoginActivity.this," Tout les champs doivent etre remplis ",Toast.LENGTH_LONG).show();
                }else {
                    Boolean checkpass =db.checkPasswordUserName(pass,user);
                    Boolean checkuserName = db.checkUserName(user);
                    if (checkpass ==true){
                        Toast.makeText(LoginActivity.this," connection valide ",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(LoginActivity.this,ListActivity.class);
                        startActivity(intent);
                    }else if (checkuserName == true && checkpass == false) {

                        Toast.makeText(LoginActivity.this," mot de pass invalide",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(LoginActivity.this," login et mot de pass invalide",Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

    }
}