package com.example.photographycustomer.Helper;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.multidex.MultiDex;

import com.pixplicity.easyprefs.library.Prefs;

import org.acra.ACRA;

public class Customer extends Application {

    public static Customer customer;

    @Override
    public void onCreate() {
        super.onCreate();
        customer = this;
        ACRA.init(this);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        ACRA.init(this);
    }

    public static void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}
