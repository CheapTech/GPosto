package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.senacrs.gposto.GUI.DestaquesActivity;
import br.com.senacrs.gposto.LibClass.OnItemViewHolderClick;
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
     /*   return new LineHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_holder_destaque, viewGroup, false)
        );*/
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_destaque,viewGroup,false);
        LineHolder lineHolder = new LineHolder(view,this);

        return lineHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LineHolder holder, int position) {

        //holder.nomeFantasia.setText(list.get(position).getNomeFantasia());
        //holder.logradouro.setText(list.get(position).getLogradouro());
        //holder.bairro.setText(list.get(position).getBairro());
        //holder.numero.setText(list.get(position).getNumero());
        holder.preco.setText(list.get(position).getPreco());
        //holder.atualizado.setText(list.get(position).getAtualizado());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemClicked(int itemPosition) {
        //TODO ação do botão - chamar o perfil do posto.
        Toast.makeText(context,list.get(itemPosition).getPreco(),Toast.LENGTH_LONG).show();
    }
}