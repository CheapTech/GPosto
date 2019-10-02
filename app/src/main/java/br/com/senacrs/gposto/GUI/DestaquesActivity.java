package br.com.senacrs.gposto.GUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import br.com.senacrs.gposto.Controller.CombustivelController;
import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.AdapterTopPostos;
import br.com.senacrs.gposto.Utilities.Utils;
import okhttp3.internal.Util;

public class DestaquesActivity extends AppCompatActivity implements CombustivelCallback, TopPostosCallback, NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton btnVerTodos;
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
        //rvTopPostos.setAdapter(new AdapterTopPostos(list, DestaquesActivity.this));
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
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onTopPostosFailure(String message) {
        Utils.longToast(this, message);
    }

    private void setStateSearchView() {
        if (searchPosto.getVisibility() == View.VISIBLE) {
            searchPosto.setVisibility(View.GONE);
        } else {
            searchPosto.setVisibility(View.VISIBLE);
        }
    }

    private void navigationDrawer() {
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);

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

    public void verTodosPostos(View view) {
        setStateSearchView();
    }
}
