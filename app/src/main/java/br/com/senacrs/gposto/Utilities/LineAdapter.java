package br.com.senacrs.gposto.Utilities;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> {

    private List<TopPostos> list;

    public LineAdapter(List postos) {
        list = postos;
    }

    @NonNull
    @Override
    public LineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new LineHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.body_destaques, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LineHolder holder, int position) {

        holder.nomeFantasia.setText(list.get(position).getNomeFantasia());
        holder.logradouro.setText(list.get(position).getLogradouro());
        holder.bairro.setText(list.get(position).getBairro());
        holder.numero.setText(list.get(position).getNumero());
        holder.preco.setText(list.get(position).getPreco());
        holder.atualizado.setText(list.get(position).getAtualizado());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}