package br.com.senacrs.gposto.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class PerfilPostosActivity extends AppCompatActivity {

    private TopPostos posto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_postos);

        Intent intent = getIntent();
        if(intent.getSerializableExtra("posto") != null){
            this.posto = (TopPostos) intent.getSerializableExtra("posto");
        }



    }
}
