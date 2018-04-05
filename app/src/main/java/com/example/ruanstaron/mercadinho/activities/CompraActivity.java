package com.example.ruanstaron.mercadinho.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
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
import com.example.ruanstaron.mercadinho.db.Produto;
import com.example.ruanstaron.mercadinho.db.ProdutoDao;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class CompraActivity extends AppCompatActivity implements ActionMode.Callback, OnClickListener, AdapterView.OnItemLongClickListener, PopupMenu.OnMenuItemClickListener {

    private Lista lista = new Lista();
    private boolean                 retornoScanFalse = false;
    private DaoMaster.DevOpenHelper helper;
    private static DaoMaster        master;
    private DaoSession              session;
    private Banco            banco;
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
    private long                    codBarrasNew;
    public static long              codBarrasOld;
    public static long              idListaCompras;
    public static boolean           valorProdutoZerado = false;

    private Button                  finalizar;

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

        finalizar = (Button) findViewById(R.id.bFinalizar);

        helper  = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master  = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);

        scan.setOnClickListener(this);
        add.setOnClickListener(this);
        finalizar.setOnClickListener(this);

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

        listaCompras.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        boolean consumed = (actionMode == null);

        if(consumed){
            iniciarActionMode();
            listaCompras.setItemChecked(position, true);
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
        if (valorProdutoZerado){
            ListaDialogValorProduto dlgNomeValorProduto = new ListaDialogValorProduto();
            dlgNomeValorProduto.show(getSupportFragmentManager(), "dlgNomeValorProduto");
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
            case R.id.bFinalizar:
                LerQrCode();
                break;
        }
    }

    private void iniciarActionMode(){
        actionMode = startSupportActionMode(this);
        listaCompras.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void verificaIdLista(){
        Intent it = getIntent();
        if (it.hasExtra("id_lista")){
            lista.setId(((long) Integer.parseInt(it.getStringExtra("id_lista"))));
            lista.setDescricao(it.getStringExtra("descricao_lista"));
        }
    }

    private void atualizaListaDeCompras(){
        lista_de_produtos = new Banco(master.newSession()).carregaCompras(lista.getId().intValue());
        listaCompras.setAdapter(new CompraAdapter(lista_de_produtos, this, master, this));
        atualizaValorTotal();
    }

    public void atualizaValorTotal(){
        valorTotalCompra = 0.0;
        for(Lista_de_produtos l:lista_de_produtos){
            valorTotalCompra = valorTotalCompra + l.getValor()*l.getQuantidade();
        }
        String resultado = String.format("%.2f", valorTotalCompra);
        tvValorTotal.setText(resultado);
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

        banco.insereProdutoNaLista(produto);
        atualizaListaDeCompras();

        codEscaneado = (long) 0;
        etProduto.setText("");
        etQuantidade.setText("");
        etValor.setText("");
    }

    public void excluirProdutoSelecionado(){
        Lista_de_produtos lista_de_produtos2 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);

        banco.excluiProdutoDaLista(lista_de_produtos2.getId());

        actionMode.finish();
        atualizaListaDeCompras();
    }

    public void duplicarProdutoSelecionado(){
        Lista_de_produtos lista_de_produtos3 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);

        banco.insereProdutoNaLista(lista_de_produtos3);

        actionMode.finish();
        atualizaListaDeCompras();
    }

    public String getDescricaoProdutoNoDialog(){
        Lista_de_produtos lista_de_produtos4 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        return banco.getProdutoDescricao(lista_de_produtos4.getCod_barras());
    }

    public void setDescricaoProdutoNoDialog(String descricao){
        Lista_de_produtos lista_de_produtos5 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        banco.atualizaDescricaoProduto(lista_de_produtos5.getCod_barras(), descricao);
        actionMode.finish();
        atualizaListaDeCompras();
    }

    public float getQuantidadeProdutoDialog(){
        Lista_de_produtos lista_de_produtos6 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        return lista_de_produtos6.getQuantidade();
    }

    public void setQuantidadeProdutoDialog(float quantidade){
        Lista_de_produtos lista_de_produtos7 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        banco.atualizaQuantidadeProduto(lista_de_produtos7.getId(), quantidade);
        actionMode.finish();
        atualizaListaDeCompras();
    }

    public float getValorProdutoDialog(){
        Lista_de_produtos lista_de_produtos7 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        return lista_de_produtos7.getValor();
    }

    public void setValorProdutoDialog(float valor){
        Lista_de_produtos lista_de_produtos8 = (Lista_de_produtos) listaCompras.getItemAtPosition(0);
        banco.atualizaValorProduto(lista_de_produtos8.getId(), valor);
        actionMode.finish();
        atualizaListaDeCompras();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null && scanningResult.getContents() == null)
            return;

        if(veioDoBotao){
            String scanContent = scanningResult.getContents();
            codEscaneado = Long.parseLong(scanContent);

            if (banco.getProdutoDescricao(Long.parseLong(scanContent)) != "")
                etProduto.setText(banco.getProdutoDescricao(Long.parseLong(scanContent)));
            else
                retornoScanFalse = true;
        }else{
            String scanContent = scanningResult.getContents();
            codBarrasNew = Long.parseLong(scanContent);
            banco.atualizaCodBarras(idListaCompras,codBarrasOld,codBarrasNew);
            banco.atualizaProdutoComprado(idListaCompras, 4);
            codBarrasOld = 0;
            codBarrasNew = 0;
            codEscaneado = 0;

            atualizaListaDeCompras();
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
                ExcluirDialogProduto dlgExcluirProduto = new ExcluirDialogProduto();
                dlgExcluirProduto.show(getSupportFragmentManager(), "dlgExcluirProduto");
                break;
            case R.id.acao_edit:
                View menuItemView = findViewById(R.id.acao_edit);
                showMenuEditarProduto(menuItemView);
                break;
            case R.id.acao_duplicar:
                DuplicarDialogProduto dlgDuplicarProduto = new DuplicarDialogProduto();
                dlgDuplicarProduto.show(getSupportFragmentManager(), "dlgDuplicarProduto");
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

    public void showMenuEditarProduto(View v) {
        PopupMenu popup = new PopupMenu(this, v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_editar_produto);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.descricao:
                ListaDialogDescricaoProduto dlgEditarDescricaoProduto = new ListaDialogDescricaoProduto();
                dlgEditarDescricaoProduto.show(getSupportFragmentManager(), "dlgEditarDescricaoProduto");
                return true;
            case R.id.quantidade:
                ListaDialogQuantidadeProduto dlgEditarQuantidadeProduto = new ListaDialogQuantidadeProduto();
                dlgEditarQuantidadeProduto.show(getSupportFragmentManager(), "dlgEditarQuantidadeProduto");
                return true;
            case R.id.valorUnitario:
                ValorProdutoDialog dlgEditarValorProduto = new ValorProdutoDialog();
                dlgEditarValorProduto.show(getSupportFragmentManager(), "dlgEditarValorProduto");
                return true;
            default:
                return false;
        }
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

    public static class ListaDialogValorProduto extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CompraActivity compraActivity = (CompraActivity)getActivity();
                    new Banco(master.newSession()).atualizaValorProduto(compraActivity.idListaCompras, Float.parseFloat(input.getText().toString()));
                    valorProdutoZerado = false;
                    compraActivity.atualizaListaDeCompras();
                    dismiss();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoValorProdutoTitulo)
                    .setMessage(R.string.dialogoValorProdutoMsg)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }

    public static class ExcluirDialogProduto extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int button){
                    if(button == DialogInterface.BUTTON_POSITIVE){
                        CompraActivity compraActivity = ((CompraActivity) getActivity());
                        compraActivity.excluirProdutoSelecionado();
                        dismiss();
                    }
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialogoConfirmaExclusaoProdutoTitulo)
                    .setMessage(R.string.dialogoExcluirProdutoMsg)
                    .setPositiveButton(R.string.sim, listener)
                    .setNegativeButton(R.string.nao, null)
                    .create();
            return dialog;
        }
    }

    public static class DuplicarDialogProduto extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int button){
                    if(button == DialogInterface.BUTTON_POSITIVE){
                        CompraActivity actvCompra = ((CompraActivity) getActivity());
                        actvCompra.duplicarProdutoSelecionado();
                        dismiss();
                    }
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialogoConfirmaDuplicarProdutoTitulo)
                    .setMessage(R.string.dialogoDuplicarProdutoMsg)
                    .setPositiveButton(R.string.sim, listener)
                    .setNegativeButton(R.string.nao, null)
                    .create();
            return dialog;
        }
    }

    public static class ListaDialogDescricaoProduto extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            final CompraActivity compraActivity = (CompraActivity)getActivity();
            input.setText(compraActivity.getDescricaoProdutoNoDialog());

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CompraActivity compraActivity = (CompraActivity)getActivity();
                    compraActivity.setDescricaoProdutoNoDialog(input.getText().toString());
                    dismiss();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoDescricaoProdutoTitulo)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }

    public static class ListaDialogQuantidadeProduto extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);

            final CompraActivity compraActivity = (CompraActivity)getActivity();
            input.setText(String.valueOf(compraActivity.getQuantidadeProdutoDialog()));

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CompraActivity compraActivity = (CompraActivity)getActivity();
                    compraActivity.setQuantidadeProdutoDialog(Float.parseFloat(input.getText().toString()));
                    dismiss();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoQuantidadeProdutoTitulo)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }

    public static class ValorProdutoDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            final CompraActivity compraActivity = (CompraActivity)getActivity();
            input.setText(String.valueOf(compraActivity.getValorProdutoDialog()));

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CompraActivity compraActivity = (CompraActivity)getActivity();
                    compraActivity.setValorProdutoDialog(Float.parseFloat(input.getText().toString()));
                    dismiss();
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoValorProdutoTitulo)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }

    public void LerQrCode(){
        Intent intentConf = new Intent(this, ConferenciaActivity.class);
        intentConf.putExtra("id_lista", lista.getId());
        startActivity(intentConf);

      //  List<Lista_de_produtos> compras = banco.carregaCompras(lista.getId());


       /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String qrcode = "http://www.dfeportal.fazenda.pr.gov.br/dfe-portal/rest/servico/consultaNFCe?chNFe=41180376189406003656651140000948921252055427&nVersao=100&tpAmb=1&cDest=08279105921&dhEmi=323031382d30332d32355432303a35353a34322d30333a3030&vNF=21.46&vICMS=0.00&digVal=6a6278736a4b76436c48326a665a4b41576e4b507a57502b4b45593d&cIdToken=000001&cHashQRCode=8BB01F046FD53207E1176D3CAA705B5F3A5742AE";
        Cupom cupom = new Cupom(master);*/
        /*Mercado mercado = new Mercado();
        try {
            mercado = cupom.getMercado(qrcode);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
      /*  List<Lista_de_produtos> listaDeProdutos = new ArrayList<>();
        try {
           listaDeProdutos = cupom.getProdutosDoCupomFiscal(qrcode);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*for (Produto p : produtos){
            System.out.println(p.getDescricao());
        }*/
    }
}