package br.com.senacrs.gposto.GUI;

import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.senacrs.gposto.GUI.Callback.CombustivelCallback;
import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.R;
import br.com.senacrs.gposto.Utilities.AdapterLvCombustivel;
import br.com.senacrs.gposto.Utilities.Utils;

public class CombustivelActivity extends AppCompatActivity implements CombustivelCallback {

    public ListView lvCombustivel;

    public void popularLvCombustivel (List<Combustivel> combustivelList){
        AdapterLvCombustivel adapter = new AdapterLvCombustivel(CombustivelActivity.this, R.layout.layout_item_lv_combustivel, combustivelList);
        lvCombustivel.setAdapter(adapter);
    }


    @Override
    public void onCombustivelSuccess(List<Combustivel> list) {

        if(list.size() > 0){
            popularLvCombustivel(list);
        }

    }

    @Override
    public void onCombustivelFailure(String message) {
        Utils.shortToast(this,message);
    }
}
