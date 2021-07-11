package com.example.coursesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Liste;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Edit_List extends AppCompatActivity {
    EditText listName;
    EditText montantPrevue;
    Button editer;
    DataBaseHelpers db;
    int idlistrecup;
    Liste liste ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_course);

        listName = (EditText) findViewById(R.id.listname);
        montantPrevue =(EditText) findViewById(R.id.montantPrevue);
        editer = (Button) findViewById(R.id.ajoutList);
        db = new DataBaseHelpers(this);
        idlistrecup = (int)getIntent().getSerializableExtra("id");
        liste=new Liste();
        liste = db.oneliste(idlistrecup);

        listName.setText(liste.getListName());
        montantPrevue.setText(liste.getMontantPrevue());


        editer.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MMMM/yyyy 'à' hh:mm");
                liste =new Liste();
                liste.setListName(listName.getText().toString());
                liste.setMontantPrevue(montantPrevue.getText().toString());
                liste.setDateModification(LocalDateTime.now().format(formater));
                db.onpenDataBase();
                db.updatList(idlistrecup,liste);

                Toast.makeText(getApplicationContext(),"id = "+idlistrecup ,Toast.LENGTH_LONG).show();

            }
        });
    }
}