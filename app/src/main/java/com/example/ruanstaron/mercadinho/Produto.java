package com.example.ruanstaron.mercadinho;

/**
 * Created by Ruan Staron on 27/02/2017.
 */

public class Produto {
    private String id;
    private String cod_barras;
    private String produto;
    private String votos;

    public Produto(String s, String s1, String s2) {
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
}
