package br.com.senacrs.gposto.LibClass;

import java.io.Serializable;

public class Combustivel implements Serializable {
    private int id;
    private String descricao;
    private float preco;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
