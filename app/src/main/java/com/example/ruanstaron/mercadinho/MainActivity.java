package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.db.Compras;
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

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public Button gettProdutos;
    public Button posttProdutos;
    public Button fazerCompras;
    public Button configuracoes;
    DaoMaster.DevOpenHelper helper;
    DaoMaster master;
    DaoSession session;
    Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //instancia os botoões
        gettProdutos = (Button) findViewById(R.id.getProdutos);
        posttProdutos = (Button) findViewById(R.id.postProdutos);
        fazerCompras = (Button) findViewById(R.id.fazerCompras);
        configuracoes = (Button) findViewById(R.id.configuracoes);

        //seta como onlicklistener
        gettProdutos.setOnClickListener(this);
        posttProdutos.setOnClickListener(this);
        fazerCompras.setOnClickListener(this);
        configuracoes.setOnClickListener(this);

        //Para poder conectar na net
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //instancia o greendao para poder limpar e carregar o banco definitivo
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
    }

    public void onClick(View v){
        if(v.getId()==R.id.getProdutos){
            if (verificaConexao()){
                ArrayList<Produtos> produtos_array;
                produtos_array = getProdutos();
                banco.limpaBanco();
                banco.gravaBanco(produtos_array);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Não há conexão com a internet", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if(v.getId()==R.id.postProdutos){
            List<Compras> produtos = banco.carregaProdutos();
            String json = montaJson(produtos);
            postProdutos(json);
        }
        if(v.getId()==R.id.fazerCompras){
            Intent scan = new Intent(this, Scan.class);
            startActivity(scan);
        }
        if(v.getId()==R.id.configuracoes){
            Intent itListaActivity = new Intent(this, ListaActivity.class);
            startActivity(itListaActivity);
        }
    }

    public void postProdutos(String requestJson){

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

    public ArrayList<Produtos> getProdutos(){
        ArrayList<Produtos> produtos_array = new ArrayList<>();
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
                Produtos produto = gson.fromJson(jsonObj.toString(), Produtos.class);
                produtos_array.add(produto);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return produtos_array;
    }

    public String montaJson(List<Compras> compras){
        JSONObject prontoEnvio = new JSONObject();
        JSONArray jsonProdutos = new JSONArray();

        for(Compras compr : compras){
            JSONObject produto = new JSONObject();
            try {
                produto.put("cod_barras",compr.getCod_barras());
                produto.put("descricao",banco.getDescricao(compr.getCod_barras()));
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

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}