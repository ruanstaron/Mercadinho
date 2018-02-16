package com.example.ruanstaron.mercadinho.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.model.Compra;
import java.util.List;

/**
 * Created by pucci on 07/08/2017.
 */

public class CompraAdapter extends BaseAdapter {

    private final Activity act;
    private final List<Compra> compras;

    public CompraAdapter(List<Compra> compras, Activity act) {
        this.compras = compras;
        this.act = act;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.list_item_compras, parent, false);
        Compra compra = compras.get(position);
        TextView nomeProduto = (TextView) view.findViewById(R.id.tvProduto);
        TextView quantidade = (TextView) view.findViewById(R.id.tvQuantidade);
        TextView valorTotal = (TextView) view.findViewById(R.id.tvValalorTotal);
        CheckBox comprado = (CheckBox) view.findViewById(R.id.cbComprado);
        nomeProduto.setText(compra.getProduto());
        quantidade.setText(String.valueOf(compra.getQuantidade()));
        valorTotal.setText(String.valueOf(compra.getQuantidade()*compra.getValorUnitario()));
        comprado.setChecked(compra.isComprado());
        if(position % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#ffaf18"));
        }
        return view;
    }

    @Override
    public int getCount() {
        return compras.size();
    }

    @Override
    public Object getItem(int position) {
        return compras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}