package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.Compras;
import com.example.ruanstaron.mercadinho.db.ComprasDao;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Produtos;
import com.example.ruanstaron.mercadinho.db.ProdutosDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruan on 02/08/2017.
 */

public class Banco {
    DaoSession session;

    public Banco(DaoSession session){
        this.session = session;
    }

    public void limpaBanco(){
        ProdutosDao produtosDao = session.getProdutosDao();
        produtosDao.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        session.clear();
    }

    public void gravaBanco(ArrayList<Produtos> arrayBdd){
        ProdutosDao produtosDao = session.getProdutosDao();
        for (Produtos bdd: arrayBdd){
            produtosDao.insert(bdd);
        }
    }

    public List<Lista> carregaListas(){
        ListaDao listasDao = session.getListaDao();
        List<Lista> datalist = listasDao.queryBuilder().where(ListaDao.Properties.Id.isNotNull()).list();
        return datalist;
    }

    public Lista carregaListas(int id){
        ListaDao listasDao = session.getListaDao();
        Lista lista = listasDao.queryBuilder().where(ListaDao.Properties.Id.eq(id)).unique();
        return lista;
    }

    public List<Compras> carregaCompras(int id){
        ComprasDao comprasDao = session.getComprasDao();
        List<Compras> lCompras = comprasDao.queryBuilder().where(ComprasDao.Properties.ListaId.eq(id)).list();
        return lCompras;
    }

    public ArrayList<String> carregaProdutosDaLista(int id){
        List<Compras> lCompras = carregaCompras(id);
        ArrayList<String> lCodBarras = new ArrayList<String>();

        for(int i = 0; i < lCompras.size(); i++){
            lCodBarras.add(String.valueOf(lCompras.get(i).getCod_barras()));
        }

        return lCodBarras;
    }

    public List<Compras> carregaProdutos(){
        ComprasDao comprasDao = session.getComprasDao();
        List<Compras> datalist = comprasDao.queryBuilder().where(ComprasDao.Properties.Manual.eq(true)).list();
        return datalist;
    }

    public String getDescricao (Long cod_barras){
        String descricao;
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> datalist = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.eq(cod_barras)).list();
        descricao = datalist.get(0).toString();
        return descricao;
    }

    public String verificaProduto(Long cod_barras){
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> datalist = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.eq(cod_barras)).list();
        if(datalist.isEmpty()){
            return "Digite o nome do produto";
        }else{
            return datalist.get(0).toString();
        }
    }
}