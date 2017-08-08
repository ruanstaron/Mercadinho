package com.example.ruanstaron.mercadinho;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.adapter.ListaAdapter;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.db.Lista;
import com.example.ruanstaron.mercadinho.db.ListaDao;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private ListaDao listaDao;

    private ListView lvListas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lvListas = ((ListView) findViewById(R.id.lvListas));

        lvListas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lista lista = (Lista)parent.getItemAtPosition(position);

                Intent itScan = new Intent(getApplicationContext(), Scan.class);
                itScan.putExtra("id_lista", lista.getId().toString());
                itScan.putExtra("descricao_lista", lista.getDescricao());
                startActivity(itScan);
            }
        });

        helper = new DaoMaster.DevOpenHelper(this, "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();

        AtualizaListListas();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                ListaDialogLista dlgNomeLista = new ListaDialogLista();
                dlgNomeLista.show(getSupportFragmentManager(), "dlgNomemLista");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void AtualizaListListas(){
        List<Lista> lListas = new Banco(session).carregaListas();
        lvListas.setAdapter(new ListaAdapter(this, lListas));
    }

    public void IncluirBancoLista(DaoSession session, Lista lista){
        listaDao = session.getListaDao();
        listaDao.insert(lista);
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
                    lista.setDescricao(input.getText().toString());
                    atvLista.IncluirBancoLista(atvLista.session, lista);
                    dismiss();
                    atvLista.AtualizaListListas();
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
