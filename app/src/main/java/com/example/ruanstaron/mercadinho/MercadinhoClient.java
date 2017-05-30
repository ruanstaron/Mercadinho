package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.Produtos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MercadinhoClient {
    @GET("/Mercadinhophp/public/getprodutos")
    Call<List<Produtos>> Produto();
}
