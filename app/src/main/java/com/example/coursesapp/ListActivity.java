package com.example.coursesapp;

import androidx.fragment.app.Fragment;

public class ListActivity extends SimpleActivityHost {
    @Override
    protected Fragment createFragment() {
        return new FragmentList();
    }
}
