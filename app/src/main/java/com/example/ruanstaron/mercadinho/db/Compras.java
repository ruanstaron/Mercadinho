package com.example.ruanstaron.mercadinho.db;

import org.greenrobot.greendao.annotation.*;

import java.util.List;

import org.greenrobot.greendao.DaoException;


/**
 * Entity mapped to table "COMPRAS".
 */
@Entity(active = true)
public class Compras {

    @Id
    private Long id;
    private Long listaId;
    private Long cod_barras;
    private Integer quantidade;
    private Double valor;
    private Double valorTotal;
    private Boolean Manual;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient ComprasDao myDao;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "listaId")
    })
    private List<Lista> listaList;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "cod_barras")
    })
    private List<Produtos> produtosList;

    @Generated
    public Compras() {
    }

    public Compras(Long id) {
        this.id = id;
    }

    @Generated
    public Compras(Long id, Long listaId, Long cod_barras, Integer quantidade, Double valor, Double valorTotal, Boolean Manual) {
        this.id = id;
        this.listaId = listaId;
        this.cod_barras = cod_barras;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTotal = valorTotal;
        this.Manual = Manual;
    }

    public Compras(Long listaId, Long cod_barras, Integer quantidade, Double valor, Double valorTotal, Boolean Manual) {
        this.listaId = listaId;
        this.cod_barras = cod_barras;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTotal = valorTotal;
        this.Manual = Manual;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getComprasDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public Long getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(Long cod_barras) {
        this.cod_barras = cod_barras;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getManual() {
        return Manual;
    }

    public void setManual(Boolean Manual) {
        this.Manual = Manual;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Lista> getListaList() {
        if (listaList == null) {
            __throwIfDetached();
            ListaDao targetDao = daoSession.getListaDao();
            List<Lista> listaListNew = targetDao._queryCompras_ListaList(id);
            synchronized (this) {
                if(listaList == null) {
                    listaList = listaListNew;
                }
            }
        }
        return listaList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetListaList() {
        listaList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Produtos> getProdutosList() {
        if (produtosList == null) {
            __throwIfDetached();
            ProdutosDao targetDao = daoSession.getProdutosDao();
            List<Produtos> produtosListNew = targetDao._queryCompras_ProdutosList(id);
            synchronized (this) {
                if(produtosList == null) {
                    produtosList = produtosListNew;
                }
            }
        }
        return produtosList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetProdutosList() {
        produtosList = null;
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    public String toString(){
        return "Valor="+valor+" Quantidade="+quantidade+" Valor Total="+valorTotal;
    }

}