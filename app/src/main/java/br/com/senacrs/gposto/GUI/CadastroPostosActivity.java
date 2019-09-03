package br.com.senacrs.gposto.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import br.com.senacrs.gposto.R;

public class CadastroPostosActivity extends AppCompatActivity {

    TextInputEditText editRSocial,editNFantasia,editCNPJ,editCEP,editLogradouro,editNumero,editBairro,editCidade,editEstado, editTel;
    ImageView imagePosto;
    Button btnCadstrarPosto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_posto);

        editRSocial = findViewById(R.id.editRazaoSocial);
        editNFantasia = findViewById(R.id.editNomeFantasia);
        editCNPJ = findViewById(R.id.editCNPJ);
        editCEP = findViewById(R.id.editCEP);
        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);
        editTel = findViewById(R.id.editTel01);

        imagePosto = findViewById(R.id.imagePosto);

        btnCadstrarPosto = findViewById(R.id.btnCadastrarPosto);
    }

    public void cadastrarPosto(View view) {
        if (!testarCampos()){

        }
    }

    public void salvarImage(View view) {
    }

    public boolean testarCampos(){

        boolean testarCampos = true;

        if (editNFantasia.getText().toString().trim().isEmpty()){
            editNFantasia.setError("Campo Obrigatorio");
        }else {
            if (editCNPJ.getText().toString().trim().isEmpty()){
                editCNPJ.setError("Campo Obrigatorio");
            }else {
                if (editCEP.getText().toString().trim().isEmpty()){
                    editCEP.setError("Campo Obrigatorio");
                }else {
                    if (editLogradouro.getText().toString().trim().isEmpty()){
                        editLogradouro.setError("Campo Obrigatorio");
                    }else {
                        if (editNumero.getText().toString().trim().isEmpty()){
                            editNumero.setError("Campo Obrigatorio");
                        }else {
                            if (editEstado.getText().toString().trim().isEmpty()){
                                editEstado.setError("Campo Obrigatorio");
                            }else {
                                if (editBairro.getText().toString().trim().isEmpty()){
                                    editBairro.setError("Campo Obrigatorio");
                                }else {
                                    if (editCidade.getText().toString().trim().isEmpty()){
                                        editCidade.setError("Campo Obrigatorio");
                                    }else {
                                        if (editTel.getText().toString().trim().isEmpty()){
                                            editTel.setError("Campo Obrigatorio");
                                        }else {
                                            testarCampos = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testarCampos;
    }
}
