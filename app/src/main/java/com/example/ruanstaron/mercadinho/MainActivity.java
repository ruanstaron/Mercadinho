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
import com.example.ruanstaron.mercadinho.db.Produtos;
import com.example.ruanstaron.mercadinho.db.ProdutosDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String WS_SERVER = "http://10.0.2.2/"; // 10.0.2.2 -> Como o Android entende o endereco lookup do pc rodando a AVD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getProdutosWS();
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
}