package com.example.ruanstaron.mercadinho.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.Validacao;
import com.example.ruanstaron.mercadinho.WsClient;
import com.example.ruanstaron.mercadinho.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button   btnCadastrar;
    private Button   btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instanciarComponentes();
    }

    private void instanciarComponentes(){
        edtEmail = ((EditText) findViewById(R.id.edtLoginEmail));
        edtSenha = ((EditText) findViewById(R.id.edtLoginSenha));
        instanciarBtnCadastrar();
        instanciarBtnLogin();
    }

    private void instanciarBtnCadastrar(){
        btnCadastrar = ((Button) findViewById(R.id.btnCadastrar));

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itCadastro = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(itCadastro);
            }
        });
    }

    private void instanciarBtnLogin(){
        btnLogin = ((Button) findViewById(R.id.btnLogin));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!verificarCamposObrigatorios())
                    return;

                if(!Validacao.verificarEmail(edtEmail.getText().toString())){
                    edtEmail.setError(getText(R.string.email_invalido));
                    return;
                }

                Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                        .baseUrl(WsClient.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = retrofitBuilder.build();
                final WsClient wsclient = retrofit.create(WsClient.class);

                String base = edtEmail.getText().toString() + ":" + edtSenha.getText().toString();
                String authHeader = WsClient.BASIC_AUTH + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                Call<List<Usuario>> call = wsclient.usuarioLogin(authHeader);

                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        List<Usuario> resposta = response.body();
                        if(response.isSuccessful() && resposta.get(0).getCod_msg() == null ){
                            Intent itListas = new Intent(getApplicationContext(), ListaActivity.class);
                            startActivity(itListas);
                            finish();
                        }
                        else if(resposta.get(0).getCod_msg().equals("-2"))
                            Toast.makeText(getApplicationContext(), R.string.login_usuario_incorreto, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean verificarCamposObrigatorios(){
        if(Validacao.verificarCamposObrigatorios(edtEmail, "Preencha o e-mail!") &&
           Validacao.verificarCamposObrigatorios(edtSenha, "Preencha a senha")){
            return true;
        }
        else
            return false;
    }
}
