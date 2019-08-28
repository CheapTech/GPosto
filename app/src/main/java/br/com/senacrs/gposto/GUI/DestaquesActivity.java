package br.com.senacrs.gposto.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.senacrs.gposto.Controller.CombustivelController;
import br.com.senacrs.gposto.Controller.TopPostosController;
import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.GUI.Callback.TopPostosCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class DestaquesActivity extends AppCompatActivity implements CombustivelCallback, TopPostosCallback {

    private TextView textDescricao;
    public Spinner spinner;
    public RecyclerView rvTopPostos;
    public List<TopPostos> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  textDescricao = findViewById(R.id.textDescricao);

        spinner = findViewById(R.id.spinner);

     //   rvTopPostos = findViewById(R.id.rvPrecosCombustivel);


        CombustivelController controller = new CombustivelController();

        TopPostosController topPostosController = new TopPostosController();
        try {
            TopPostos topPostos = new TopPostos();


            topPostosController.getTopPostosWeb(topPostos.getId(), DestaquesActivity.this);

        } catch (Exception e) {
            Toast.makeText(DestaquesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        try {
            controller.getCombustivelWeb(DestaquesActivity.this);
        } catch (Exception e) {
            Toast.makeText(DestaquesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onCombustivelSuccess(final List<Combustivel> list) {
        final Combustivel combustivel = new Combustivel();
        combustivel.getId();

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                Combustivel combustivel = (Combustivel) parent.getItemAtPosition(position);
                Toast.makeText(DestaquesActivity.this, "Id Comb: "+combustivel.getId(), Toast.LENGTH_LONG).show();

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
    public void onTopPostosSuccess(List<TopPostos> list) {


      /*  rvTopPostos.setAdapter(new LineAdapter(list));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvTopPostos.setLayoutManager(layout);

        LineAdapter adapter = new LineAdapter(list);
        rvTopPostos.setAdapter(adapter);

        //GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //rvTopPostos.setLayoutManager(layoutManager);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvTopPostos.setLayoutManager(layoutManager);*/
    }

    @Override
    public void onTopPostosFailure(String message) {
        Toast.makeText(DestaquesActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
