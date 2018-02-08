package com.example.ruanstaron.mercadinho.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ruanstaron.mercadinho.WebService;

/**
 * Created by pucci on 10/09/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebService webService = new WebService(this);
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(webService.verificaConexao(conectivtyManager)){
            webService.sincronizar();

            Intent itLogin = new Intent(this, LoginActivity.class);
            startActivity(itLogin);

            //Intent intent = new Intent(this, ListaActivity.class);
            //startActivity(intent);
            finish();
        }
    }
}
