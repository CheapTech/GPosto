package br.com.senacrs.gposto.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.senacrs.gposto.Controller.CombustivelController;
import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.AdapterTopPostos;
import br.com.senacrs.gposto.Utilities.Utils;

public class DestaquesActivity extends AppCompatActivity implements CombustivelCallback, TopPostosCallback, NavigationView.OnNavigationItemSelectedListener {

    public static final String LOGIN_SAVE = "loginref";
    SharedPreferences loginPreferences;

    private FloatingActionButton btnVerTodos;
    private RadioButton rbtn_SearchPosto,rbtn_SearchBairro;
    private LinearLayout layout_searchPosto;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private SearchView searchPosto;
    public Spinner spinner;
    public RecyclerView rvTopPostos;
    public List<TopPostos> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_destaques);
        fieldSpinner();
        navigationDrawer();

        btnVerTodos = findViewById(R.id.btnVerTodos);
        searchPosto = findViewById(R.id.search_posto);
        spinner = findViewById(R.id.spinner);
        layout_searchPosto = findViewById(R.id.layout_searchPosto);
        rbtn_SearchPosto = findViewById(R.id.search_by_Posto);
        rbtn_SearchBairro = findViewById(R.id.search_by_Bairro);
        rvTopPostos = findViewById(R.id.rvPrecosCombustivel);
    }

    private void fieldSpinner(){
        CombustivelController controller = new CombustivelController(); // classe chamada pra trazer os combustiveis, será usada no spinner
        try {
            controller.getCombustivelWeb(DestaquesActivity.this);
        } catch (Exception e) {
            Toast.makeText(DestaquesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCombustivelSuccess(final List<Combustivel> list) {//monta as opções de combustiveis no spinner
        final Combustivel combustivel = new Combustivel();
        combustivel.getId();

        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//Quando selecionado o combustivel monta a lista de destaques por ordem
                Combustivel combustivel = (Combustivel) parent.getItemAtPosition(position);
                TopPostosController controller = new TopPostosController();
                try {
                    controller.getTopPostosWeb(combustivel.getId(), DestaquesActivity.this);
                } catch (Exception e) {
                    Toast.makeText(DestaquesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onCombustivelFailure(String message) {
        Toast.makeText(DestaquesActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTopPostosSuccess(List<TopPostos> list) {//Monta a lista de postos com o combustivel especificado recebida da api
        final AdapterTopPostos adapter = new AdapterTopPostos(list, DestaquesActivity.this);

        rvTopPostos.setAdapter(adapter);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvTopPostos.setLayoutManager(layout);

        searchPosto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (rbtn_SearchPosto.isChecked()){
                    adapter.getFilter().filter(newText);
                }else {
                    if (rbtn_SearchBairro.isChecked()){
                        adapter.getFilterBairro().filter(newText);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onTopPostosFailure(String message) {
        Utils.longToast(this, message);
    }

    private void navigationDrawer() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);View navView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        TextView nav_user = navView.findViewById(R.id.nav_header_user);
        TextView nav_email = navView.findViewById(R.id.nav_header_email);
        ImageView nav_photo = navView.findViewById(R.id.nav_header_photo);

        String user = "teste";
        String email = "Teste@gmail.com";

        nav_email.setText(email);
        nav_user.setText(user);

        toolbar.setTitle("Destaques");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_destaques: {
                Utils.shortToast(this, "?????___??????");
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
                loginPreferences = getSharedPreferences(LOGIN_SAVE, MODE_PRIVATE);
                loginPreferences.edit().clear().commit();
                Intent intent = new Intent(this, LoginActivity.class);
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

    public void verTodosPostos(View view) {
        if (layout_searchPosto.getVisibility() == View.VISIBLE) {
            layout_searchPosto.setVisibility(View.GONE);
        } else {
            layout_searchPosto.setVisibility(View.VISIBLE);
            searchPosto.requestFocus();
        }
    }
}
