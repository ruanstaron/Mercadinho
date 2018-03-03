package com.example.ruanstaron.mercadinho.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

            SharedPreferences prefs = getSharedPreferences(WebService.PREFS_USUARIO, MODE_PRIVATE);

            if(prefs.contains(WebService.PREFS_USUARIO_USERNAME)){
                webService.realizarLogin(prefs.getString(WebService.PREFS_USUARIO_USERNAME, null),
                        prefs.getString(WebService.PREFS_USUARIO_SENHA, null));
            }
            else{
                Intent itLogin = new Intent(this, LoginActivity.class);
                startActivity(itLogin);

                finish();
            }
        }
    }
}
