package com.example.ruanstaron.mercadinho;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ruanstaron.mercadinho.adapter.ListaAdapter;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;

import java.util.List;

public class ListaActivity extends AppCompatActivity implements ActionMode.Callback, AdapterView.OnItemLongClickListener {

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private ListaDao listaDao;

    private ListView lvListas;
    private FloatingActionButton fabAddLista;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lvListas = ((ListView) findViewById(R.id.lvListas));

        lvListas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode == null){
                    Lista lista = (Lista)parent.getItemAtPosition(position);

                    Intent itScan = new Intent(getApplicationContext(), Scan.class);
                    itScan.putExtra("id_lista", lista.getId().toString());
                    itScan.putExtra("descricao_lista", lista.getDescricao());
                    startActivity(itScan);
                }else{
                    atualizarItensMarcados(lvListas, position);

                    if(qtdeItensMarcados() == 0){
                        actionMode.finish();
                    }
                }
            }
        });

        fabAddLista = ((FloatingActionButton) findViewById(R.id.fabAddLista));

        fabAddLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaDialogLista dlgNomeLista = new ListaDialogLista();
                dlgNomeLista.show(getSupportFragmentManager(), "dlgNomemLista");
            }
        });

        lvListas.setOnItemLongClickListener(this);

        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();

        atualizaListListas();
        lvListas.setEmptyView((findViewById(R.id.imgListasVazio)));
    }

    private void atualizaListListas(){
        List<Lista> lListas = new Banco(session).carregaListas();
        lvListas.setAdapter(new ListaAdapter(this, lListas));
    }

    public void IncluirBancoLista(DaoSession session, Lista lista){
        listaDao = session.getListaDao();
        listaDao.insert(lista);
    }

    private void iniciarModoExclusao(){
        actionMode = startSupportActionMode(this);
        lvListas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void atualizarTitulo(){
        int checkedCount = qtdeItensMarcados();

        String selecionados = getResources().getQuantityString(R.plurals.numero_selecionados, checkedCount, checkedCount);
        actionMode.setTitle(selecionados);
    }

    private int qtdeItensMarcados(){
        SparseBooleanArray checked = lvListas.getCheckedItemPositions();
        int checkedCount = 0;

        for(int i = 0; i < checked.size(); i++){
            if(checked.valueAt(i)){
                checkedCount++;
            }
        }

        return checkedCount;
    }

    private void atualizarItensMarcados(ListView l, int position) {
        l.setItemChecked(position, l.isItemChecked(position));
        atualizarTitulo();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        boolean consumed = (actionMode == null);

        if(consumed){
            iniciarModoExclusao();
            lvListas.setItemChecked(position, true);
            atualizarItensMarcados(lvListas, position);
        }

        return consumed;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public void excluirListasSelecionadas(){
        SparseBooleanArray checked = lvListas.getCheckedItemPositions();

        for(int i = checked.size() - 1; i >= 0; i--){
            if (checked.valueAt(i)){
                Lista lista = (Lista)lvListas.getItemAtPosition(checked.keyAt(i));

                Banco banco = new Banco(session);
                banco.excluiListas(lista.getId());
            }
        }

        actionMode.finish();
        atualizaListListas();
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if(item.getItemId() == R.id.acao_delete) {
            SparseBooleanArray checked = lvListas.getCheckedItemPositions();

            ExcluirDialogLista dlgExcluirListas = new ExcluirDialogLista();
            dlgExcluirListas.setMensagem(getResources().getQuantityString(R.plurals.listas_selecionados, checked.size(), checked.size()));
            dlgExcluirListas.show(getSupportFragmentManager(), "dlgExcluirLista");
        }

        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        lvListas.clearChoices();
        lvListas.setChoiceMode(ListView.CHOICE_MODE_NONE);
        atualizaListListas();
    }

    public static class ListaDialogLista extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final EditText input = new EditText(getActivity());
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ListaActivity atvLista = (ListaActivity)getActivity();
                    Lista lista = new Lista();

                    if(input.getText().toString().isEmpty())
                        input.setText("Nova Lista");

                    lista.setDescricao(input.getText().toString());
                    atvLista.IncluirBancoLista(atvLista.session, lista);
                    dismiss();
                    atvLista.atualizaListListas();
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

    public static class ExcluirDialogLista extends DialogFragment {

        private String mensagem;

        public void setMensagem(String mensagem){
            this.mensagem = mensagem;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int button){
                    if(button == DialogInterface.BUTTON_POSITIVE){
                        ListaActivity actvListas = ((ListaActivity) getActivity());
                        actvListas.excluirListasSelecionadas();
                        dismiss();
                    }
                }
            };

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialogoConfirmaExclusaoListaTitulo)
                    .setMessage(mensagem)
                    .setPositiveButton(R.string.sim, listener)
                    .setNegativeButton(R.string.nao, null)
                    .create();
            return dialog;
        }
    }
}
