package com.example.coursesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Liste;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class AddListCourseActivity extends AppCompatActivity {

    EditText listName;
    EditText montantPrevue;
    EditText dateDeCreation;
    EditText dateDeModification;
    Button ajouter;
    DataBaseHelpers db;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_course);

        listName = (EditText) findViewById(R.id.listname);
        montantPrevue =(EditText) findViewById(R.id.montantPrevue);
        //dateDeCreation = (EditText) findViewById(R.id.datecreation);
       // dateDeModification = (EditText) findViewById(R.id.datemodification);
        ajouter = (Button) findViewById(R.id.ajoutList);
        db = new DataBaseHelpers(this);


      //  DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
        //    @Override
          //  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //    calendar.set(Calendar.YEAR,year);
              //  calendar.set(Calendar.MONTH,month);
                //calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
               // updateLabel();
            //}
        //};
       // dateDeCreation.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   new DatePickerDialog(AddListCourseActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            //}
        //});


        ajouter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Liste list = new Liste();
                if (listName.getText().toString().equals("") || montantPrevue.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Veillez remplire tous les champs", Toast.LENGTH_LONG).show();

                } else {
                    DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MMMM/yyyy 'à' hh:mm");
                    list.setListName(listName.getText().toString());
                    list.setMontantPrevue(montantPrevue.getText().toString());
                    list.setDateCreation(LocalDateTime.now().format(formater));
                    //list.setDateCreation(dateDeCreation.getText().toString());
                    list.setDateModification("");
                    //list.setDateCreation( dateDeCreation.getDayOfMonth() +"/"+dateDeCreation.getMonth()+"/"+dateDeCreation.getYear() );
                    // list.setDateModification(dateDeCreation.getMonth() +"/"+dateDeCreation.getDayOfMonth()+"/"+dateDeCreation.getYear());
                    db.onpenDataBase();
                    db.addList(list);
                    // Toast.makeText(AddListCourseActivity.this,"insertion cool " +dateDeCreation.getDayOfMonth(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddListCourseActivity.this, ListActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    //private void updateLabel() {
      //  String myFormat = "dd/MMM/yyyy";
        //SimpleDateFormat sdf =new SimpleDateFormat(myFormat, Locale.FRANCE);
        //dateDeCreation.setText(sdf.format(calendar.getTime()));
   // }
}