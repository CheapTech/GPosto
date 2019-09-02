package br.com.senacrs.gposto.GUI;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import br.com.senacrs.gposto.Controller.UsuarioController;
import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class CadastroActivity extends AppCompatActivity implements UsuarioCallback {

    TextView txtUsuarioSugerido;
    TextInputEditText editEmail,editUser,editSenha,editConfirmarSenha;
    Button btnCadastrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtUsuarioSugerido = findViewById(R.id.txtUsuarioSugerido);

        editEmail = findViewById(R.id.editEmail);
        editUser = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);
        editConfirmarSenha = findViewById(R.id.editConfirmarSenha);

        btnCadastrar = findViewById(R.id.btnCadastrar);
    }

    public void cadastrarUsuario(View view) {
        if (!testarCampos()) {
            String email = editEmail.getText().toString();
            String user = editUser.getText().toString();
            String senha = editSenha.getText().toString();

            Usuario body = new Usuario(user, senha, email);
            UsuarioController usuarioController = new UsuarioController();
            try {
                usuarioController.sendUsuarioWeb(body, this);
            } catch (Exception e) {
                Utils.longToast(this, e.getMessage());
            }
        }
    }

    public boolean testarCampos(){
        boolean testarCampos = true;
        if (editEmail.getText().toString().trim().isEmpty()){
            editEmail.setError("Campo Obrigatorio");
        } else {
            if (editUser.getText().toString().trim().isEmpty()){
                editUser.setError("Campo Obrigatorio");
            } else {
                if (editSenha.getText().toString().trim().isEmpty()){
                    editSenha.setError("Campo Obrigatorio");
                } else {
                    if (editConfirmarSenha.getText().toString().trim().isEmpty()){
                        editConfirmarSenha.setError("Campo Obrigatorio");
                    } else {
                        if (editSenha.getText().toString().trim().equals(editConfirmarSenha.getText().toString().trim())){
                            testarCampos = false;
                        } else {
                            Utils.shortToast(this, "Senhas Diferentes");
                        }
                    }
                }
            }
        }
        return testarCampos;
    }

    @Override
    public void onUsuarioSuccess(Usuario usuario) { Utils.longToast(CadastroActivity.this,"Cadastro Efetuado com Sucesso"); }

    @Override
    public void onUsuarioFailure(String message) { Utils.longToast(CadastroActivity.this,message); }

    @Override
    public void onBackPressed() { finish(); }
}
