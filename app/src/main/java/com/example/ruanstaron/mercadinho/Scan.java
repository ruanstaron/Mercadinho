package com.example.ruanstaron.mercadinho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ruanstaron.mercadinho.db.BdDefinitivo;
import com.example.ruanstaron.mercadinho.db.BdDefinitivoDao;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.example.ruanstaron.mercadinho.db.Produtos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Scan extends AppCompatActivity implements OnClickListener {
    private Button scanBtn, okBtn;
    private TextView tvValorTotal;
    private EditText etProduto, etQuantidade, etValor;
    private ListView listaProdutos;
    private ArrayList<String> produtos;
    private ArrayAdapter<String> arrayAdapter;
    DaoMaster.DevOpenHelper helper;
    DaoMaster master;
    DaoSession session;
    ListaDao listaDao;
    String cod_barras;
    Double valorTotalCompra = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        geraLista(session);
        scanBtn = (Button)findViewById(R.id.scan_button);
        okBtn = (Button)findViewById(R.id.bOk);
        etProduto = (EditText) findViewById(R.id.etProduto);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etValor = (EditText) findViewById(R.id.etValor);
        tvValorTotal = (TextView) findViewById(R.id.valorTotal);
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
            etProduto.setText(verificaProduto(session, scanContent));
            cod_barras = scanContent;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nenhum dado foi recebido!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void geraProduto(View v){
        Double valorTotal = Integer.parseInt(etQuantidade.getText().toString()) * Double.parseDouble(etValor.getText().toString());
        Produtos produto = new Produtos(this.cod_barras, etProduto.getText().toString(),
                Integer.parseInt(etQuantidade.getText().toString()), Double.parseDouble(etValor.getText().toString()), valorTotal);
        produtos.add(produto.toString());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, produtos);
        listaProdutos.setAdapter(arrayAdapter);
        valorTotalCompra = valorTotalCompra+produto.getValorTotal();
        tvValorTotal.setText(valorTotalCompra.toString());
    }

    public String verificaProduto(DaoSession session, String cod_barras){
        BdDefinitivoDao bdDefinitivoDao = session.getBdDefinitivoDao();
        List<BdDefinitivo> datalist = bdDefinitivoDao.queryBuilder().where(BdDefinitivoDao.Properties.Cod_barras.eq(cod_barras)).list();
        if(datalist.isEmpty()){
            return "Digite o nome do produto";
        }else{
            return datalist.get(0).toString();
        }
    }

    public void geraLista(DaoSession session){
        listaDao = session.getListaDao();
        Lista lista = new Lista();
        lista.setDescricao("teste");
        listaDao.insert(lista);
        Toast toast = Toast.makeText(getApplicationContext(),
                lista.getId().toString(), Toast.LENGTH_SHORT);
        toast.show();
    }
}