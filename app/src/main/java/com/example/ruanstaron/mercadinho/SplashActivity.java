package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;

/**
 * Created by pucci on 10/09/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private Banco banco;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);

        WebService webService = new WebService(this);
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(webService.verificaConexao(conectivtyManager)){
            webService.postProdutos(webService.montaJson(banco.carregaProdutosManuais()));
            banco.setaProdutosEnviados();

            banco.limpaBanco();
            banco.gravaBanco(webService.getProdutos());

            Intent intent = new Intent(this, ListaActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
