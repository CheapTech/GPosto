package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

import br.com.senacrs.gposto.Controller.BandeiraController;
import br.com.senacrs.gposto.Controller.PostosController;
import br.com.senacrs.gposto.GUI.Callback.BandeiraCallback;
import br.com.senacrs.gposto.GUI.Callback.PostosCallback;
import br.com.senacrs.gposto.LibClass.Bandeira;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.Usuario;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.Utils;

public class CadastroPostosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PostosCallback, BandeiraCallback {

    public static final String USER_REF = "user_ref";

    TextInputEditText editNFantasia,editLogradouro,editNumero,editBairro,editTel;
    Toolbar toolbar;
    Spinner spBandeira;
    private ImageView nav_photo_user;
    private TextView nav_user,nav_email;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton btnCadstrarPosto;
    int id_bandeira = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_posto);
        navigationDrawer();
        fieldSpinner();
        editNFantasia = findViewById(R.id.editNomeFantasia);
        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editTel = findViewById(R.id.editTel);
        spBandeira = findViewById(R.id.spBandeira);
        btnCadstrarPosto = findViewById(R.id.btnCadastrarPosto);
    }

    private Usuario getSavedUserReference(){
        Usuario usuario;

        SharedPreferences editorGetSavedUser = getSharedPreferences(USER_REF, MODE_PRIVATE);

        String user = editorGetSavedUser.getString("user", null);
        if(user != null){
            usuario = new Usuario();
            usuario.setId(editorGetSavedUser.getInt("id", 0));
            usuario.setUser(editorGetSavedUser.getString("user", ""));
            usuario.setSenha(editorGetSavedUser.getString("senha", ""));
            usuario.setEmail(editorGetSavedUser.getString("email", ""));

            return usuario;
        }else{
            return null;
        }
    }


    private void fieldSpinner(){
        BandeiraController controller = new BandeiraController();
        try {
            controller.getBandeiraWeb(CadastroPostosActivity.this);
        } catch (Exception e) {
            Utils.longToast(this,e.getMessage());
        }
    }

    public void cadastrarPosto(View view) {
        if (!testarCampos()){
            Postos posto = new Postos();
            posto.setNomeFantasia(editNFantasia.getText().toString());
            posto.setId_bandeira(id_bandeira);
            posto.setLogradouro(editLogradouro.getText().toString());
            posto.setBairro(editBairro.getText().toString());
            posto.setNumero(editNumero.getText().toString());
            posto.setTel(editTel.getText().toString());

            PostosController postosController = new PostosController();
            try {
                postosController.postPostosWeb(posto, this);
            } catch (Exception e) {
                Utils.longToast(this, e.getMessage());
            }
        }
    }

    private void clearEditText(){
        editNFantasia.setText("");
        editLogradouro.setText("");
        editBairro.setText("");
        editNumero.setText("");
        editTel.setText("");
        editNFantasia.requestFocus();
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
                    if (id_bandeira == 0){
                        Utils.shortToast(this, "Selecione uma Marca");
                    }else {
                        testarCampos = false;
                    }
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

        nav_photo_user = navView.findViewById(R.id.nav_header_photo);
        nav_user= navView.findViewById(R.id.nav_header_user);
        nav_email = navView.findViewById(R.id.nav_header_email);

        Usuario usuario = getSavedUserReference();
        if (usuario != null){
            nav_user.setText(usuario.getUser());
            nav_email.setText(usuario.getEmail());
        }else {
            String userNull = "Modo Visitante";
            nav_user.setText(userNull);
            nav_email.setVisibility(View.GONE);
            nav_photo_user.setVisibility(View.INVISIBLE);
        }
        navigationView.setNavigationItemSelectedListener(this);

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
                if(getSavedUserReference() != null){
                    Intent intent = new Intent(this, DestaquesActivity.class);
                    startActivity(intent);
                }else{
                    Utils.shortToast(this, "FAÇA LOGIN PARA ACESSAR ESSA FUNCIONALIDADE");
                }
                break;
            }

            case R.id.menu_cadastrar_posto: {
                if(getSavedUserReference() != null){
                    Intent intent = new Intent(this, CadastroPostosActivity.class);
                    startActivity(intent);
                }else{
                    Utils.shortToast(this, "FAÇA LOGIN PARA ACESSAR ESSA FUNCIONALIDADE");
                }
                break;
            }
            case R.id.menu_editar_perfil: {
                if(getSavedUserReference() != null){
                    Intent intent = new Intent(this, PerfilUsuarioActivity.class);
                    startActivity(intent);
                }else{
                    Utils.shortToast(this, "FAÇA LOGIN PARA ACESSAR ESSA FUNCIONALIDADE");
                }
                break;
            }
            case R.id.menu_sair: {
                clearUserReference();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
    public void onPostosSuccess(Postos postos) {
        Utils.shortToast(CadastroPostosActivity.this,"Cadastro Efetuado com Sucesso");
        clearEditText();
    }

    @Override
    public void onPostosFailure(String message) { Utils.longToast(CadastroPostosActivity.this,message); }

    @Override
    public void onBandeiraSuccess(List<Bandeira> list)  {
        final Bandeira bandeira = new Bandeira();
        bandeira.setId(0);
        bandeira.setMarca("Selecione");
        bandeira.setLogo("");
        List<Bandeira> newList = new ArrayList<>();
        newList.add(bandeira);

        for (int i = 0 ; i < list.size(); i++ ){
            newList.add(list.get(i));
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,newList);

        spBandeira.setAdapter(adapter);

        spBandeira.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bandeira bandeiras = (Bandeira) parent.getItemAtPosition(position);
                id_bandeira = bandeiras.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBandeiraFailure(String message) {
        Utils.longToast(this,message);
    }

    private void clearUserReference(){
        SharedPreferences.Editor editorSaveUser = getSharedPreferences(USER_REF, MODE_PRIVATE).edit();
        editorSaveUser.clear();
        editorSaveUser.commit();
    }
}
