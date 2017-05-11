package com.example.ruanstaron.mercadinho;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void startScan(View view) {
        Intent scan = new Intent(this, Scan.class);
        startActivity(scan);
    }

    public void startBanco(View view) {
        Intent banco = new Intent(this, Banco.class);
        startActivity(banco);
    }
    //ta bugado esse git
}