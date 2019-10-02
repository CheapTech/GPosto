package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.senacrs.gposto.GUI.PerfilPostosActivity;
import br.com.senacrs.gposto.LibClass.Postos;
import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class AdapterTopPostos extends RecyclerView.Adapter<HolderTopPostos> implements OnItemViewHolderClick, Filterable {

    private List<TopPostos> searchList;
    private List<TopPostos> list;
    private Context context;

    public AdapterTopPostos(List postos, Context context) {
        this.list = postos;
        searchList = new ArrayList<>(postos);
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

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<TopPostos> filteredList = new ArrayList<>();

           if (constraint == null || constraint.length() == 0){
               filteredList.addAll(searchList);
           }else {
               String filterPattern = constraint.toString().toLowerCase().trim();

               for (TopPostos postos : searchList){
                   if(postos.getNomeFantasia().toLowerCase().contains(filterPattern)){
                       filteredList.add(postos);
                   }
               }
           }
           FilterResults results = new FilterResults();
           results.values = filteredList;

           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}