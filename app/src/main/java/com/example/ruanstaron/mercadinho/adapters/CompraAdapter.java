package com.example.ruanstaron.mercadinho.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;

import java.util.List;

/**
 * Created by pucci on 07/08/2017.
 */

public class CompraAdapter extends BaseAdapter {

    private final Activity act;
    private final List<Lista_de_produtos> lista_produtos;
    private DaoSession session;

    public CompraAdapter(List<Lista_de_produtos> lista_produtos, Activity act, DaoSession session) {
        this.lista_produtos = lista_produtos;
        this.act = act;
        this.session = session;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.list_item_compras, parent, false);
        Lista_de_produtos lista_produto = lista_produtos.get(position);
        TextView nomeProduto = (TextView) view.findViewById(R.id.tvProduto);
        TextView quantidade = (TextView) view.findViewById(R.id.tvQuantidade);
        TextView valorTotal = (TextView) view.findViewById(R.id.tvValalorTotal);
        CheckBox comprado = (CheckBox) view.findViewById(R.id.cbComprado);
        nomeProduto.setText("teste");
        //nomeProduto.setText(new Banco(session).getProdutoDescricao(lista_de_produto.getCod_barras()));
        quantidade.setText(String.valueOf(lista_produto.getQuantidade()));
        valorTotal.setText(String.valueOf(lista_produto.getQuantidade()*lista_produto.getValor()));
        //fazer um switch para saber qual a situação desse produto
        //comprado.setChecked(compra.isComprado());
        comprado.setChecked(false);
        if(position % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#ffaf18"));
        }
        return view;
    }

    @Override
    public int getCount() {
        return lista_produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}