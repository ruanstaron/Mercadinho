package com.example.ruanstaron.mercadinho.model;



/**
 * Created by Ruan on 10/02/2018.
 */

public class Produto {
    private String produto;
    private String mercado;
    private float valor;
    private String data;

    public Produto(String produto, String mercado, float valor, String data) {
        this.produto = produto;
        this.mercado = mercado;
        this.valor = valor;
        this.data = data;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
