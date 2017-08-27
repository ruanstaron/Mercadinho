package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.Compras;
import com.example.ruanstaron.mercadinho.db.ComprasDao;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Produtos;
import com.example.ruanstaron.mercadinho.db.ProdutosDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<String> carregaNomeProdutos(){
        ProdutosDao produtosDao = session.getProdutosDao();

        List<Produtos> produtos = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.isNotNull()).orderAsc().list();
        ArrayList<String> alNomeProdutos = new ArrayList<String>();

        for(int i = 0; i < produtos.size(); i++)
            alNomeProdutos.add(produtos.get(i).getDescricao());

        return alNomeProdutos;
    }

    // NAO REMOVER POR ENQUANTO, PODE SER USADO NO PROBLEMA DO AUTOCOMPLETE
    public ArrayList<String> carregaCodBarrasProdutos(){
        ProdutosDao produtosDao = session.getProdutosDao();

        List<Produtos> produtos = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.isNotNull()).orderAsc().list();
        ArrayList<String> alCodBarrasProdutos = new ArrayList<String>();

        for(int i = 0; i < produtos.size(); i++)
            alCodBarrasProdutos.add(produtos.get(i).getCod_barras().toString());

        return alCodBarrasProdutos;
    }

    public List<Produtos> carregaProdutosManuais(){
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> datalist = produtosDao.queryBuilder().where(ProdutosDao.Properties.Manual.eq(true)).list();
        return datalist;
    }

    public String getProdutoDescricao(Long cod_barras){
        String descricao;
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> datalist = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.eq(cod_barras)).list();

        if(datalist.size() > 0)
            descricao = datalist.get(0).getDescricao();
        else
            descricao = "";

        return descricao;
    }

    public Produtos carregaProduto(String descricao){
        ProdutosDao produtosDao = session.getProdutosDao();
        Produtos produto = produtosDao.queryBuilder().where(ProdutosDao.Properties.Descricao.eq(descricao)).orderAsc().unique();

         if (produto != null) {
             return produto;
         }
         else{
             return produto = new Produtos();
         }
    }

    public String verificaProduto(Long cod_barras){
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> datalist = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.eq(cod_barras)).list();

        if(datalist.isEmpty()){
            return "";
        }else{
            return datalist.get(0).toString();
        }
    }

    public void setaProdutosEnviados(){
        ProdutosDao produtosDao = session.getProdutosDao();
        List<Produtos> lProdutos = produtosDao.queryBuilder().where(ProdutosDao.Properties.Cod_barras.isNotNull()).list();

        if(!lProdutos.isEmpty())
            for(int i = 0; i < lProdutos.size(); i++){
                lProdutos.get(i).setManual(false);
                produtosDao.update(lProdutos.get(i));
            }
    }
}