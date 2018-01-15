package com.example.ruanstaron.mercadinho.db;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Ruan on 14/01/2018.
 */

public class AtributosDao {

    public static void insereDados(Database db) {
        db.execSQL("INSERT INTO Situacao (descricao) VALUES (\"ok\");\n" +
                "INSERT INTO Situacao (descricao) VALUES (\"divergencia\");\n" +
                "INSERT INTO Situacao (descricao) VALUES (\"compra\")");
    }
}
