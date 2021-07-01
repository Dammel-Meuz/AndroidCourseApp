package com.example.coursesapp;

import androidx.fragment.app.Fragment;

public class Product extends SimpleActivityHost {
    @Override
    protected Fragment createFragment() {
        return new FragmentProduitList();
    }
}
