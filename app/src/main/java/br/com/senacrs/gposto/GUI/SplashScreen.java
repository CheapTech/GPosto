package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;

public class SplashScreen extends AppCompatActivity {

    public static final String USER_REF = "user_ref";
    public static final String REMEMBER_LOGIN_REF = "remember_ref";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if(getRememberLoginReference()){
            Intent intent = new Intent(SplashScreen.this,DestaquesActivity.class);
            startActivity(intent);

        }else{
            clearUserReference();
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

    private void clearUserReference(){
        SharedPreferences.Editor editorSaveUser = getSharedPreferences(USER_REF, MODE_PRIVATE).edit();
        editorSaveUser.clear();
        editorSaveUser.commit();
    }

    private boolean getRememberLoginReference() {
        SharedPreferences editorGetRememberLogin = getSharedPreferences(REMEMBER_LOGIN_REF, MODE_PRIVATE);
        return editorGetRememberLogin.getBoolean("logged", false);
    }

}

