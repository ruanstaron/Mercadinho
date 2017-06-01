package com.example.ruanstaron.mercadinho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;

import org.greenrobot.greendao.database.Database;

public class Banco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
    }
}
