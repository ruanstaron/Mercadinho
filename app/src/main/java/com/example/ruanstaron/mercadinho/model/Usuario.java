package com.example.ruanstaron.mercadinho.model;

/**
 * Created by pucci on 09/01/2018.
 */

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private long  cidadeId;
    private String cod_msg;

    public Usuario(String email, String senha, String nome, long cidadeId){
        setEmail(email);
        setSenha(senha);
        setNome(nome);
        setCidadeId(cidadeId);
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

    public long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(long cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getCod_msg() {
        return cod_msg;
    }

    public void setCod_msg(String cod_msg) {
        this.cod_msg = cod_msg;
    }
}
