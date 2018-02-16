package com.example.ruanstaron.mercadinho.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.model.Produto;
import java.util.List;

/**
 * Created by Ruan on 10/02/2018.
 */

public class BuscaProdutoAdapter extends BaseAdapter {
    private final Activity act;
    private final List<Produto> produtos;

    public BuscaProdutoAdapter(List<Produto> produtos, Activity act) {
        this.produtos = produtos;
        this.act = act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.list_item_busca_produtos, parent, false);
        Produto produto = produtos.get(position);
        TextView NomeProduto = (TextView) view.findViewById(R.id.tvNomeProduto);
        TextView NomeMercado = (TextView) view.findViewById(R.id.tvNomeMercado);
        TextView ValorProduto = (TextView) view.findViewById(R.id.tvValorProduto);
        TextView DataCompraProduto = (TextView) view.findViewById(R.id.tvDataCompraProduto);
        NomeProduto.setText(produto.getProduto());
        NomeMercado.setText(produto.getMercado());
        ValorProduto.setText(String.valueOf(produto.getValor()));
        DataCompraProduto.setText(produto.getData());
        if(position % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#ffaf18"));
        }
        return view;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
