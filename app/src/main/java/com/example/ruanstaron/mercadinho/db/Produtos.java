package com.example.ruanstaron.mercadinho.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "PRODUTOS".
 */
@Entity
public class Produtos {

    @Id
    private Long cod_barras;
    private String descricao;
    private Boolean Manual;

    @Generated
    public Produtos() {
    }

    public Produtos(Long cod_barras) {
        this.cod_barras = cod_barras;
    }

    @Generated
    public Produtos(Long cod_barras, String descricao, Boolean Manual) {
        this.cod_barras = cod_barras;
        this.descricao = descricao;
        this.Manual = Manual;
    }

    public Long getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(Long cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getManual() {
        return Manual;
    }

    public void setManual(Boolean Manual) {
        this.Manual = Manual;
    }

}
