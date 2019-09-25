package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class CadastroPostosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextInputEditText editNFantasia,editLogradouro,editBairro,editTel;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FloatingActionButton btnCadstrarPosto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_posto);
        navigationDrawer();

        editNFantasia = findViewById(R.id.editNomeFantasia);
        editLogradouro = findViewById(R.id.editLogradouro);
        editBairro = findViewById(R.id.editBairro);
        editTel = findViewById(R.id.editTel);

        btnCadstrarPosto = findViewById(R.id.btnCadastrarPosto);
    }

    public void cadastrarPosto(View view) {
        if (!testarCampos()){
            String nfantasia = editNFantasia.getText().toString();
            String logradouro = editLogradouro.getText().toString();
            String bairro = editBairro.getText().toString();
            String tel = editTel.getText().toString();
        }
    }



    public boolean testarCampos(){

        boolean testarCampos = true;

        if(editNFantasia.getText().toString().trim().isEmpty()){
            editNFantasia.setError("Campo Obrigatorio");
        }else {
            if (editLogradouro.getText().toString().trim().isEmpty()){
                editLogradouro.setError("Campo Obrigatorio");
            }else {
                if (editBairro.getText().toString().trim().isEmpty()){
                    editBairro.setError("Campo Obrigatorio");
                }
            }
        }
        return testarCampos;
    }

    private void navigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Cadastro Posto");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);

        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_destaques:{
                Intent intent = new Intent(this,DestaquesActivity.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.menu_cadastrar_posto: {
                Utils.shortToast(this, "?????___??????");
                break;
            }
            case R.id.menu_editar_perfil: {
                Intent intent = new Intent(this,PerfilUsuarioActivity.class);
                startActivity(intent);
                finish();
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
