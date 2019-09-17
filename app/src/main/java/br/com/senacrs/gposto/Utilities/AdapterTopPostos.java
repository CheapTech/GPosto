package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.senacrs.gposto.GUI.PerfilPostosActivity;
import br.com.senacrs.gposto.LibClass.OnItemViewHolderClick;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class AdapterTopPostos extends RecyclerView.Adapter<HolderTopPostos> implements OnItemViewHolderClick {

    private List<TopPostos> list;
    private Context context;

    public AdapterTopPostos(List postos, Context context) {

        this.list = postos;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderTopPostos onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_destaque, viewGroup, false);
        HolderTopPostos holderTopPostos = new HolderTopPostos(view, this);

        return holderTopPostos;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTopPostos holder, int position) {

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(list.get(position).getLogradouro());
        strBuilder.append(", ");
        strBuilder.append(list.get(position).getNumero());

        holder.nomeFantasia.setText(list.get(position).getNomeFantasia());
        holder.logradouro.setText(strBuilder.toString());
        holder.bairro.setText(list.get(position).getBairro());
        holder.preco.setText(list.get(position).getPreco());
        holder.atualizado.setText(list.get(position).getAtualizado());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClicked(int itemPosition) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("posto", list.get(itemPosition));

        Intent intent = new Intent(context, PerfilPostosActivity.class);
        intent.putExtras(bundle);

        context.startActivity(intent);
    }
}