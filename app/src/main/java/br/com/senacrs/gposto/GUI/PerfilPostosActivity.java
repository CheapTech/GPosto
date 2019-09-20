package br.com.senacrs.gposto.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class PerfilPostosActivity extends AppCompatActivity implements TopPostosCallback , NavigationView.OnNavigationItemSelectedListener{

    private TopPostos posto;
    public TextView perfilNome,endereco,telefone;
    public TextView preco;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_perfil_postos);
        navigationDrawer();


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

    public void atualizarValor(View view) {
    }

    //NavigationDrawer (Menu)
    private void navigationDrawer() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setTitle("Perfil Usuario");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_destaques:{
                Intent intent = new Intent(this,DestaquesActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.menu_cadastrar_posto: {
                Intent intent = new Intent(this,CadastroPostosActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_editar_perfil: {
                Intent intent = new Intent(this,PerfilUsuarioActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_sair: {
                Utils.shortToast(this, "funciono3");
                break;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
