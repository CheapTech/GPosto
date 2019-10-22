package br.com.senacrs.gposto.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.senacrs.gposto.Controller.CombustivelController;
import br.com.senacrs.gposto.Controller.PostosController;
import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.AvaliacaoCallback;
import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.CombustivelUpdateCallback;
import br.com.senacrs.gposto.GUI.Callback.PostosCallback;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.AdapterLvPrecos;
import br.com.senacrs.gposto.Utilities.Utils;
import okhttp3.internal.Util;

public class PerfilPostosActivity extends AppCompatActivity implements TopPostosCallback, AvaliacaoCallback , CombustivelCallback, CombustivelUpdateCallback, NavigationView.OnNavigationItemSelectedListener {

    public static final String LOGIN_SAVE = "loginref";
    SharedPreferences loginPreferences;

    public static final String STABILISHED_SESSION ="stabilishedsession";
    SharedPreferences stabilishedSession;

    private RatingBar ratingBar;
    private TopPostos posto;
    public TextView perfilNome, endereco, telefone, bairro,avaliacao,preco;
    public Button btnAvaliacao;
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

        btnAvaliacao = findViewById(R.id.btnRatingPosto);
        ratingBar = findViewById(R.id.ratingPosto);
        perfilNome = findViewById(R.id.perfil_nome);
        endereco = findViewById(R.id.perfil_endereco);
        telefone = findViewById(R.id.perfil_telefone);
        bairro = findViewById(R.id.perfil_bairro);
        avaliacao = findViewById(R.id.txtAvaliacao);

        perfilNome.setText(posto.getNomeFantasia());
        endereco.setText(strBuilder.toString());
        telefone.setText(posto.getTelefone());
        bairro.setText(posto.getBairro());
    }

    @Override
    public void onTopPostosFailure(String message) { Utils.longToast(PerfilPostosActivity.this,"ERRO AO TRAZER POSTO"); }

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
                if(getStabilishedSession()){
                    Intent intent = new Intent(this, CadastroPostosActivity.class);
                    startActivity(intent);
                }
                else{
                    Utils.longToast(this, "FAÇA LOGIN PARA ACESSAR ESSA FUNCIONALIDADE");
                }
            }
            case R.id.menu_editar_perfil: {
                if(getStabilishedSession()){
                    Intent intent = new Intent(this, PerfilUsuarioActivity.class);
                    startActivity(intent);
                }else{
                    Utils.longToast(this, "FAÇA LOGIN PARA ACESSAR ESSA FUNCIONALIDADE");
                }
            }
            case R.id.menu_sair: {
                loginPreferences = getSharedPreferences(LOGIN_SAVE, MODE_PRIVATE);
                loginPreferences.edit().clear().commit();
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
    public void onCombustivelSuccess(List<Combustivel> list) {
        if (list.size() > 0) {
            final AdapterLvPrecos adapter = new AdapterLvPrecos(PerfilPostosActivity.this, R.layout.layout_item_precos, list);//MONTA A LISTA DE PREÇOS
            lvPrecos.setAdapter(adapter);


            lvPrecos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {//PERMITE O CLIQUE NO LAYOUTITEM DA LISTA

                    if(getStabilishedSession()){
                        final Combustivel combustivel = (Combustivel) parent.getItemAtPosition(position);

                        final AlertDialog alertDialog;

                        final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilPostosActivity.this);

                        final View mView = getLayoutInflater().inflate(R.layout.alertdialog_edit_combustivel, null);//CRIA O ALERT PARA FAZER O UPDATE

                        final EditText editValor = mView.findViewById(R.id.edit_valor_combustivel);
                        final TextView editCombustivel = mView.findViewById(R.id.textCombustivel);
                        final Button btnSalvar = mView.findViewById(R.id.btn_salvar_preco);


                        editCombustivel.setText(combustivel.getDescricao());
                        editValor.setText(String.valueOf(combustivel.getPreco()));
                        editValor.requestFocus();

                        btnSalvar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Float preco = Float.valueOf(editValor.getText().toString());
                                if(preco <= 2 || preco >=6){
                                    editValor.setError("Valor incompatível");
                                    editValor.requestFocus();
                                }else{
                                    CombustivelController controller = new CombustivelController();
                                    try {
                                        controller.updateCombustivelWeb(combustivel.getIdValor(),preco,PerfilPostosActivity.this);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });

                        builder.setView(mView);
                        alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        Utils.longToast(PerfilPostosActivity.this, "FAÇA LOGIN PARA EDITAR PREÇOS");
                    }
                }
            });

        }
    }

    @Override
    public void onCombustivelFailure(String message) {
        Utils.longToast(this,message);
    }

    @Override
    public void onCombustivelUpdateSuccess(Boolean update) {
        Utils.longToast(PerfilPostosActivity.this, "Preço atualizado.");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onCombustivelUpdateFailure(String message) {
        Utils.longToast(PerfilPostosActivity.this,"Erro: "+message);
    }

    public boolean getStabilishedSession(){
        stabilishedSession = getSharedPreferences(STABILISHED_SESSION, MODE_PRIVATE);

        return stabilishedSession.getBoolean("isLogged", false);
    }

    public void sendAvaliacao(View view) {
        float aval = ratingBar.getRating();

        PostosController controller = new PostosController();
        try {
            controller.sendRatingPosto(aval,PerfilPostosActivity.this);
        } catch (Exception e) {
            Utils.longToast(this,e.getMessage());
        }
    }

    @Override
    public void onAvaliacaoSucces(String avaliacao) { Utils.longToast(PerfilPostosActivity.this,"Agradecemos sua FeedBack"); }

    @Override
    public void onAvaliacaoFailure(String message) { Utils.longToast(PerfilPostosActivity.this,"Erro: " + message); }
}
