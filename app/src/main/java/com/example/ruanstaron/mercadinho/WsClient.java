package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by pucci on 09/01/2018.
 */

public interface WsClient {

    // Endpoints
    String EDP_LOGIN  = "login";
    String EDP_SIGNUP = "signup";

    // Misc
    String SERVER_URL    = "http://mercadinho.96.lt/WS/index.php/";
    String AUTHORIZATION = "Authorization";
    String BASIC_AUTH    = "Basic ";

    @POST(EDP_LOGIN)
    Call<Boolean> usuarioLogin(@Header(AUTHORIZATION) String authHeader);

    @POST(EDP_SIGNUP)
    Call<Integer> usuarioSignup(@Body Usuario usuario);
}
