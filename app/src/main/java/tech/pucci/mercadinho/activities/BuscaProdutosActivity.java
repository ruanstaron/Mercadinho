package tech.pucci.mercadinho.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import tech.pucci.mercadinho.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import tech.pucci.mercadinho.adapters.BuscaProdutoAdapter;
import tech.pucci.mercadinho.model.ProdutoModel;

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
            List<ProdutoModel> produtoModels = buscaProdutos();
            BuscaProdutoAdapter adapter = new BuscaProdutoAdapter(produtoModels, this);
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

    private List<ProdutoModel> buscaProdutos() throws ParseException {

        List<ProdutoModel> produtoModels = new ArrayList<ProdutoModel>();
        ProdutoModel e = new ProdutoModel("produto1", "mercado1", 1, "10-02-2018" );
        produtoModels.add(e);

        e = new ProdutoModel("produto2", "mercado2", 2, "10-02-2018" );
        produtoModels.add(e);

        e = new ProdutoModel("produto3", "mercado3", 3, "10-02-2018" );
        produtoModels.add(e);

        e = new ProdutoModel("produto4", "mercado4", 4, "10-02-2018" );
        produtoModels.add(e);

        e = new ProdutoModel("produto5", "mercado6", 5, "10-02-2018" );
        produtoModels.add(e);
        return produtoModels;
    }
}