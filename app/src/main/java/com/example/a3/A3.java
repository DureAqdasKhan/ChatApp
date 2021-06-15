package com.example.a3;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class A3 extends android.app.Application  {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
