package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.tasks.LoginTask;
import com.example.ruanstaron.mercadinho.db.AtributosDao;
import com.example.ruanstaron.mercadinho.db.Cidade;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Mercado;
import com.example.ruanstaron.mercadinho.db.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pucci on 10/09/2017.
 */

public class WebService {


    public static final String PREFS_USUARIO            = "usuario";
    public static final String PREFS_USUARIO_USERNAME   = "username";
    public static final String PREFS_USUARIO_SENHA      = "senha";

    private final String WS_URL     = "http://mercadinho.96.lt/WS/index.php/";
    private final String POST_URL   = "addprodutos";
    private final String GET_URL    = "getprodutos";
    private final String POST       = "POST";
    private final String GET        = "GET";
    private final String UTF_8      = "UTF-8";

    private final String PRODUTO            = "produto";
    private final String PRODUTO_COD_BARRAS = "cod_barras";
    private final String PRODUTO_DESCRICAO  = "descricao";

    private final int TIMEOUT = 3000;


    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private Banco banco;
    private Context context;

    public WebService(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        helper = new DaoMaster.DevOpenHelper(context, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);

        this.context = context;

        AtributosDao.insereDados(helper.getReadableDb());
    }

    public boolean verificaConexao(ConnectivityManager conectivtyManager) {
        return (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isConnected()
                && conectivtyManager.getActiveNetworkInfo().isAvailable());
    }

    //TODO: ARRUMAR ASSIM QUE OS PRODUTOS PUDEREM SER CADASTRADOS
    private void postProdutos(String requestJson){

        try {
            URL url = new URL(WS_URL + POST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(requestJson.length()));
            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(requestJson.getBytes(UTF_8));
            stream.flush();
            stream.close();
            connection.connect();
            connection.getInputStream().toString();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getProdutos(){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(WsClient.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        final WsClient wsclient = retrofit.create(WsClient.class);

        Call<List<Produto>> call = wsclient.getProdutos();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if(response.isSuccessful()){
                    List<Produto> lProdutos = response.body();

                    banco.gravaProdutos(lProdutos);
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO: TEM QUE FAZER ALGO PARECIDO PARA FRENTE, VER ISSO
    private String montaJson(List<Produto> produtos){
        JSONObject prontoEnvio = new JSONObject();
        JSONArray jsonProdutos = new JSONArray();

        for(Produto prod : produtos){
            JSONObject produto = new JSONObject();
            try {
                produto.put(PRODUTO_COD_BARRAS, prod.getCod_barras());
                produto.put(PRODUTO_DESCRICAO, banco.getProdutoDescricao(prod.getCod_barras()));
                jsonProdutos.put(produto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
                prontoEnvio.put(PRODUTO,jsonProdutos);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return prontoEnvio.toString();
    }

    public void sincronizar(){
        //postProdutos(montaJson(banco.carregaProdutosManuais())); TODO: Ver se esta funcionando no server e ajustar
        /*banco.setaProdutosEnviados(); TODO: Comitei senão não conseguia gravar nenhum produto no banco.
        banco.excluiProdutos();
        getProdutos();*/
    }

    public void getMercados(Cidade cidade){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(WsClient.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        final WsClient wsclient = retrofit.create(WsClient.class);

        Call<List<Mercado>> call = wsclient.getMercados(cidade);

        call.enqueue(new Callback<List<Mercado>>() {
            @Override
            public void onResponse(Call<List<Mercado>> call, Response<List<Mercado>> response) {
                if(response.isSuccessful()){
                    List<Mercado> lMercados = response.body();

                    if(lMercados != null)
                        banco.insertMercados(lMercados);
                }
            }

            @Override
            public void onFailure(Call<List<Mercado>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void realizarLogin(String usuario, String senha){
        LoginTask loginTask = new LoginTask(context);

        loginTask.execute(usuario, senha);
    }
}

