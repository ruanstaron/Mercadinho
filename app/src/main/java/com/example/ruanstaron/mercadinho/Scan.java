package com.example.ruanstaron.mercadinho;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ruanstaron.mercadinho.db.Compras;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Scan extends AppCompatActivity implements OnClickListener {
    private Button scanBtn, okBtn;
    private TextView tvValorTotal;
    private EditText etProduto, etQuantidade, etValor;
    private ListView listaCompras;
    private ArrayList<String> compras;
    private ArrayAdapter<String> arrayAdapter;
    private ImageButton btnConfirma;
    DaoMaster.DevOpenHelper helper;
    DaoMaster master;
    DaoSession session;
    ListaDao listaDao;
    Long cod_barras;
    Double valorTotalCompra = 0.00;
    Banco banco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        scanBtn = (Button)findViewById(R.id.scan_button);
        okBtn = (Button)findViewById(R.id.bOk);
        etProduto = (EditText) findViewById(R.id.etProduto);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etValor = (EditText) findViewById(R.id.etValor);
        tvValorTotal = (TextView) findViewById(R.id.valorTotal);
        listaCompras = (ListView) findViewById(R.id.lvProdutos);
        scanBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        compras = new ArrayList<String>();
        btnConfirma = (ImageButton) findViewById(R.id.btnAddLista);
        btnConfirma.setOnClickListener(this);
        banco = new Banco(session);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnAddLista:
                DialogoConfirmaLista dfConfirmaLista = new DialogoConfirmaLista();
                dfConfirmaLista.show(getSupportFragmentManager(), "dfConfirmaLista");

                break;
            case R.id.scan_button:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            case R.id.bOk:
                geraProduto();
                break;
        }

        // TODO: IMPLEMENTAR UM LISTENER CUSTOMIZADO OU IMPLEMENTAR DE FORMA ANONIMA
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("FORMAT: " + scanFormat);
            etProduto.setText(banco.verificaProduto(Long.parseLong(scanContent)));
            cod_barras = Long.parseLong(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Nenhum dado foi recebido!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void geraProduto(){
        if(etProduto.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherProduto, Toast.LENGTH_SHORT).show();
            return;
        }

        Double valorTotal = Integer.parseInt(etQuantidade.getText().toString()) * Double.parseDouble(etValor.getText().toString());
        //aqui tem a parada da lista
        Compras compra = new Compras ((long) 1, this.cod_barras, Integer.parseInt(etQuantidade.getText().toString()), Double.parseDouble(etValor.getText().toString()), valorTotal, true);
        compras.add("Produto = "+etProduto.getText().toString()+" "+compra.toString());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, compras);
        listaCompras.setAdapter(arrayAdapter);
        valorTotalCompra = valorTotalCompra+compra.getValorTotal();
        tvValorTotal.setText(valorTotalCompra.toString());
        etProduto.setText("");
        etQuantidade.setText("");
        etValor.setText("");
    }

    public void IncluirBancoLista(DaoSession session, Lista lista){
        listaDao = session.getListaDao();
        listaDao.insert(lista);
        Toast toast = Toast.makeText(getApplicationContext(),
                lista.getId().toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public static class DialogoConfirmaLista extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int button){
                    if(button == DialogInterface.BUTTON_POSITIVE){
                        dismiss();
                        DialogoNomeLista dfNomeDialogo = new DialogoNomeLista();
                        dfNomeDialogo.show(getFragmentManager(), "dfNomeDialogo");
                    }
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialogoConfirmaListaTitulo)
                    .setMessage(R.string.dialogoConfirmaListaMsg)
                    .setPositiveButton(R.string.sim, listener)
                    .setNegativeButton(R.string.nao, null)
                    .create();
            return dialog;
        }
    }

    public static class DialogoNomeLista extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Scan scan = (Scan)getActivity();
                    Lista lista = new Lista();
                    lista.setDescricao(input.getText().toString());
                    scan.IncluirBancoLista(scan.session, lista);
                    dismiss();
                    scan.finish();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoNomeListaTitulo)
                    .setMessage(R.string.dialogoNomeListaMsg)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

                return dialog;
        }
    }
}