package com.example.ruanstaron.mercadinho;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class Banco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);
        SQLiteDatabase db;
        db = openOrCreateDatabase("TestData.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        if(db.getVersion()==1){

        }else{
            db.setVersion(1);
            db.setLocale(Locale.getDefault());
            db.setLockingEnabled(true);
            String sql_lista_de_compras = "CREATE TABLE lista_de_compras ( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_BARRAS INTEGER UNIQUE, DESCRICAO TEXT, QUANTIDADE INTEGER, VALOR DECIMAL);";
            String banco_nuvem = "CREATE TABLE banco_nuvem ( id INTEGER PRIMARY KEY AUTOINCREMENT, COD_BARRAS INTEGER UNIQUE, DESCRICAO TEXT);";
            db.execSQL(sql_lista_de_compras);
            db.execSQL(banco_nuvem);
        }
    }
}
