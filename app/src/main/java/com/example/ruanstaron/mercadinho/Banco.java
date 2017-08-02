package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.Compras;
import com.example.ruanstaron.mercadinho.db.ComprasDao;
import com.example.ruanstaron.mercadinho.db.DaoSession;
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