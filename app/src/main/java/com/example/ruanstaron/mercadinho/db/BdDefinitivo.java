package com.example.ruanstaron.mercadinho.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "BD_DEFINITIVO".
 */
@Entity
public class BdDefinitivo {

    @Id
    private Long id;
    private String cod_barras;
    private String produto;

    @Generated
    public BdDefinitivo() {
    }

    public BdDefinitivo(Long id) {
        this.id = id;
    }

    @Generated
    public BdDefinitivo(Long id, String cod_barras, String produto) {
        this.id = id;
        this.cod_barras = cod_barras;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

}