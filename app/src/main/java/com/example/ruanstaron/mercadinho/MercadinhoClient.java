package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.BdDefinitivo;
import com.example.ruanstaron.mercadinho.db.Produtos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MercadinhoClient {

    String ENDPOINT = "/Mercadinhophp/public/getprodutos";

    @GET(ENDPOINT)
    Call<List<BdDefinitivo>> BdDefinitivo();

    @POST(ENDPOINT)
    Call<BdDefinitivo> AddBdDefinitivo();


}
