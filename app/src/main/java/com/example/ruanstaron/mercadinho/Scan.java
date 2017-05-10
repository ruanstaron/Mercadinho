package com.example.ruanstaron.mercadinho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Scan extends AppCompatActivity implements OnClickListener {
    private Button scanBtn, okBtn;
    private EditText etProduto, etQuantidade, etValor;
    ListView listaProdutos;
    private ArrayList<String> produtos;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        scanBtn = (Button)findViewById(R.id.scan_button);
        okBtn = (Button)findViewById(R.id.bOk);
        etProduto = (EditText) findViewById(R.id.etProduto);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etValor = (EditText) findViewById(R.id.etValor);
        listaProdutos = (ListView) findViewById(R.id.lvProdutos);
        scanBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        produtos = new ArrayList<String>();
    }

    public void onClick(View v){
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.bOk){
            geraProduto(v);
            etProduto.setText("");
            etQuantidade.setText("");
            etValor.setText("");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("FORMAT: " + scanFormat);
            etProduto.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nenhum dado foi recebido!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void geraProduto(View v){
        Produto produto = new Produto(etProduto.getText().toString(), etValor.getText().toString(), etQuantidade.getText().toString());
        produtos.add(produto.toString());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, produtos);
        listaProdutos.setAdapter(arrayAdapter);
    }
}