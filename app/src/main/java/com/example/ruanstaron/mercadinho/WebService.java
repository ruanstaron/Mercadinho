package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;

import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Produto;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pucci on 10/09/2017.
 */

public class WebService {

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


    DaoMaster.DevOpenHelper helper;
    DaoMaster master;
    DaoSession session;
    Banco banco;

    public WebService(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        helper = new DaoMaster.DevOpenHelper(context, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
    }

    public boolean verificaConexao(ConnectivityManager conectivtyManager) {
        return (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isConnected()
                && conectivtyManager.getActiveNetworkInfo().isAvailable());
    }

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

    private ArrayList<Produto> getProdutos(){
        ArrayList<Produto> produtos_array = new ArrayList<>();
        URL obj;
        String line;
        Gson gson = new Gson();
        try {
            obj = new URL(WS_URL + GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(GET);
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", UTF_8);
            con.setConnectTimeout(TIMEOUT);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            JSONArray jsonArr = new JSONArray(response.toString());
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Produto produto = gson.fromJson(jsonObj.toString(), Produto.class);
                produto.setRecente(false);
                produtos_array.add(produto);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return produtos_array;
    }

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
        postProdutos(montaJson(banco.carregaProdutosManuais()));
        banco.setaProdutosEnviados();

        banco.limpaBanco();
        banco.gravaBanco(getProdutos());
    }
}
