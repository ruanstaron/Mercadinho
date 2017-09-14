package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.widget.Toast;


import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Produtos;
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

    public  boolean verificaConexao(ConnectivityManager conectivtyManager) {
        return (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isConnected()
                && conectivtyManager.getActiveNetworkInfo().isAvailable());
    }

    public void postProdutos(String requestJson){

        try {
            URL url = new URL("http://mercadinho.96.lt/WS/index.php/addprodutos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(3000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(requestJson.length()));
            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(requestJson.getBytes("UTF-8"));
            stream.flush();
            stream.close();
            connection.connect();
            connection.getInputStream().toString();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Produtos> getProdutos(){
        ArrayList<Produtos> produtos_array = new ArrayList<>();
        URL obj;
        String line;
        Gson gson = new Gson();
        try {
            obj = new URL("http://mercadinho.96.lt/WS/index.php/getprodutos");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setConnectTimeout(3000);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            JSONArray jsonArr = new JSONArray(response.toString());
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                Produtos produto = gson.fromJson(jsonObj.toString(), Produtos.class);
                produto.setManual(false);
                produtos_array.add(produto);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return produtos_array;
    }

    public String montaJson(List<Produtos> produtos){
        JSONObject prontoEnvio = new JSONObject();
        JSONArray jsonProdutos = new JSONArray();

        for(Produtos prod : produtos){
            JSONObject produto = new JSONObject();
            try {
                produto.put("cod_barras",prod.getCod_barras());
                produto.put("descricao",banco.getProdutoDescricao(prod.getCod_barras()));
                jsonProdutos.put(produto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            prontoEnvio.put("produto",jsonProdutos);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return prontoEnvio.toString();
    }
}
