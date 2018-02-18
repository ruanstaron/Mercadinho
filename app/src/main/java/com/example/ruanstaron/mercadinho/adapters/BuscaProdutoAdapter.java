package com.example.ruanstaron.mercadinho.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.model.ProdutoModel;
import java.util.List;

/**
 * Created by Ruan on 10/02/2018.
 */

public class BuscaProdutoAdapter extends BaseAdapter {
    private final Activity act;
    private final List<ProdutoModel> produtoModels;

    public BuscaProdutoAdapter(List<ProdutoModel> produtoModels, Activity act) {
        this.produtoModels = produtoModels;
        this.act = act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.list_item_busca_produtos, parent, false);
        ProdutoModel produtoModel = produtoModels.get(position);
        TextView NomeProduto = (TextView) view.findViewById(R.id.tvNomeProduto);
        TextView NomeMercado = (TextView) view.findViewById(R.id.tvNomeMercado);
        TextView ValorProduto = (TextView) view.findViewById(R.id.tvValorProduto);
        TextView DataCompraProduto = (TextView) view.findViewById(R.id.tvDataCompraProduto);
        NomeProduto.setText(produtoModel.getProduto());
        NomeMercado.setText(produtoModel.getMercado());
        ValorProduto.setText(String.valueOf(produtoModel.getValor()));
        DataCompraProduto.setText(produtoModel.getData());
        if(position % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#ffaf18"));
        }
        return view;
    }

    @Override
    public int getCount() {
        return produtoModels.size();
    }

    @Override
    public Object getItem(int position) {
        return produtoModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
