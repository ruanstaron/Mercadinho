package com.example.ruanstaron.mercadinho.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
        //http://respostas.guj.com.br/4372-listview-multiselecionavel-com-checkbox-selecionar-varios-itens-de-uma-listview
        View view = act.getLayoutInflater().inflate(R.layout.list_item_compras, parent, false);
        final Lista_de_produtos lista_produto = lista_produtos.get(position);
        TextView nomeProduto = (TextView) view.findViewById(R.id.tvProduto);
        TextView quantidade = (TextView) view.findViewById(R.id.tvQuantidade);
        TextView valorUnitario = (TextView) view.findViewById(R.id.tvValorUnitario);
        TextView valorTotal = (TextView) view.findViewById(R.id.tvValorTotal);
        final CheckBox comprado = (CheckBox) view.findViewById(R.id.cbComprado);
        comprado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comprado.isChecked()){
                    if(lista_produto.getCod_barras()<0){
                        IntentIntegrator scan = new IntentIntegrator(act);
                        //scan.addExtra("Listview",1);
                        scan.initiateScan();
                    }else{
                        System.out.println("Produto COM código de barras");
                    }
                }else{
                    System.out.println("NÃO");
                }
            }
        });
        nomeProduto.setText(new Banco(session).getProdutoDescricao(lista_produto.getCod_barras()));
        quantidade.setText(String.valueOf(lista_produto.getQuantidade()));
        valorUnitario.setText(String.valueOf(lista_produto.getValor()));
        valorTotal.setText(String.valueOf(lista_produto.getQuantidade()*lista_produto.getValor()));
        atualizaCompra(view, Integer.parseInt(lista_produto.getSituacaoId().toString()), comprado);
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

    public void atualizaCompra(View view, int situacao, CheckBox comprado){
        switch(situacao){
            case 1:
                view.setBackgroundResource(R.drawable.list_selector_compra_ok);
                comprado.setChecked(true);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.list_selector_compra_divergencia);
                comprado.setChecked(true);
                break;
            case 3:
                view.setBackgroundResource(R.drawable.list_selector_compra_em_compra);
                comprado.setChecked(false);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.list_selector_compra_em_compra);
                comprado.setChecked(true);
                break;
            default:
                view.setBackgroundResource(R.drawable.list_selector_compra_em_compra);
                comprado.setChecked(false);
                break;
        }
    }
}