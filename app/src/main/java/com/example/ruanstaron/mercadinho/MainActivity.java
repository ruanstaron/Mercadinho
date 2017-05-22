package com.example.ruanstaron.mercadinho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> produtos;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        produtos = new ArrayList<String>();
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
                .baseUrl("http://10.0.2.2/") // 10.0.2.2 -> Como o Android entende o endereco lookup do pc rodando a AVD
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        MercadinhoClient client = retrofit.create(MercadinhoClient.class);
        Call<List<Produto>> call =  client.Produto();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
               // List<Produto> produtos  = response.body();

              //  listaProdutos.setAdapter(new ProdutoAdapter(MainActivity.this, produtos));
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}