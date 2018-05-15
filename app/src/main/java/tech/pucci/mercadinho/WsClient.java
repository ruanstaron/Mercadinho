package tech.pucci.mercadinho;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tech.pucci.mercadinho.db.Cidade;
import tech.pucci.mercadinho.db.Mercado;
import tech.pucci.mercadinho.db.Produto;
import tech.pucci.mercadinho.model.Usuario;

/**
 * Created by pucci on 09/01/2018.
 */

public interface WsClient {

    // Endpoints
    String EDP_LOGIN    = "login";
    String EDP_SIGNUP   = "signup";
    String EDP_MERCADOS = "getmercados";
    String EDP_PRODUTOS = "getprodutos";

    // Misc
    String SERVER_URL    = "https://mercadinho.herokuapp.com/WS/index.php/";
    String AUTHORIZATION = "Authorization";
    String BASIC_AUTH    = "Basic ";

    @POST(EDP_LOGIN)
    Call<List<Usuario>> usuarioLogin(@Header(AUTHORIZATION) String authHeader);

    @POST(EDP_SIGNUP)
    Call<RetornoWS> usuarioSignup(@Body Usuario usuario);

    @POST(EDP_MERCADOS)
    Call<List<Mercado>> getMercados(@Body Cidade cidade);

    @GET(EDP_PRODUTOS)
    Call<List<Produto>> getProdutos();
}
