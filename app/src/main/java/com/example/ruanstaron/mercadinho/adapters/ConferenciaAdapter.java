package com.example.ruanstaron.mercadinho.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtosDao;

import java.util.List;

/**
 * Created by pucci on 06/08/2017.
 */

public class ConferenciaAdapter extends BaseAdapter {

    private List<Lista_de_produtos> compras;
    private Context                 context;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster               master;
    private DaoSession              session;
    private Lista_de_produtosDao    listaProdutosDao;
    private Banco                   banco;

    public ConferenciaAdapter(Context context, List<Lista_de_produtos> compras){
        this.context   = context;
        this.compras   = compras;

        helper = new DaoMaster.DevOpenHelper(context, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lista_de_produtos compra = compras.get(position);
        View linha = LayoutInflater.from(context).inflate(R.layout.list_item_conferencia, parent, false);

        TextView  lvtvProdutoConf   = ((TextView) linha.findViewById(R.id.lvtvProdutoConf));
        TextView  lvtvValorEsperado = ((TextView) linha.findViewById(R.id.lvtvValorEsperado));
        TextView  lvtvValorNota     = ((TextView) linha.findViewById(R.id.lvtvValorNota));

        listaProdutosDao = session.getLista_de_produtosDao();

        lvtvProdutoConf.setText(banco.getProdutoDescricao(compra.getCod_barras()));
        lvtvValorEsperado.setText(String.valueOf(compra.getValor()));
        lvtvValorNota.setText(String.valueOf(compra.getValor_nota()));

        switch (String.valueOf(compra.getSituacaoId())){
            case "1" : pintaLinha(Lista_de_produtos.OK, linha);
                break;
            case "2": pintaLinha(Lista_de_produtos.DIVERGENCIA, linha);
                break;
            case "3": pintaLinha(Lista_de_produtos.COMPRA, linha);
                break;
        }

        return linha;
    }

    private void pintaLinha(int idSituacao, View linha){
        ImageView imgIcone = ((ImageView) linha.findViewById(R.id.imgIconeConfSituacao));

        switch (idSituacao){
            case Lista_de_produtos.DIVERGENCIA:
                linha.setBackgroundResource(R.drawable.list_selector_lista_divergencia);
                imgIcone.setImageResource(R.drawable.ic_cancel_black_24dp);
                break;
            case
                Lista_de_produtos.OK:
                linha.setBackgroundResource(R.drawable.list_selector_lista_ok);
                imgIcone.setImageResource(R.drawable.ic_check_circle_black_24dp);
                break;
            default:
                linha.setBackgroundResource(R.drawable.list_selector_lista_em_compra);
                imgIcone.setImageResource(R.drawable.ic_add_shopping_cart_black_24dp);
        }
    }
}
