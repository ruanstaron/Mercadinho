package com.example.ruanstaron.mercadinho;

import com.example.ruanstaron.mercadinho.db.Cidade;
import com.example.ruanstaron.mercadinho.db.CidadeDao;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtosDao;
import com.example.ruanstaron.mercadinho.db.Mercado;
import com.example.ruanstaron.mercadinho.db.MercadoDao;
import com.example.ruanstaron.mercadinho.db.Produto;
import com.example.ruanstaron.mercadinho.db.ProdutoDao;

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

    public void excluiProdutos(){
        ProdutoDao produtosDao = session.getProdutoDao();
        produtosDao.queryBuilder().buildDelete().executeDeleteWithoutDetachingEntities();
        session.clear();
    }

    public void gravaProdutos(List<Produto> arrayBdd){
        ProdutoDao produtosDao = session.getProdutoDao();
        for (Produto bdd: arrayBdd){
            produtosDao.insert(bdd);
        }
    }

    public List<Lista> carregaListas(){
        ListaDao listasDao = session.getListaDao();
        List<Lista> datalist = listasDao.queryBuilder().where(ListaDao.Properties.Id.isNotNull()).orderDesc(ListaDao.Properties.Id).list();
        return datalist;
    }

    public Lista carregaListas(int id){
        ListaDao listasDao = session.getListaDao();
        Lista lista = listasDao.queryBuilder().where(ListaDao.Properties.Id.eq(id)).unique();
        return lista;
    }

    public List<Lista_de_produtos> carregaCompras(int id){
        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
        List<Lista_de_produtos> lCompras = comprasDao.queryBuilder().where(Lista_de_produtosDao.Properties.ListaId.eq(id)).list();
        return lCompras;
    }

    public ArrayList<String> carregaProdutosDaLista(int id){
        List<Lista_de_produtos> lCompras = carregaCompras(id);
        ArrayList<String> lCodBarras = new ArrayList<String>();

        for(int i = 0; i < lCompras.size(); i++){
            lCodBarras.add(String.valueOf(lCompras.get(i).getCod_barras()));
        }

        return lCodBarras;
    }

    public ArrayList<String> carregaNomeProdutos(){
        ProdutoDao produtosDao = session.getProdutoDao();

        List<Produto> produtos = produtosDao.queryBuilder().where(ProdutoDao.Properties.Cod_barras.isNotNull()).orderAsc().list();
        ArrayList<String> alNomeProdutos = new ArrayList<String>();

        for(int i = 0; i < produtos.size(); i++)
            alNomeProdutos.add(produtos.get(i).getDescricao());

        return alNomeProdutos;
    }

    // NAO REMOVER POR ENQUANTO, PODE SER USADO NO PROBLEMA DO AUTOCOMPLETE
    public ArrayList<String> carregaCodBarrasProdutos(){
        ProdutoDao produtosDao = session.getProdutoDao();

        List<Produto> produtos = produtosDao.queryBuilder().where(ProdutoDao.Properties.Cod_barras.isNotNull()).orderAsc().list();
        ArrayList<String> alCodBarrasProdutos = new ArrayList<String>();

        for(int i = 0; i < produtos.size(); i++)
            alCodBarrasProdutos.add(produtos.get(i).getCod_barras().toString());

        return alCodBarrasProdutos;
    }

    public List<Produto> carregaProdutosManuais(){
        ProdutoDao produtosDao = session.getProdutoDao();
        List<Produto> datalist = produtosDao.queryBuilder().where(ProdutoDao.Properties.Recente.eq(true)).list();
        return datalist;
    }

    public String getProdutoDescricao(Long cod_barras){
        String descricao;
        ProdutoDao produtosDao = session.getProdutoDao();
        List<Produto> datalist = produtosDao.queryBuilder().where(ProdutoDao.Properties.Cod_barras.eq(cod_barras)).list();

        if(datalist.size() > 0)
            descricao = datalist.get(0).getDescricao();
        else
            descricao = "";

        return descricao;
    }

    public Produto carregaProduto(String descricao){
        ProdutoDao produtosDao = session.getProdutoDao();
        Produto produto = produtosDao.queryBuilder().where(ProdutoDao.Properties.Descricao.eq(descricao)).orderAsc().unique();

         if (produto != null) {
             return produto;
         }
         else{
             return produto = new Produto();
         }
    }

    public String verificaProduto(Long cod_barras){
        ProdutoDao produtosDao = session.getProdutoDao();
        List<Produto> datalist = produtosDao.queryBuilder().where(ProdutoDao.Properties.Cod_barras.eq(cod_barras)).list();

        if(datalist.isEmpty()){
            return "";
        }else{
            return datalist.get(0).toString();
        }
    }

    public void setaProdutosEnviados(){
        ProdutoDao produtosDao = session.getProdutoDao();
        List<Produto> lProdutos = produtosDao.queryBuilder().where(ProdutoDao.Properties.Cod_barras.isNotNull()).list();

        if(!lProdutos.isEmpty())
            for(int i = 0; i < lProdutos.size(); i++){
                lProdutos.get(i).setRecente(false);
                produtosDao.update(lProdutos.get(i));
            }
    }

    public List<Lista_de_produtos> carregaComprasLista(long id){
        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
        List<Lista_de_produtos> datalist = comprasDao.queryBuilder().where(Lista_de_produtosDao.Properties.ListaId.eq(id)).list();

        return datalist;
    }


    public void excluiListas(long idLista){
        ListaDao listaDao = session.getListaDao();
        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();

        List<Lista_de_produtos> comprasVinculadas = carregaComprasLista(idLista);

        for(int i = 0; i < comprasVinculadas.size(); i++)
            comprasDao.deleteByKey(comprasVinculadas.get(i).getId());

        listaDao.deleteByKey(idLista);
    }

    public void insertMercados(List<Mercado> lMercado){
        MercadoDao mercadoDao = session.getMercadoDao();

        for (Mercado mercado : lMercado) {
            mercadoDao.insert(mercado);
        }
    }

    public List<Cidade> selectCidadesTodas(){
        CidadeDao cidadeDao = session.getCidadeDao();
        List<Cidade> lCidades = cidadeDao.queryBuilder().list();

        return lCidades;
    }


}