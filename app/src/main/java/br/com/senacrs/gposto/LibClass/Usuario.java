package br.com.senacrs.gposto.LibClass;

import java.io.Serializable;

public class Usuario implements Serializable {

    private Integer id;
    private String user;
    private String senha;
    private String email;

    public Usuario(String user, String senha, String email) {
        this.user = user;
        this.senha = senha;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
