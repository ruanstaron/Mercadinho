package com.example.ruanstaron.mercadinho;

/**
 * Created by Ruan Staron on 27/02/2017.
 */

public class Produto {
    private String valor;
    private String quantidade;
    private String id;
    private String cod_barras;
    private String produto;
    private String votos;

    public Produto(String cod_barras, String produto){
        this.cod_barras = cod_barras;
        this.produto = produto;
    }

    public Produto(String descricao, String valor, String quantidade){
        this.produto = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getId(){
        return id;
    }

    public String getCod_barras(){
        return cod_barras;
    }

    public String getDescricao() {
        return produto;
    }

    public String getVotos(){
        return votos;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String toString(){
        return "Produto: "+produto+", Quantidade: "+quantidade+", Valor: "+valor;
    }
}
