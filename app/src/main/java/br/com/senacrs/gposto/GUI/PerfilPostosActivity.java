package br.com.senacrs.gposto.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class PerfilPostosActivity extends AppCompatActivity implements TopPostosCallback {

    private TopPostos posto;
    public TextView perfilNome;
    public TextView preco;
    public TextView endereco;
    public TextView telefone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_postos);


        Intent intent = getIntent();
        if(intent.getSerializableExtra("posto") != null){
            this.posto = (TopPostos) intent.getSerializableExtra("posto");

            TopPostosController controller = new TopPostosController();

            try {
                controller.getTopPostosWeb(posto.getId(), PerfilPostosActivity.this);
            } catch (Exception e) {
                Toast.makeText(PerfilPostosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onTopPostosSuccess(List<TopPostos> list) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(posto.getLogradouro());
        strBuilder.append(", ");
        strBuilder.append(posto.getNumero());


        perfilNome = findViewById(R.id.perfil_nome);
        endereco = findViewById(R.id.perfil_endereco);
        telefone = findViewById(R.id.perfil_telefone);


        perfilNome.setText(posto.getNomeFantasia());
        endereco.setText(strBuilder.toString());
        telefone.setText(posto.getTelefone());
    }

    @Override
    public void onTopPostosFailure(String message) {
        Toast.makeText(PerfilPostosActivity.this, "ERRO AO TRAZER POSTO", Toast.LENGTH_LONG).show();
    }
}
