package br.com.senacrs.gposto.GUI;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import br.com.senacrs.gposto.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editEmail,editSenha;
    Button btnLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);

        btnLogin = findViewById(R.id.btnLogin);

    }
}
