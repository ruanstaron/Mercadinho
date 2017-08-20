package com.example.ruanstaron.mercadinho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.db.Compras;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pucci on 07/08/2017.
 */

public class ComprasAdapter extends BaseAdapter {

    List<Compras> lCompras;
    Context context;

    public ComprasAdapter(Context context, List<Compras> lCompras){
        this.context = context;
        this.lCompras = lCompras;
    }

    @Override
    public int getCount() {
        return lCompras.size();
    }

    @Override
    public Object getItem(int position) {
        return lCompras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "mercadinho-db");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        Banco banco = new Banco(session);

        Compras compras = lCompras.get(position);

        View linha = LayoutInflater.from(context).inflate(R.layout.list_item_compras, parent, false);

        TextView tvQuantidade = ((TextView) linha.findViewById(R.id.lvtvQuantidade));
        TextView tvProduto    = ((TextView) linha.findViewById(R.id.lvtvProduto));
        TextView tvValor      = ((TextView) linha.findViewById(R.id.lvtvValor));

        ArrayList<String> lCodBarras = new ArrayList<String>();
          lCodBarras = banco.carregaProdutosDaLista(Integer.parseInt(compras.getListaId().toString()));

        tvQuantidade.setText(compras.getQuantidade().toString());
        tvProduto.setText(banco.getProdutoDescricao(Long.parseLong(lCodBarras.get(position).toString())));
        tvValor.setText(compras.getValor().toString());

        return linha;
    }
}
