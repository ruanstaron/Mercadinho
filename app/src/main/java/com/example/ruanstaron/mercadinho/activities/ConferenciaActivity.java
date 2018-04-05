package com.example.ruanstaron.mercadinho.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.Cupom;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.adapters.ConferenciaAdapter;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtosDao;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConferenciaActivity extends AppCompatActivity {

    private Button   btnConferir;
    private ListView lvResultados;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private ListaDao listaDao;
    private Banco banco;

    private long idLista;

    private List<Lista_de_produtos> comprasSelecionadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferencia);

        idLista =  getIntent().getLongExtra("id_lista", -1);


        inicializarComponentes();
        inicializarBanco();

        comprasSelecionadas = banco.carregaCompras((int)idLista);
    }

    private void inicializarComponentes(){
        lvResultados = ((ListView) findViewById(R.id.lvResultados));

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

    private void atualizarListViewCompras(){
        lvResultados.setVisibility(View.VISIBLE);

        List<Lista_de_produtos> compras = new Banco(session).carregaCompras((int) idLista);
        lvResultados.setAdapter(new ConferenciaAdapter(this, compras, compras));

       /* if(lvCompras.getAdapter().isEmpty())
             tvListaVazia.setVisibility(View.VISIBLE);
        else
            tvListaVazia.setVisibility(View.INVISIBLE);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        try {
            if (scanningResult != null && scanningResult.getContents() == null)
                return;

            URL urlSEFAZ  = new URL(scanningResult.getContents());

            // Variaveis temporarias para evitar o erro decorrente da comparação de longs. TODO: PESQUISAR E TESTAR
            String temp1;
            String temp2;

            Cupom cupom = new Cupom(master);

            List<Lista_de_produtos> compras = cupom.getProdutosDoCupomFiscal(String.valueOf(urlSEFAZ));
            compras = filtrarCompras(compras);

            for (Lista_de_produtos compra : compras) {
                for(Lista_de_produtos compraSelecionada: comprasSelecionadas){
                    compraSelecionada.setSituacaoId((long) Lista_de_produtos.DIVERGENCIA);

                    temp1 = String.valueOf(compra.getCod_barras());
                    temp2 = String.valueOf(compraSelecionada.getCod_barras());

                    if(temp1.equals(temp2)){
                        if(Float.compare(compra.getValor(), compraSelecionada.getValor()) == 0)
                            compraSelecionada.setSituacaoId((long)Lista_de_produtos.OK);

                        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
                        comprasDao.update(compraSelecionada);

                        //comprasSelecionadas.remove(compraSelecionada);
                        break;
                    }
                }
            }

            atualizarListViewCompras();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro no escaneamento", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Lista_de_produtos> filtrarCompras(List<Lista_de_produtos> compras){
        Set<Lista_de_produtos> comprasFiltradas = new HashSet<>(compras);

        return new ArrayList<>(comprasFiltradas);
    }
}
