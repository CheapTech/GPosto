package br.com.senacrs.gposto.Utilities;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.senacrs.gposto.R;

public class HolderTopPostos extends RecyclerView.ViewHolder {

    public ImageView bandeira;
    public TextView nomeFantasia;
    public TextView preco;
    public TextView logradouro;
    public TextView bairro;
    public TextView atualizado;


    public HolderTopPostos(View itemView, final OnItemViewHolderClick itemClicked) {
        super(itemView);

        bandeira = itemView.findViewById(R.id.bandeira);
        nomeFantasia = itemView.findViewById(R.id.nomeFantasia);
        preco = itemView.findViewById(R.id.preco);
        logradouro = itemView.findViewById(R.id.logradouroFull);
        bairro = itemView.findViewById(R.id.bairro);
        atualizado = itemView.findViewById(R.id.atualizado);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked.onItemClicked(getAdapterPosition());
            }
        });

    }
}