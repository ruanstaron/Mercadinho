package com.example.ruanstaron.mercadinho.model;

/**
 * Created by Ruan on 16/02/2018.
 */

public class Compra {
    private String produto;
    private int quantidade;
    private float valorUnitario;
    private boolean comprado;

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public Compra(String produto, int quantidade, float valorUnitario, boolean comprado) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.comprado = comprado;
    }
}
