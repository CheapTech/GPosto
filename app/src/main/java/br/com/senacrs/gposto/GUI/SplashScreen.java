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
    public static final String STABILISHED_SESSION ="stabilishedsession";

    SharedPreferences stabilishedSession;
    SharedPreferences.Editor editorSession;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        stabilishedSession = getSharedPreferences(STABILISHED_SESSION, MODE_PRIVATE);

        clearStabilishedSession();

        if(getSavedLogin() == true){
            stabilishSession();
            Intent intent = new Intent(SplashScreen.this,DestaquesActivity.class);
            startActivity(intent);

        }else{
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2500);
        }
    }

    private boolean getSavedLogin() {
        SharedPreferences prefs = getSharedPreferences(LOGIN_SAVE, MODE_PRIVATE);
        return prefs.getBoolean("savelogin", false);
    }

    private void clearStabilishedSession(){
        editorSession = stabilishedSession.edit();
        editorSession.clear();
        editorSession.commit();
    }

    private boolean stabilishSession(){
        editorSession=stabilishedSession.edit();
        editorSession.putBoolean("isLogged", true);
        editorSession.commit();

        return stabilishedSession.getBoolean("isLogged", false);
    }
}

