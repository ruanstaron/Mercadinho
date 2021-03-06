package tech.pucci.mercadinho.db;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import java.util.Objects;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS
// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "LISTA_DE_PRODUTOS".
 */
@Entity(active = true)
public class Lista_de_produtos {

    @Id
    private Long id;
    private Long cod_barras;
    private Long listaId;
    private Long cnpj;
    private Long situacaoId;
    private Float quantidade;
    private Float valor;
    private Float valor_nota;
    private java.util.Date data_da_compra;
    private Boolean recente;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient Lista_de_produtosDao myDao;

    @ToOne(joinProperty = "listaId")
    private Lista lista;

    @Generated
    private transient Long lista__resolvedKey;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "cod_barras")
    })
    private List<Produto> produtoList;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "cnpj")
    })
    private List<Mercado> mercadoList;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "situacaoId")
    })
    private List<Situacao> situacaoList;

    // KEEP FIELDS - put your custom fields here
    public static final int OK          = 1;
    public static final int DIVERGENCIA = 2;
    public static final int COMPRA      = 3;
    public static final int COMPRADO    = 4;
    // KEEP FIELDS END

    @Generated
    public Lista_de_produtos() {
    }

    public Lista_de_produtos(Long id) {
        this.id = id;
    }

    public Lista_de_produtos(Long codBar, float quantidade, float valor){
        this.cod_barras = codBar;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    @Generated
    public Lista_de_produtos(Long id, Long cod_barras, Long listaId, Long cnpj, Long situacaoId, Float quantidade, Float valor, Float valor_nota, java.util.Date data_da_compra, Boolean recente) {
        this.id = id;
        this.cod_barras = cod_barras;
        this.listaId = listaId;
        this.cnpj = cnpj;
        this.situacaoId = situacaoId;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valor_nota = valor_nota;
        this.data_da_compra = data_da_compra;
        this.recente = recente;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLista_de_produtosDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(Long cod_barras) {
        this.cod_barras = cod_barras;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Long getSituacaoId() {
        return situacaoId;
    }

    public void setSituacaoId(Long situacaoId) {
        this.situacaoId = situacaoId;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Float getValor_nota() {
        return valor_nota;
    }

    public void setValor_nota(Float valor_nota) {
        this.valor_nota = valor_nota;
    }

    public java.util.Date getData_da_compra() {
        return data_da_compra;
    }

    public void setData_da_compra(java.util.Date data_da_compra) {
        this.data_da_compra = data_da_compra;
    }

    public Boolean getRecente() {
        return recente;
    }

    public void setRecente(Boolean recente) {
        this.recente = recente;
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

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Produto> getProdutoList() {
        if (produtoList == null) {
            __throwIfDetached();
            ProdutoDao targetDao = daoSession.getProdutoDao();
            List<Produto> produtoListNew = targetDao._queryLista_de_produtos_ProdutoList(id);
            synchronized (this) {
                if(produtoList == null) {
                    produtoList = produtoListNew;
                }
            }
        }
        return produtoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetProdutoList() {
        produtoList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Mercado> getMercadoList() {
        if (mercadoList == null) {
            __throwIfDetached();
            MercadoDao targetDao = daoSession.getMercadoDao();
            List<Mercado> mercadoListNew = targetDao._queryLista_de_produtos_MercadoList(id);
            synchronized (this) {
                if(mercadoList == null) {
                    mercadoList = mercadoListNew;
                }
            }
        }
        return mercadoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetMercadoList() {
        mercadoList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Situacao> getSituacaoList() {
        if (situacaoList == null) {
            __throwIfDetached();
            SituacaoDao targetDao = daoSession.getSituacaoDao();
            List<Situacao> situacaoListNew = targetDao._queryLista_de_produtos_SituacaoList(id);
            synchronized (this) {
                if(situacaoList == null) {
                    situacaoList = situacaoListNew;
                }
            }
        }
        return situacaoList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetSituacaoList() {
        situacaoList = null;
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

    // KEEP METHODS - put your custom methods here

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lista_de_produtos)) return false;
        Lista_de_produtos that = (Lista_de_produtos) o;

        return Objects.equals(getCod_barras(), that.getCod_barras());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getCod_barras());
    }

    // KEEP METHODS END

}
