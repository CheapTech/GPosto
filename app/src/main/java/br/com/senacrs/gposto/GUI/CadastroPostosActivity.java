package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import br.com.senacrs.gposto.Controller.PostosController;
import br.com.senacrs.gposto.GUI.Callback.PostosCallback;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class CadastroPostosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PostosCallback {

    TextInputEditText editNFantasia,editLogradouro,editNumero,editBairro,editTel;
    Toolbar toolbar;
    Spinner spBandeira;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton btnCadstrarPosto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_posto);
        navigationDrawer();
        editNFantasia = findViewById(R.id.editNomeFantasia);
        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editTel = findViewById(R.id.editTel);
        spBandeira = findViewById(R.id.spBandeira);

        btnCadstrarPosto = findViewById(R.id.btnCadastrarPosto);
    }

    //
    //!!!!!! Ajustar Cadstro Postos !!!!!!
    //

    public void cadastrarPosto(View view) {
        if (!testarCampos()){
            int id_bandira = 0;
            String nfantasia = editNFantasia.getText().toString();
            String logradouro = editLogradouro.getText().toString();
            String numero = editNumero.getText().toString();
            String bairro = editBairro.getText().toString();
            String tel = editTel.getText().toString();

            Postos body = new Postos(nfantasia,logradouro,bairro,tel,numero,id_bandira);
            PostosController postosController = new PostosController();
            try {
                postosController.postPostosWeb(body, this);
            } catch (Exception e) {
                Utils.longToast(this, e.getMessage());
            }
        }
    }

    public boolean testarCampos(){
        boolean testarCampos = true;

        if (editNFantasia.getText().toString().trim().isEmpty()){
            editNFantasia.setError("Campo Obrigatorio");
        }else{
            if (editLogradouro.getText().toString().trim().isEmpty()){
                editLogradouro.requestFocus();
                editLogradouro.setError("Campo Obrigatorio");
            }else {
                if (editBairro.getText().toString().trim().isEmpty()){
                    editBairro.requestFocus();
                    editBairro.setError("Campo Obrigatorio");
                }else {
                    testarCampos = false;
                }
            }
        }
        return testarCampos;
    }

    private void navigationDrawer() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        View navView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);

        TextView nav_user = navView.findViewById(R.id.nav_header_user);
        TextView nav_email = navView.findViewById(R.id.nav_header_email);
        ImageView nav_photo = navView.findViewById(R.id.nav_header_photo);

        String user = "teste";
        String email = "Teste@gmail.com";

        nav_email.setText(email);
        nav_user.setText(user);

        toolbar.setTitle("Cadastro Posto");
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

    @Override
    public void onPostosSuccess(Postos postos) { Utils.longToast(CadastroPostosActivity.this,"Cadastro Efetuado com Sucesso"); }

    @Override
    public void onPostosFailure(String message) { Utils.longToast(CadastroPostosActivity.this,message); }
}
