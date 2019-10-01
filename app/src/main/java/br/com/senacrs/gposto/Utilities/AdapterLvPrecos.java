package br.com.senacrs.gposto.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.senacrs.gposto.LibClass.Combustivel;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class AdapterLvPrecos extends ArrayAdapter<Combustivel> {

    private final LayoutInflater inflater;
    private final int resourceId;

    public AdapterLvPrecos(Context context, int resource, List<Combustivel> listaPrecos) {
        super(context, resource, listaPrecos);

        this.resourceId = resource;
        inflater = LayoutInflater.from(context);
    }

    public View getView (int position, View convertView, ViewGroup parent){
        Combustivel combustivel = getItem(position);

        convertView = inflater.inflate(resourceId, parent, false);

        TextView descrPreco = convertView.findViewById(R.id.descricao_preco);

        StringBuilder strBuilder = new StringBuilder();//Constroi as descrições
        strBuilder.append(combustivel.getDescricao());
        strBuilder.append(" -  R$:");
        strBuilder.append(combustivel.getPreco());

        descrPreco.setText(strBuilder);


        return convertView;

    }


}