package com.example.ruanstaron.mercadinho.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.Cupom;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.List;

public class ConferenciaActivity extends AppCompatActivity {

    private Button btnConferir;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private ListaDao listaDao;
    private Banco banco;

    private List<Lista_de_produtos> comprasSelecionadas;
    private String  urlSEFAZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferencia);

        int idLista =  getIntent().getIntExtra("id_lista", -1);


        inicializarComponentes();
        inicializarBanco();

        comprasSelecionadas = banco.carregaCompras(idLista);
    }

    private void inicializarComponentes(){
        btnConferir = ((Button) findViewById(R.id.btnConferir));
        btnConferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(ConferenciaActivity.this);
                scanIntegrator.setTitle("Escanear Cupom Fiscal");
                scanIntegrator.setMessage("Escaneie o cupom fiscal da compra");
                scanIntegrator.initiateScan();
            }
        });
    }

    private void inicializarBanco(){
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(scanningResult != null){
            urlSEFAZ = scanningResult.getContents();

            Cupom cupom = new Cupom(master);

            try {
                List<Lista_de_produtos> compras = cupom.getProdutosDoCupomFiscal(urlSEFAZ);

                /*for (Lista_de_produtos compra : compras) {
                    for(Lista_de_produtos compraSelecionada: comprasSelecionadas){
                        if(compra){}
                    }
                }*/

                for(Lista_de_produtos compra: compras){
                    Log.i("PRODUTO", String.valueOf(compra.getCod_barras()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
