package com.example.ruanstaron.mercadinho;

/**
 * Created by Ruan Staron on 27/02/2017.
 */

public class Produto {
    private String descricao;
    private String valor;
    private String quantidade;

    public Produto(String descricao, String valor, String quantidade){
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String toString(){
        return "Produto: "+descricao+", Quantidade: "+quantidade+", Valor: "+valor;
    }
}
