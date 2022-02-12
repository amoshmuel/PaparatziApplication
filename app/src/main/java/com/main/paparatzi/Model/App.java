package com.main.paparatzi.Model;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.main.paparatzi.Control.FireBase;

public class App extends Application {
    @Override

    public void onCreate() {
        Log.d("pttt", "onCreate: APP ");
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FireBase.init(this);
    }
}
