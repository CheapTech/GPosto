package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.senacrs.gposto.R;

public class SplashScreen extends AppCompatActivity {

    public static final String LOGIN_SAVE = "loginref";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        if (getSavedLogin()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, DestaquesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2500);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2500);
        }
    }

    private boolean getSavedLogin() {
        SharedPreferences prefs = getSharedPreferences(LOGIN_SAVE, MODE_PRIVATE);
        return prefs.getBoolean("savelogin", false);
    }
}

