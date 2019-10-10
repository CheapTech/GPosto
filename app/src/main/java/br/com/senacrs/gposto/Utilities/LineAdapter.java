package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.senacrs.gposto.LibClass.TopPostos;
import br.com.senacrs.gposto.R;

public class LineAdapter extends RecyclerView.Adapter<LineHolder> implements OnItemViewHolderClick {

    private List<TopPostos> list;
    private Context context;

    public LineAdapter(List postos, Context context) {

        this.list = postos;
        this.context = context;
    }

    @NonNull
    @Override
    public LineHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_destaque, viewGroup, false);
        LineHolder lineHolder = new LineHolder(view, this);

        return lineHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LineHolder holder, int position) {

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
        //TODO ação do botão - chamar o perfil do posto.
        Toast.makeText(context, list.get(itemPosition).getPreco(), Toast.LENGTH_LONG).show();
    }
}