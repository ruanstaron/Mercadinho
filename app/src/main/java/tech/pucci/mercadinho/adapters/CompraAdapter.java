package tech.pucci.mercadinho.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import tech.pucci.mercadinho.R;

import java.util.List;

import tech.pucci.mercadinho.Banco;
import tech.pucci.mercadinho.activities.CompraActivity;
import tech.pucci.mercadinho.db.DaoMaster;
import tech.pucci.mercadinho.db.Lista_de_produtos;
import tech.zxing.android.IntentIntegrator;

import static tech.pucci.mercadinho.activities.CompraActivity.codBarrasOld;
import static tech.pucci.mercadinho.activities.CompraActivity.idListaCompras;
import static tech.pucci.mercadinho.activities.CompraActivity.valorProdutoZerado;

/**
 * Created by pucci on 07/08/2017.
 */

public class CompraAdapter extends BaseAdapter {

    private final Activity act;
    private final List<Lista_de_produtos> lista_produtos;
    private DaoMaster master;
    private Context context;

    public CompraAdapter(List<Lista_de_produtos> lista_produtos, Activity act, DaoMaster master, Context context) {
        this.lista_produtos = lista_produtos;
        this.act = act;
        this.master = master;
        this.context = context;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
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
                    if(lista_produto.getValor() == 0){
                        valorProdutoZerado = true;
                    }
                    if(lista_produto.getCod_barras()<0){
                        codBarrasOld = lista_produto.getCod_barras();
                        idListaCompras = lista_produto.getId();
                        IntentIntegrator scan = new IntentIntegrator(act);
                        scan.initiateScan();
                    }else{
                        if(lista_produto.getValor() == 0){
                            idListaCompras = lista_produto.getId();
                            CompraActivity.ListaDialogValorProduto dlgNomeValorProduto = new CompraActivity.ListaDialogValorProduto();
                            dlgNomeValorProduto.show(((CompraActivity) context).getSupportFragmentManager(), "dlgNomeValorProduto");
                        }
                        new Banco(master.newSession()).atualizaProdutoComprado(lista_produto.getId(), 4);
                    }
                }else{
                    new Banco(master.newSession()).atualizaProdutoComprado(lista_produto.getId(), 3);
                }
            }
        });
        nomeProduto.setText(new Banco(master.newSession()).getProdutoDescricao(lista_produto.getCod_barras()));
        quantidade.setText(String.valueOf(lista_produto.getQuantidade()));
        valorUnitario.setText(String.valueOf(lista_produto.getValor()));
        valorTotal.setText(String.valueOf(lista_produto.getQuantidade()*lista_produto.getValor()));
        pintaCompra(view, Integer.parseInt(lista_produto.getSituacaoId().toString()), comprado);
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

    public void pintaCompra(View view, int situacao, CheckBox comprado){
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