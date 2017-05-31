package com.example.ruanstaron.mercadinho.db;

import org.greenrobot.greendao.annotation.*;

import com.example.ruanstaron.mercadinho.db.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "PRODUTOS".
 */
@Entity(active = true)
public class Produtos {

    @Id
    private Long id;
    private Long listaId;
    private Integer cod_barras;
    private String descricao;
    private Integer quantidade;
    private Double valor;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient ProdutosDao myDao;

    @ToOne(joinProperty = "listaId")
    private Lista lista;

    @Generated
    private transient Long lista__resolvedKey;

    @Generated
    public Produtos() {
    }

    public Produtos(Long id) {
        this.id = id;
    }

    @Generated
    public Produtos(Long listaId, Integer cod_barras, String descricao, Integer quantidade, Double valor) {
        this.listaId = listaId;
        this.cod_barras = cod_barras;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Produtos(Long id, Long listaId, Integer cod_barras, String descricao, Integer quantidade, Double valor) {
        this.id = id;
        this.listaId = listaId;
        this.cod_barras = cod_barras;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProdutosDao() : null;
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

    public Integer getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(Integer cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    /** To-one relationship, resolved on first access. */
    @Generated
    public Lista getLista() {
        Long __key = this.listaId;
        if (lista__resolvedKey == null || !lista__resolvedKey.equals(__key)) {
            __throwIfDetached();
            ListaDao targetDao = daoSession.getListaDao();
            Lista listaNew = targetDao.load(__key);
            synchronized (this) {
                lista = listaNew;
            	lista__resolvedKey = __key;
            }
        }
        return lista;
    }

    @Generated
    public void setLista(Lista lista) {
        synchronized (this) {
            this.lista = lista;
            listaId = lista == null ? null : lista.getId();
            lista__resolvedKey = listaId;
        }
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

}
