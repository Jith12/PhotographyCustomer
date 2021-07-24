package com.example.photographycustomer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.photographycustomer.R;
import com.pixplicity.easyprefs.library.Prefs;

public class SplashActivity extends AppCompatActivity {

    private boolean check;
    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        check = Prefs.getBoolean("loggedIn", false);


        if (check){
            new Handler().postDelayed(() -> {
                Intent homeint = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(homeint);
                Animatoo.animateFade(SplashActivity.this);
                finish();
            }, 1000);
        }else{
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                Animatoo.animateFade(SplashActivity.this);
                finish();
            }, 1000);
        }
    }
}