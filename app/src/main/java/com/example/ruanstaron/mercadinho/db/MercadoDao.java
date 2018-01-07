package com.example.ruanstaron.mercadinho.db;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MERCADO".
*/
public class MercadoDao extends AbstractDao<Mercado, Long> {

    public static final String TABLENAME = "MERCADO";

    /**
     * Properties of entity Mercado.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Cnpj = new Property(0, Long.class, "cnpj", true, "CNPJ");
        public final static Property CidadeId = new Property(1, Long.class, "cidadeId", false, "CIDADE_ID");
        public final static Property Descricao = new Property(2, String.class, "descricao", false, "DESCRICAO");
        public final static Property Recente = new Property(3, Boolean.class, "recente", false, "RECENTE");
    }

    private DaoSession daoSession;

    private Query<Mercado> lista_de_produtos_MercadoListQuery;

    public MercadoDao(DaoConfig config) {
        super(config);
    }
    
    public MercadoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MERCADO\" (" + //
                "\"CNPJ\" INTEGER PRIMARY KEY ," + // 0: cnpj
                "\"CIDADE_ID\" INTEGER," + // 1: cidadeId
                "\"DESCRICAO\" TEXT," + // 2: descricao
                "\"RECENTE\" INTEGER);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MERCADO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Mercado entity) {
        stmt.clearBindings();
 
        Long cnpj = entity.getCnpj();
        if (cnpj != null) {
            stmt.bindLong(1, cnpj);
        }
 
        Long cidadeId = entity.getCidadeId();
        if (cidadeId != null) {
            stmt.bindLong(2, cidadeId);
        }
 
        String descricao = entity.getDescricao();
        if (descricao != null) {
            stmt.bindString(3, descricao);
        }
 
        Boolean recente = entity.getRecente();
        if (recente != null) {
            stmt.bindLong(4, recente ? 1L: 0L);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Mercado entity) {
        stmt.clearBindings();
 
        Long cnpj = entity.getCnpj();
        if (cnpj != null) {
            stmt.bindLong(1, cnpj);
        }
 
        Long cidadeId = entity.getCidadeId();
        if (cidadeId != null) {
            stmt.bindLong(2, cidadeId);
        }
 
        String descricao = entity.getDescricao();
        if (descricao != null) {
            stmt.bindString(3, descricao);
        }
 
        Boolean recente = entity.getRecente();
        if (recente != null) {
            stmt.bindLong(4, recente ? 1L: 0L);
        }
    }

    @Override
    protected final void attachEntity(Mercado entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Mercado readEntity(Cursor cursor, int offset) {
        Mercado entity = new Mercado( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // cnpj
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // cidadeId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // descricao
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0 // recente
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Mercado entity, int offset) {
        entity.setCnpj(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCidadeId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setDescricao(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRecente(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Mercado entity, long rowId) {
        entity.setCnpj(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Mercado entity) {
        if(entity != null) {
            return entity.getCnpj();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Mercado entity) {
        return entity.getCnpj() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "mercadoList" to-many relationship of Lista_de_produtos. */
    public List<Mercado> _queryLista_de_produtos_MercadoList(Long cnpj) {
        synchronized (this) {
            if (lista_de_produtos_MercadoListQuery == null) {
                QueryBuilder<Mercado> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Cnpj.eq(null));
                lista_de_produtos_MercadoListQuery = queryBuilder.build();
            }
        }
        Query<Mercado> query = lista_de_produtos_MercadoListQuery.forCurrentThread();
        query.setParameter(0, cnpj);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCidadeDao().getAllColumns());
            builder.append(" FROM MERCADO T");
            builder.append(" LEFT JOIN CIDADE T0 ON T.\"CIDADE_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Mercado loadCurrentDeep(Cursor cursor, boolean lock) {
        Mercado entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Cidade cidade = loadCurrentOther(daoSession.getCidadeDao(), cursor, offset);
        entity.setCidade(cidade);

        return entity;    
    }

    public Mercado loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Mercado> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Mercado> list = new ArrayList<Mercado>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Mercado> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Mercado> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}