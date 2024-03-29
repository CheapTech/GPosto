package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.R;

public class AdapterLvCombustivel extends ArrayAdapter<Combustivel> {

    private final LayoutInflater inflater;
    private final int resourceId;
    public AdapterLvCombustivel(Context context, int resource, List<Combustivel> listaCombustivel) {
        super(context, resource, listaCombustivel);

        this.resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    public View getView (int position, View convertView, ViewGroup parent){
        Combustivel combustivel = getItem(position);


        convertView = inflater.inflate(resourceId, parent, false);

        TextView textComb = convertView.findViewById(R.id.textDescrComb);

        textComb.setText(combustivel.getDescricao());


        return convertView;

    }
}
