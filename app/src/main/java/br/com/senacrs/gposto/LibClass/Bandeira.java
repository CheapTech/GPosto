package br.com.senacrs.gposto.LibClass;

import java.io.Serializable;

public class Bandeira implements Serializable {
    private int id;
    private String nome;
    private String logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return nome;
    }

    public void setMarca(String nome) {
        this.nome = nome;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return getMarca();
    }
}
