package com.example.coursesapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SimpleActivityHost extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list);
        FragmentManager fm = getSupportFragmentManager();
       Fragment fragment = fm.findFragmentById(R.id.fragment);

        if (fragment==null){
           fragment = createFragment();

            fm.beginTransaction().add(R.id.fragment,fragment).commit();
        }
    }
}
