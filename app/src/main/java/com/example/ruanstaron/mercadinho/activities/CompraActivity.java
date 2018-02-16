package com.example.ruanstaron.mercadinho.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.adapters.CompraAdapter;
import com.example.ruanstaron.mercadinho.db.Produto;
import com.example.ruanstaron.mercadinho.model.Compra;
import com.google.zxing.integration.android.IntentIntegrator;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CompraActivity extends AppCompatActivity implements OnClickListener {

    /*private Button                  scanBtn, okBtn;
    private TextView                tvValorTotal;
    private EditText                etQuantidade, etValor;
    private AutoCompleteTextView    etProduto;

    private ArrayList<String>       alProdutosAutocompletar;
    private ArrayAdapter<String>    adapterNomeProdutos;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster               master;
    private DaoSession              session;
    private Double                  valorTotalCompra = 0.00;
    private Banco                   banco;
    private Long                    codEscaneado = (long)0;
    private Boolean                 retornoScanFalse = false;/*

    private Lista lista = new Lista();*/
    private EditText                etProduto;
    private ListView                listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        listaCompras = (ListView) findViewById(R.id.lvCompras);
        try {
            List<Compra> compras = buscaCompras();
            CompraAdapter adapter = new CompraAdapter(compras, this);
            listaCompras.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        etProduto    = (EditText) findViewById(R.id.etProduto);
        etProduto.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Intent itBuscaProduto = new Intent(getApplicationContext(), BuscaProdutosActivity.class);
                startActivity(itBuscaProduto);
                return true;
            }
        });

        /*helper  = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master  = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);

        scanBtn      = (Button)findViewById(R.id.scan_button);
        okBtn        = (Button)findViewById(R.id.bOk);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etValor      = (EditText) findViewById(R.id.etValor);
        tvValorTotal = (TextView) findViewById(R.id.valorTotal);
        listaCompras = (ListView) findViewById(R.id.lvBuscaProdutos);
        scanBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);*/
        //Intent it = getIntent();

        /*if (it.hasExtra("id_lista")){
            lista.setId(((long) Integer.parseInt(it.getStringExtra("id_lista"))));
            lista.setDescricao(it.getStringExtra("descricao_lista"));

            lista = banco.carregaListas(lista.getId().intValue());

            atualizaCompras();
        }

        etProduto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !etProduto.getText().toString().isEmpty()){
                    codEscaneado = new Banco(session).carregaProduto(etProduto.getText().toString()).getCod_barras();
                }
            }
        });

        atualizaNomeProdutos();*/
    }

    private List<Compra> buscaCompras() throws ParseException {

        List<Compra> compras = new ArrayList<Compra>();
        Compra c = new Compra("produto1", 1, 1, false);
        compras.add(c);

        c = new Compra("produto2", 2, 2, false);
        compras.add(c);

        c = new Compra("produto3", 3, 3, false);
        compras.add(c);

        c = new Compra("produto3", 3, 3, false);
        compras.add(c);

        c = new Compra("produto4", 4, 4, false);
        compras.add(c);
        return compras;
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        if(retornoScanFalse){
            ListaDialogProduto dlgNomeProduto = new ListaDialogProduto();
            dlgNomeProduto.show(getSupportFragmentManager(), "dlgnomeProduto");
            retornoScanFalse = false;
        }
    }*/

    public void onClick(View v){
        switch (v.getId()){
            case R.id.scan_button:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            case R.id.bOk:
                //IncluiCompraLista();
                break;
        }

        // TODO: IMPLEMENTAR UM LISTENER CUSTOMIZADO OU IMPLEMENTAR DE FORMA ANONIMA
    }

    /*public void atualizaCompras(){
        List<Lista_de_produtos> lComprasAtualizadas = new Banco(session).carregaCompras(Integer.parseInt(lista.getId().toString()));
        listaCompras.setAdapter(new CompraAdapter(this, lComprasAtualizadas));
    }

    public void atualizaNomeProdutos(){
        alProdutosAutocompletar = new Banco(session).carregaNomeProdutos();
        adapterNomeProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alProdutosAutocompletar);
        etProduto.setAdapter(adapterNomeProdutos);
        etProduto.setThreshold(1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            codEscaneado = Long.parseLong(scanContent);

            if (banco.getProdutoDescricao(Long.parseLong(scanContent)) != "")
                etProduto.setText(banco.getProdutoDescricao(Long.parseLong(scanContent)));
            else
                retornoScanFalse = true;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nenhum dado foi recebido!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void IncluiCompraLista(){
        if(etValor.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherValor, Toast.LENGTH_SHORT).show();
            return;
        }

        if(etQuantidade.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherQuantidade, Toast.LENGTH_SHORT).show();
            return;
        }

        if(etProduto.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherDescricao, Toast.LENGTH_SHORT).show();
            return;
        }

        Lista_de_produtos pCompras  = new Lista_de_produtos();
        pCompras.setCod_barras(new Banco(session).carregaProduto(etProduto.getText().toString()).getCod_barras());

        if((pCompras.getCod_barras() == null) || (pCompras.getCod_barras().toString() == "0")) {
            Toast.makeText(this, R.string.geraProdutoPreencherCodBarras, Toast.LENGTH_SHORT).show();
            return;
        }

        //Double valorTotal = Integer.parseInt(etQuantidade.getText().toString()) * Double.parseDouble(etValor.getText().toString());

        pCompras.setListaId(lista.getId());
        pCompras.setQuantidade(Float.parseFloat(etQuantidade.getText().toString()));
        pCompras.setValor(Float.parseFloat(etValor.getText().toString()));
        //pCompras.setValorTotal(valorTotal);

        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
        Toast.makeText(this, pCompras.getListaId().toString() + " - " + pCompras.getCod_barras().toString(), Toast.LENGTH_SHORT).show();
        comprasDao.insert(pCompras);
        atualizaCompras();

        //valorTotalCompra = valorTotalCompra+pCompras.getValorTotal();
        tvValorTotal.setText(valorTotalCompra.toString());

        etProduto.setText("");
        etQuantidade.setText("");
        etValor.setText("");
    }

    public static class ListaDialogProduto extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CompraActivity compraActivity = (CompraActivity)getActivity();
                    Produto produto = new Produto();
                    produto.setDescricao(input.getText().toString());
                    produto.setCod_barras(compraActivity.codEscaneado);
                    produto.setRecente(true);

                    ProdutoDao produtosDao = compraActivity.session.getProdutoDao();
                    produtosDao.insert(produto);
                    compraActivity.atualizaNomeProdutos();
                    compraActivity.etProduto.setText(produto.getDescricao());

                    dismiss();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoNomeProdutoTitulo)
                    .setMessage(R.string.dialogoNomeProdutoMsg)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }*/
}