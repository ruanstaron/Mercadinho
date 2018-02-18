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
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtosDao;

import java.util.List;

/**
 * Created by pucci on 06/08/2017.
 */

public class ListaAdapter extends BaseAdapter {

    List<Lista> lListas;
    Context context;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private Lista_de_produtosDao listaProdutosDao;
    private Banco banco;

    public ListaAdapter(Context context, List<Lista> lListas){
        this.context = context;
        this.lListas = lListas;

        helper = new DaoMaster.DevOpenHelper(context, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
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
        View linha = LayoutInflater.from(context).inflate(R.layout.list_item_listas, parent, false);

        TextView  lvtvDescricao = ((TextView) linha.findViewById(R.id.lvtvDescricao));

        listaProdutosDao = session.getLista_de_produtosDao();
        List<Lista_de_produtos> lListaProdutos = listaProdutosDao.queryBuilder().where(Lista_de_produtosDao.Properties.ListaId.eq(lista.getId())).list();

        if(!lListaProdutos.isEmpty()){
            for (Lista_de_produtos lListaProdutosAux : lListaProdutos) {
                if(lListaProdutosAux.getSituacaoId() == Lista_de_produtos.DIVERGENCIA){
                    pintaLinha(Lista_de_produtos.DIVERGENCIA, linha);
                    break;
                }else if (lListaProdutosAux.getSituacaoId() == Lista_de_produtos.COMPRA ||
                          lListaProdutosAux.getSituacaoId() == Lista_de_produtos.COMPRADO){
                    pintaLinha(Lista_de_produtos.COMPRA, linha);
                    break;
                }
                else {
                    pintaLinha(Lista_de_produtos.OK, linha);
                }
            }
        }else
            pintaLinha(Lista_de_produtos.COMPRA, linha);

        lvtvDescricao.setText(lista.getDescricao());

        return linha;
    }

    private void pintaLinha(int idSituacao, View linha){
        ImageView imgIcone = ((ImageView) linha.findViewById(R.id.imgIconeListaSituacao));

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
