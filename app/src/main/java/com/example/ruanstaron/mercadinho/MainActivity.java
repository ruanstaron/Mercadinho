package com.example.ruanstaron.mercadinho;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.ruanstaron.mercadinho.db.BdDefinitivo;
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

public class MainActivity extends AppCompatActivity {

    public TextView json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        json = (TextView) findViewById(R.id.json);
        //Para poder conectar na net
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //postProdutos();
        //getProdutos();
    }

    public void startScan(View view) {
        Intent scan = new Intent(this, Scan.class);
        startActivity(scan);
    }

    public void postProdutos(String requestJson){
        //exemplo de como gerar o Json
        /*JSONObject produto = new JSONObject();
        JSONObject produto2 = new JSONObject();
        JSONObject prontoEnvio = new JSONObject();
        JSONArray produtos = new JSONArray();

        try {
            produto.put("cod_barras", "20658");
            produto.put("descricao", "teste_json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        produtos.put(produto);

        try {
            produto2.put("cod_barras", "2424");
            produto2.put("descricao", "joao_viadao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        produtos.put(produto2);
        try {
            prontoEnvio.put("produto",produtos);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json.setText(prontoEnvio.toString());
        //-------------------------------------//

        String requestJson = prontoEnvio.toString();*/

        try {
            URL url = new URL("http://mercadinho.96.lt/index.php/addprodutos");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(requestJson.length()));
            DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
            stream.write(requestJson.getBytes("UTF-8"));
            stream.flush();
            stream.close();
            connection.connect();
            //String responseJson = connection.getInputStream().toString();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Vai retornar um Array de BdDefinitivo
    public ArrayList<BdDefinitivo> getProdutos(){
        ArrayList<BdDefinitivo> bdd = new ArrayList<>();
        URL obj;
        String line;
        Gson gson = new Gson();
        try {
            obj = new URL("http://mercadinho.96.lt/index.php/getprodutos");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            JSONArray jsonArr = new JSONArray(response.toString());
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                BdDefinitivo bdDefinitivo = gson.fromJson(jsonObj.toString(), BdDefinitivo.class);
                bdd.add(bdDefinitivo);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return bdd;
    }
}