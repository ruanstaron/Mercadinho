package com.example.ruanstaron.mercadinho.model;

/**
 * Created by pucci on 09/01/2018.
 */

public class Usuario {

    private String nome;
    private String email;
    private String senha;

    public Usuario(String email, String senha, String nome){
        setEmail(email);
        setSenha(senha);
        setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
