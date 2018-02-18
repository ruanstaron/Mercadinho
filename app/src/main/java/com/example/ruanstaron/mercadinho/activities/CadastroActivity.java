package com.example.ruanstaron.mercadinho.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.Banco;
import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.RetornoWS;
import com.example.ruanstaron.mercadinho.Validacao;
import com.example.ruanstaron.mercadinho.WebService;
import com.example.ruanstaron.mercadinho.WsClient;
import com.example.ruanstaron.mercadinho.db.Cidade;
import com.example.ruanstaron.mercadinho.db.DaoMaster;
import com.example.ruanstaron.mercadinho.db.DaoSession;
import com.example.ruanstaron.mercadinho.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtNome;
    private Button   btnCadastrar;
    private Spinner  spCidades;

    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private Banco banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        instanciarBanco();
        instanciarComponentes();
    }

    private void instanciarComponentes(){
        edtEmail  = ((EditText) findViewById(R.id.edtCadastroEmail));
        edtSenha  = ((EditText) findViewById(R.id.edtCadastroSenha));
        edtNome   = ((EditText) findViewById(R.id.edtCadastroNome));
        instanciarBotaoCadastrar();
        instanciarSpinnerCidades();
    }

    private void instanciarBanco(){
        helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "mercadinho-db");
        master = new DaoMaster(helper.getWritableDatabase());
        session = master.newSession();
        banco = new Banco(session);
    }

    private void instanciarSpinnerCidades(){
        spCidades = ((Spinner) findViewById(R.id.spCidade));
        ArrayAdapter cidadeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, banco.selectCidadesTodas());
        spCidades.setAdapter(cidadeAdapter);
    }

    private void instanciarBotaoCadastrar(){
        btnCadastrar = ((Button) findViewById(R.id.btnCadastrar));

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!verificarCamposObrigatorios())
                    return;

                if(!Validacao.verificarEmail(edtEmail.getText().toString())){
                    edtEmail.setError(getText(R.string.email_invalido));
                    return;
                }

                if(!Validacao.verificarCamposObrigatorios(spCidades, "Preencha a Cidade!", getApplicationContext()))
                    return;

                final Cidade cidade = (Cidade) ((Spinner) findViewById(R.id.spCidade)).getSelectedItem();

                Usuario usuario = new Usuario(
                        edtEmail.getText().toString(),
                        edtSenha.getText().toString(),
                        edtNome.getText().toString(),
                        cidade.getId()
                );

                Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                        .baseUrl(WsClient.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = retrofitBuilder.build();
                WsClient wsclient = retrofit.create(WsClient.class);

                Call<RetornoWS> call = wsclient.usuarioSignup(usuario);

                call.enqueue(new Callback<RetornoWS>() {
                    @Override
                    public void onResponse(Call<RetornoWS> call, Response<RetornoWS> response) {
                        if(response.isSuccessful())
                            switch (response.body().getCod_msg()) {
                                case "-1":
                                    WebService webService = new WebService(getApplicationContext());
                                    webService.getMercados(cidade);

                                    Toast.makeText(getApplicationContext(), R.string.signup_sucesso, Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;
                                case "23505":
                                    Toast.makeText(getApplicationContext(), R.string.signup_usuario_duplicado, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), R.string.erro_generico, Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<RetornoWS> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean verificarCamposObrigatorios(){
        if(Validacao.verificarCamposObrigatorios(edtEmail, "Preencha o e-mail!") &&
           Validacao.verificarCamposObrigatorios(edtSenha, "Preencha a senha")   &&
           Validacao.verificarCamposObrigatorios(edtNome,  "Preencha o Nome!")){
            return true;
        }
        else
            return false;
    }
}
