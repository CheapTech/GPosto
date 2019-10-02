package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.senacrs.gposto.Controller.UsuarioLoginController;
import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class LoginActivity extends AppCompatActivity implements UsuarioCallback {

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

    public void login(View view) {
        if(!areEmptyFields()){
            userLogin();
        }
    }

    private void userLogin() {
        UsuarioLoginController controller = new UsuarioLoginController();
        try {
            controller.getUsuarioWeb(editEmail.getText().toString(), editSenha.getText().toString(), this);
        } catch (Exception e) {
            android.widget.Toast.makeText(this, e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
        }
    }

    private boolean areEmptyFields() {
        boolean isEmpty = true;

        if (editEmail.getText().toString().trim().isEmpty()) {
            editEmail.setError("CAMPO VAZIO!");
        } else {
            if (editSenha.getText().toString().trim().isEmpty()) {
                editSenha.setError("CAMPO VAZIO!");
            } else {
                isEmpty = false;
            }

        }
        return isEmpty;
    }


    @Override
    public void onUsuarioSuccess(Usuario usuario) {
        Utils.longToast(this, "LOGIN BEM SUCEDIDO!");
        startActivity(new Intent(this, DestaquesActivity.class));
    }

    @Override
    public void onUsuarioFailure(String message) {
        Utils.longToast(this, message);
    }
}
