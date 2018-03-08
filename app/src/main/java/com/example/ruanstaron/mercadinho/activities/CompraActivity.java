package com.example.ruanstaron.mercadinho.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.adapters.CompraAdapter;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtos;
import com.example.ruanstaron.mercadinho.db.Lista_de_produtosDao;
import com.example.ruanstaron.mercadinho.db.Produto;
import com.example.ruanstaron.mercadinho.db.ProdutoDao;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class CompraActivity extends AppCompatActivity implements ActionMode.Callback, OnClickListener, AdapterView.OnItemLongClickListener {

    private Lista lista = new Lista();
    private boolean                 retornoScanFalse = false;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster               master;
    private DaoSession              session;
    private Banco                   banco;
    private long                    codEscaneado = (long) 0;
    private Button                  scan;
    private Button                  add;
    private EditText                etProduto;
    private EditText                etQuantidade;
    private EditText                etValor;
    private ListView                listaCompras;
    private List<Lista_de_produtos> lista_de_produtos;
    private Double                  valorTotalCompra = 0.00;
    private TextView                tvValorTotal;
    private ActionMode              actionMode;
    private Produto                 produ;
    private boolean                 veioDoBotao = false;
    public static long              codBarrasNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        listaCompras = (ListView) findViewById(R.id.lvCompras);
        scan = (Button) findViewById(R.id.bEscanear);
        add = (Button) findViewById(R.id.bAdd);
        etProduto    = (EditText) findViewById(R.id.etProduto);
        etQuantidade    = (EditText) findViewById(R.id.etQuantidade);
        etValor    = (EditText) findViewById(R.id.etValor);
        tvValorTotal = (TextView) findViewById(R.id.tvValorTotal);

        helper  = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master  = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);

        scan.setOnClickListener(this);
        add.setOnClickListener(this);

        //clique longo no editText
        etProduto.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Intent itBuscaProduto = new Intent(getApplicationContext(), BuscaProdutosActivity.class);
                startActivity(itBuscaProduto);
                return true;
            }
        });

        verificaIdLista();
        atualizaListaDeCompras();
        atualizaValorTotal();

        listaCompras.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        boolean consumed = (actionMode == null);

        if(consumed){
            iniciarActionMode();
            listaCompras.setItemChecked(position, true);
            atualizarItensMarcados(listaCompras, position);
        }
        return consumed;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(retornoScanFalse){
            ListaDialogProduto dlgNomeProduto = new ListaDialogProduto();
            dlgNomeProduto.show(getSupportFragmentManager(), "dlgnomeProduto");
            retornoScanFalse = false;
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bEscanear:
                veioDoBotao = true;
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            case R.id.bAdd:
                IncluiCompraLista();
                break;
        }
    }

    private void atualizarItensMarcados(ListView l, int position) {
        l.setItemChecked(position, l.isItemChecked(position));
        //atualizarTitulo();

        /*if(l.getCheckedItemCount() == 1)
            for(int i = 0; i < listaCompras.getCount(); i++) {
                if(l.isItemChecked(i))
                    produ = (Produto) l.getItemAtPosition(i);
            }*/
    }

    private void iniciarActionMode(){
        actionMode = startSupportActionMode(this);
        listaCompras.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void verificaIdLista(){
        Intent it = getIntent();
        if (it.hasExtra("id_lista")){
            lista.setId(((long) Integer.parseInt(it.getStringExtra("id_lista"))));
            lista.setDescricao(it.getStringExtra("descricao_lista"));
        }
    }

    private void atualizaListaDeCompras(){
        lista_de_produtos = new Banco(session).carregaCompras(Integer.valueOf(lista.getId().intValue()));
        listaCompras.setAdapter(new CompraAdapter(lista_de_produtos, this, session));
    }

    public void atualizaValorTotal(){
        valorTotalCompra = 0.0;
        for(Lista_de_produtos l:lista_de_produtos){
            valorTotalCompra = valorTotalCompra + l.getValor()*l.getQuantidade();
        }
        tvValorTotal.setText(valorTotalCompra.toString());
    }

    public void IncluiCompraLista(){
        if(etProduto.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherDescricao, Toast.LENGTH_SHORT).show();
            return;
        }

        if(etQuantidade.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.geraProdutoPreencherQuantidade, Toast.LENGTH_SHORT).show();
            return;
        }

        Lista_de_produtos produto  = new Lista_de_produtos();
        if(codEscaneado != 0){
            produto.setCod_barras(codEscaneado);
            produto.setListaId(lista.getId());
            produto.setSituacaoId((long) 3);
            produto.setQuantidade(Float.parseFloat(etQuantidade.getText().toString()));
            if(etValor.getText().toString().isEmpty()){
                produto.setValor((float) 0);
            }else{
                produto.setValor(Float.parseFloat(etValor.getText().toString()));
            }
            produto.setRecente(true);
        }else{
            //Insere o produto com cod de barras negativo
            Produto prod = new Produto();
            prod.setCod_barras(banco.verificaMenorCodBarras());
            prod.setDescricao_usuario(etProduto.getText().toString());
            ProdutoDao produtoDao = session.getProdutoDao();
            produtoDao.insert(prod);

            produto.setCod_barras(prod.getCod_barras());
            produto.setListaId(lista.getId());
            produto.setSituacaoId((long) 3);
            produto.setQuantidade(Float.parseFloat(etQuantidade.getText().toString()));
            if(etValor.getText().toString().isEmpty()){
                produto.setValor((float) 0);
            }else{
                produto.setValor(Float.parseFloat(etValor.getText().toString()));
            }
            produto.setRecente(true);
        }

        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
        comprasDao.insert(produto);
        atualizaListaDeCompras();
        atualizaValorTotal();

        codEscaneado = (long) 0;
        etProduto.setText("");
        etQuantidade.setText("");
        etValor.setText("");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(veioDoBotao){
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
        }else{
            if (scanningResult != null) {
                String scanContent = scanningResult.getContents();
                codBarrasNew = Long.parseLong(scanContent);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Nenhum dado foi recebido!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        veioDoBotao = false;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listas_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch(item.getItemId()){
            case R.id.acao_delete:
                /*SparseBooleanArray checked = lvListas.getCheckedItemPositions();

                ExcluirDialogLista dlgExcluirListas = new ExcluirDialogLista();
                dlgExcluirListas.setMensagem(getResources().getQuantityString(R.plurals.listas_selecionados, checked.size(), checked.size()));
                dlgExcluirListas.show(getSupportFragmentManager(), "dlgExcluirLista");*/
                break;
            case R.id.acao_edit:
                /*EditarDialogLista dlgEditarListas = new EditarDialogLista();
                dlgEditarListas.show(getSupportFragmentManager(), "dlgEditarLista");*/
                break;
            case R.id.acao_duplicar:
                /*DuplicarDialogLista dlgDuplicarListas = new DuplicarDialogLista();
                dlgDuplicarListas.show(getSupportFragmentManager(), "dlgDuplicarLista");*/
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        listaCompras.clearChoices();
        listaCompras.setChoiceMode(ListView.CHOICE_MODE_NONE);
        atualizaListaDeCompras();
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
                    produto.setDescricao_usuario(input.getText().toString());
                    produto.setCod_barras(compraActivity.codEscaneado);
                    produto.setRecente(true);

                    ProdutoDao produtosDao = compraActivity.session.getProdutoDao();
                    produtosDao.insert(produto);
                    compraActivity.etProduto.setText(produto.getDescricao_usuario());

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
    }
}