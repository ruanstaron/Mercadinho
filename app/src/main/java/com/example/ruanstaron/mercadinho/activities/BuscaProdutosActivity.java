package com.example.ruanstaron.mercadinho.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.adapters.BuscaProdutoAdapter;
import com.example.ruanstaron.mercadinho.model.Produto;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruan on 10/02/2018.
 */

public class BuscaProdutosActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_de_produto);
        ListView lista = (ListView) findViewById(R.id.lvBuscaProdutos);

        try {
            List<Produto> produtos = buscaProdutos();
            BuscaProdutoAdapter adapter = new BuscaProdutoAdapter(produtos, this);
            lista.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bOk:
                break;
        }
    }

    private List<Produto> buscaProdutos() throws ParseException {

        List<Produto> produtos = new ArrayList<Produto>();
        Produto e = new Produto("produto1", "mercado1", 1, "10-02-2018" );
        produtos.add(e);

        e = new Produto("produto2", "mercado2", 2, "10-02-2018" );
        produtos.add(e);

        e = new Produto("produto3", "mercado3", 3, "10-02-2018" );
        produtos.add(e);

        e = new Produto("produto4", "mercado4", 4, "10-02-2018" );
        produtos.add(e);

        e = new Produto("produto5", "mercado6", 5, "10-02-2018" );
        produtos.add(e);
        return produtos;
    }
}