package br.com.senacrs.gposto.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.senacrs.gposto.Controller.UsuarioController;
import br.com.senacrs.gposto.GUI.Callback.UsuarioCallback;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class CadastroActivity extends AppCompatActivity implements UsuarioCallback {

    TextView txtUsuarioSugerido;
    EditText editEmail,editUser,editSenha,editConfirmarSenha;
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
            Usuario usuario = new Usuario();
            usuario.setUser(editUser.getText().toString());
            usuario.setSenha(editSenha.getText().toString());
            usuario.setEmail(editEmail.getText().toString());
            UsuarioController usuarioController = new UsuarioController();
            try {
                usuarioController.postUserWeb(usuario, this);
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
