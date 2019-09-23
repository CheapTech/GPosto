package br.com.senacrs.gposto.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class MainActivity extends AppCompatActivity {

    TextView txtCadastro;
    Button btnLogin,btnModoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCadastro = findViewById(R.id.txtCadastro);

        btnLogin = findViewById(R.id.btnLogin);
        btnModoV = findViewById(R.id.btnModoV);
    }


    public void goToLogin(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToModoV(View view) {
        Intent intent = new Intent(MainActivity.this, DestaquesActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToCadastro(View view) {
        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
