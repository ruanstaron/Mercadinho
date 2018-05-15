package tech.pucci.mercadinho.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import tech.pucci.mercadinho.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tech.pucci.mercadinho.Banco;
import tech.pucci.mercadinho.Cupom;
import tech.pucci.mercadinho.adapters.ConferenciaAdapter;
import tech.pucci.mercadinho.db.DaoMaster;
import tech.pucci.mercadinho.db.DaoSession;
import tech.pucci.mercadinho.db.ListaDao;
import tech.pucci.mercadinho.db.Lista_de_produtos;
import tech.pucci.mercadinho.db.Lista_de_produtosDao;
import tech.zxing.android.IntentIntegrator;
import tech.zxing.android.IntentResult;

public class ConferenciaActivity extends AppCompatActivity {

    private Button    btnConferir;
    private ListView  lvResultados;
    private ImageView imgMsgConferencia;
    private TextView  tvMsgConferencia;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private ListaDao listaDao;
    private Banco banco;

    private long idLista;
    private Lista_de_produtos itemCompraSelecionada;

    private List<Lista_de_produtos> comprasSelecionadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferencia);

        idLista =  getIntent().getLongExtra("id_lista", -1);

        inicializarComponentes();
        inicializarBanco();

        comprasSelecionadas = banco.carregaCompras((int)idLista);

        atualizarListViewCompras();
    }

   @Override
    protected void onResume() {
        super.onResume();

        atualizarListViewCompras();
    }

    private void inicializarComponentes(){
        tvMsgConferencia  = ((TextView) findViewById(R.id.tvMsgConferencia));
        imgMsgConferencia = ((ImageView) findViewById(R.id.imgMsgConferencia));

        lvResultados      = ((ListView) findViewById(R.id.lvResultados));

        btnConferir = ((Button) findViewById(R.id.btnConferir));
        btnConferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(ConferenciaActivity.this);
                scanIntegrator.setTitle("Escanear Cupom Fiscal");
                scanIntegrator.setMessage("Escaneie o cupom fiscal da compra");
                scanIntegrator.initiateScan();
            }
        });

        implementarListview();
    }

    private void inicializarBanco(){
        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
    }

    private void atualizarListViewCompras(){
        lvResultados.setVisibility(banco.isListaConferida((int) idLista) ? View.VISIBLE : View.INVISIBLE);

        List<Lista_de_produtos> compras = new Banco(session).carregaCompras((int) idLista);
        lvResultados.setAdapter(new ConferenciaAdapter(this, compras));

       btnConferir.setVisibility(lvResultados.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
       tvMsgConferencia.setVisibility(btnConferir.getVisibility());
       imgMsgConferencia.setVisibility(btnConferir.getVisibility());
    }

    private void implementarListview(){
        lvResultados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 itemCompraSelecionada = ((Lista_de_produtos) lvResultados.getItemAtPosition(position));

                if(itemCompraSelecionada.getSituacaoId() == Lista_de_produtos.DIVERGENCIA){
                    CorrigirCompraDivergenteConferencia dlgCorrigirDivergencia = new CorrigirCompraDivergenteConferencia();
                    dlgCorrigirDivergencia.show(getSupportFragmentManager(), "dlgCorrigirDivergencia");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        try {
            if (scanningResult != null && scanningResult.getContents() == null)
                return;

            URL urlSEFAZ  = new URL(scanningResult.getContents());

            // Variaveis temporarias para evitar o erro decorrente da comparação de longs. TODO: PESQUISAR E TESTAR
            String temp1;
            String temp2;

            Cupom cupom = new Cupom(master);

            List<Lista_de_produtos> compras = cupom.getProdutosDoCupomFiscal(String.valueOf(urlSEFAZ));
            compras = filtrarCompras(compras);

            for (Lista_de_produtos compra : compras) {
                for(Lista_de_produtos compraSelecionada: comprasSelecionadas){
                    compraSelecionada.setSituacaoId((long) Lista_de_produtos.DIVERGENCIA);

                    temp1 = String.valueOf(compra.getCod_barras());
                    temp2 = String.valueOf(compraSelecionada.getCod_barras());

                    if(temp1.equals(temp2)){
                        compraSelecionada.setValor_nota(compra.getValor());

                        if(Float.compare(compra.getValor(), compraSelecionada.getValor()) == 0)
                            compraSelecionada.setSituacaoId((long)Lista_de_produtos.OK);

                        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();
                        comprasDao.update(compraSelecionada);

                        //comprasSelecionadas.remove(compraSelecionada); ver isso aqui, ajuda a ficar mais rapido caso funcione
                        break;
                    }
                }
            }

            atualizarListViewCompras();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro no escaneamento", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Lista_de_produtos> filtrarCompras(List<Lista_de_produtos> compras){
        Set<Lista_de_produtos> comprasFiltradas = new HashSet<>(compras);

        return new ArrayList<>(comprasFiltradas);
    }

    private void editarBancoCompra(DaoSession session, Lista_de_produtos compra){
        Lista_de_produtosDao comprasDao = session.getLista_de_produtosDao();

        if(compra.getValor().equals(compra.getValor_nota())){
            compra.setSituacaoId((long) Lista_de_produtos.OK);
        }

        comprasDao.update(compra);
    }


    public static class CorrigirCompraDivergenteConferencia extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

            final ConferenciaActivity atvConferencia = (ConferenciaActivity) getActivity();
            input.setText(atvConferencia.itemCompraSelecionada.getValor_nota().toString());

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!input.getText().toString().isEmpty()){
                        atvConferencia.itemCompraSelecionada.setValor_nota(Float.valueOf(input.getText().toString()));
                        atvConferencia.editarBancoCompra(atvConferencia.session, atvConferencia.itemCompraSelecionada);

                        dismiss();
                        atvConferencia.atualizarListViewCompras();
                    }
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setView(input)
                    .setTitle(R.string.dialogoConfirmaCorrigirCompraTitulo)
                    .setMessage(R.string.dialogoCorrigirCompraMsg)
                    .setPositiveButton(R.string.ok, listener)
                    .create();

            return dialog;
        }
    }
}
