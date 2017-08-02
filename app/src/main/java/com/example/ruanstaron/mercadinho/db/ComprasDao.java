package com.example.ruanstaron.mercadinho.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPRAS".
*/
public class ComprasDao extends AbstractDao<Compras, Long> {

    public static final String TABLENAME = "COMPRAS";

    /**
     * Properties of entity Compras.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ListaId = new Property(1, Long.class, "listaId", false, "LISTA_ID");
        public final static Property Cod_barras = new Property(2, Long.class, "cod_barras", false, "COD_BARRAS");
        public final static Property Quantidade = new Property(3, Integer.class, "quantidade", false, "QUANTIDADE");
        public final static Property Valor = new Property(4, Double.class, "valor", false, "VALOR");
        public final static Property ValorTotal = new Property(5, Double.class, "valorTotal", false, "VALOR_TOTAL");
        public final static Property Manual = new Property(6, Boolean.class, "Manual", false, "MANUAL");
    }

    private DaoSession daoSession;


    public ComprasDao(DaoConfig config) {
        super(config);
    }
    
    public ComprasDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPRAS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LISTA_ID\" INTEGER," + // 1: listaId
                "\"COD_BARRAS\" INTEGER," + // 2: cod_barras
                "\"QUANTIDADE\" INTEGER," + // 3: quantidade
                "\"VALOR\" REAL," + // 4: valor
                "\"VALOR_TOTAL\" REAL," + // 5: valorTotal
                "\"MANUAL\" INTEGER);"); // 6: Manual
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPRAS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Compras entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long listaId = entity.getListaId();
        if (listaId != null) {
            stmt.bindLong(2, listaId);
        }
 
        Long cod_barras = entity.getCod_barras();
        if (cod_barras != null) {
            stmt.bindLong(3, cod_barras);
        }
 
        Integer quantidade = entity.getQuantidade();
        if (quantidade != null) {
            stmt.bindLong(4, quantidade);
        }
 
        Double valor = entity.getValor();
        if (valor != null) {
            stmt.bindDouble(5, valor);
        }
 
        Double valorTotal = entity.getValorTotal();
        if (valorTotal != null) {
            stmt.bindDouble(6, valorTotal);
        }
 
        Boolean Manual = entity.getManual();
        if (Manual != null) {
            stmt.bindLong(7, Manual ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Compras entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long listaId = entity.getListaId();
        if (listaId != null) {
            stmt.bindLong(2, listaId);
        }
 
        Long cod_barras = entity.getCod_barras();
        if (cod_barras != null) {
            stmt.bindLong(3, cod_barras);
        }
 
        Integer quantidade = entity.getQuantidade();
        if (quantidade != null) {
            stmt.bindLong(4, quantidade);
        }
 
        Double valor = entity.getValor();
        if (valor != null) {
            stmt.bindDouble(5, valor);
        }
 
        Double valorTotal = entity.getValorTotal();
        if (valorTotal != null) {
            stmt.bindDouble(6, valorTotal);
        }
 
        Boolean Manual = entity.getManual();
        if (Manual != null) {
            stmt.bindLong(7, Manual ? 1L: 0L);
        }
    }

    @Override
    protected final void attachEntity(Compras entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Compras readEntity(Cursor cursor, int offset) {
        Compras entity = new Compras( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // listaId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // cod_barras
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // quantidade
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // valor
            cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5), // valorTotal
            cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0 // Manual
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Compras entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setListaId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCod_barras(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setQuantidade(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setValor(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setValorTotal(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
        entity.setManual(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Compras entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Compras entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Compras entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
