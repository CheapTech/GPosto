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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.senacrs.gposto.Controller.CombustivelController;
import br.com.senacrs.gposto.Controller.PostosController;
import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.AdapterLvPrecos;
import br.com.senacrs.gposto.Utilities.Utils;

public class PerfilPostosActivity extends AppCompatActivity implements TopPostosCallback, CombustivelCallback, NavigationView.OnNavigationItemSelectedListener {

    private TopPostos posto;
    public TextView perfilNome, endereco, telefone, bairro;
    public TextView preco;
    public ListView lvPrecos;


    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_perfil_postos);
        navigationDrawer();

        lvPrecos = findViewById(R.id.lv_precos);


        Intent intent = getIntent();
        if (intent.getSerializableExtra("posto") != null) {
            this.posto = (TopPostos) intent.getSerializableExtra("posto");

            TopPostosController controller = new TopPostosController();

            try {
                controller.getTopPostosWeb(posto.getIdPosto(), PerfilPostosActivity.this);
            } catch (Exception e) {
                Toast.makeText(PerfilPostosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


        CombustivelController combustivelController = new CombustivelController();

        try {
            combustivelController.getCombustivelById(posto.getIdPosto(), PerfilPostosActivity.this);
        } catch (Exception e) {
            Toast.makeText(PerfilPostosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        bairro = findViewById(R.id.perfil_bairro);


        perfilNome.setText(posto.getNomeFantasia());
        endereco.setText(strBuilder.toString());
        telefone.setText(posto.getTelefone());
        bairro.setText(posto.getBairro());
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

            case R.id.menu_destaques: {
                Intent intent = new Intent(this, DestaquesActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.menu_cadastrar_posto: {
                Intent intent = new Intent(this, CadastroPostosActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu_editar_perfil: {
                Intent intent = new Intent(this, PerfilUsuarioActivity.class);
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


    @Override
    public void onCombustivelSuccess(List<Combustivel> list) {

        if (list.size() > 0) {
            AdapterLvPrecos adapter = new AdapterLvPrecos(PerfilPostosActivity.this, R.layout.layout_item_precos, list);
            lvPrecos.setAdapter(adapter);
            lvPrecos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Combustivel combustivel = (Combustivel) parent.getItemAtPosition(position);
                    Toast.makeText(PerfilPostosActivity.this, combustivel.getDescricao() + " " + combustivel.getId(), Toast.LENGTH_LONG).show();

                    //TODO editar preço dos combustiveis
                }
            });
        } else {

        }
    }

    @Override
    public void onCombustivelFailure(String message) {
        Toast.makeText(PerfilPostosActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
