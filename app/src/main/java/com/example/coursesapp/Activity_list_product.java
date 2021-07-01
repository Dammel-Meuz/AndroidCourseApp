package com.example.coursesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class Activity_list_product extends SimpleActivityHostProduct {


    @Override
    protected Fragment createFragment() {
        return new FragmentProduitList();
    }
}