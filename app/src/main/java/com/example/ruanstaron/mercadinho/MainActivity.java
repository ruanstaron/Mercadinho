package com.example.ruanstaron.mercadinho;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.db.BdDefinitivo;
import com.example.ruanstaron.mercadinho.db.BdDefinitivoDao;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String WS_SERVER = "http://mercadinho.96.lt/";
    private final String WS_SERVER_TESTE = "http://10.0.2.2/";


    //ArrayList<BdDefinitivo> bdd = new ArrayList<>();
    //BdDefinitivo hue = new BdDefinitivo();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        //List<BdDefinitivo> lBdDefinitivo;
        // bddTeste.setCod_barras("7896541230");
        // bddTeste.setProduto("Xinxila");

    }

    @Override
    protected void onStart() {
        super.onStart();
       // bddTeste.setCod_barras("7896541230");
       // bddTeste.setProduto("Xinxila");
       // bddTeste.setId(null);
        //getProdutosWS();
        BdDefinitivo bddTeste[] = new BdDefinitivo[2];
        bddTeste[0] = new BdDefinitivo();
        bddTeste[0].setProduto("xinxila");
        bddTeste[0].setCod_barras("4554");
        bddTeste[1] = new BdDefinitivo();
        bddTeste[1].setProduto("xinxila2");
        bddTeste[1].setCod_barras("254127478");
        postProdutoWS(bddTeste);
    }

    public void startScan(View view) {
        Intent scan = new Intent(this, Scan.class);
        startActivity(scan);
    }

    public void startBanco(View view) {
        Intent banco = new Intent(this, Banco.class);
        startActivity(banco);
    }

    public void getProdutosWS(){
        Retrofit.Builder builder  = new Retrofit.Builder()
                .baseUrl(WS_SERVER)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MercadinhoClient client = retrofit.create(MercadinhoClient.class);
        Call<List<BdDefinitivo>> call =  client.BdDefinitivo();

        call.enqueue(new Callback<List<BdDefinitivo>>() {
            @Override
            public void onResponse(Call<List<BdDefinitivo>> call, Response<List<BdDefinitivo>> response) {
                Database db = new DaoMaster.DevOpenHelper(getApplicationContext(), "mercadinho-db").getWritableDb();
                List<BdDefinitivo> lBdDefinitivo = response.body();
                DaoSession daoSession = new DaoMaster(db).newSession();
                BdDefinitivoDao daoBdDefinitivo = daoSession.getBdDefinitivoDao();

                for(int i = 0; i < lBdDefinitivo.size(); i++){
                    daoBdDefinitivo.insertOrReplace(lBdDefinitivo.get(i));
                    Log.i("Mercadinho", "Produto Inserido!");
                }
                 //TODO: Implementacao POST
                 //TODO: Teste de Update no banco interno com o get
            }

            @Override
            public void onFailure(Call<List<BdDefinitivo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postProdutoWS(BdDefinitivo bdDefinitivo[]){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okclient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(WS_SERVER_TESTE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okclient);

        Retrofit retrofit = builder.build();
        MercadinhoClient client = retrofit.create(MercadinhoClient.class);

        Call<BdDefinitivo[]> call = client.AddBdDefinitivo(bdDefinitivo);
        call.enqueue(new Callback<BdDefinitivo[]>() {
            @Override
            public void onResponse(Call<BdDefinitivo[]> call, Response<BdDefinitivo[]> response) {
                Toast.makeText(MainActivity.this, "poi", Toast.LENGTH_SHORT).show();
                Log.i("Mercadinho", "adsrfwesews");
                okclient.toString();
            }

            @Override
            public void onFailure(Call<BdDefinitivo[]> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro Inserindo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}