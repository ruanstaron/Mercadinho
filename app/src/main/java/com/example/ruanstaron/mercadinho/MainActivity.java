package com.example.ruanstaron.mercadinho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public Button fazerCompras;
    public Button configuracoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fazerCompras = (Button) findViewById(R.id.fazerCompras);
        configuracoes = (Button) findViewById(R.id.configuracoes);

        fazerCompras.setOnClickListener(this);
        configuracoes.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==R.id.fazerCompras){
            Intent intentLista = new Intent(this, ListaActivity.class);
            startActivity(intentLista);
        }
        if(v.getId()==R.id.configuracoes){
            //TODO: CONFIGURAÇÕES
        }
    }
}