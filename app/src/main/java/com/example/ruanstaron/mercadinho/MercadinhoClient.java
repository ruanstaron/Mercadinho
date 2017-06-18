package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.BdDefinitivo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MercadinhoClient {

    String  GET_ENDPOINT = "/index.php/getprodutos";
    String  POST_ENDPOINT = "/index.php/addprodutos";
    String  GET_ENDPOINT_TESTE = "/MercadinhoPHP/public/getprodutos";
    String  POST_ENDPOINT_TESTE = "/MercadinhoPHP/public/addproduto";

    @GET(GET_ENDPOINT_TESTE)
    Call<List<BdDefinitivo>> BdDefinitivo();

    @POST(POST_ENDPOINT_TESTE)
    Call<BdDefinitivo[]> AddBdDefinitivo(@Body BdDefinitivo bdDefinitivo[]);

}
