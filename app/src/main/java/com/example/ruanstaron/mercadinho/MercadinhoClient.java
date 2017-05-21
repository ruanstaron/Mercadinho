package com.example.ruanstaron.mercadinho;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pucci on 07/05/2017.
 */

public interface MercadinhoClient {
    @GET("/Mercadinhophp/public/getprodutos")
    Call<List<Produto>> Produto();
}
