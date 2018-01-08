package com.example.ruanstaron.mercadinho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruanstaron.mercadinho.db.Lista;

import java.util.List;

/**
 * Created by pucci on 06/08/2017.
 */

public class ListaAdapter extends BaseAdapter {

    List<Lista> lListas;
    Context context;

    public ListaAdapter(Context context, List<Lista> lListas){
        this.context = context;
        this.lListas = lListas;
    }

    @Override
    public int getCount() {
        return lListas.size();
    }

    @Override
    public Object getItem(int position) {
        return lListas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lista lista = lListas.get(position);
        View linha = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_1, parent, false);

        TextView lvtvDescricao = ((TextView) linha.findViewById(android.R.id.text1));
        lvtvDescricao.setText(lista.getDescricao());

        return linha;
    }
}
