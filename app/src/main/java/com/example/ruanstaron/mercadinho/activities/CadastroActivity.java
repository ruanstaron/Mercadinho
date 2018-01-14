package com.example.ruanstaron.mercadinho.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.WsClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        instanciarComponentes();
    }

    private void instanciarComponentes(){
        edtEmail = ((EditText) findViewById(R.id.edtCadastroEmail));
        edtSenha = ((EditText) findViewById(R.id.edtCadastroSenha));
        edtNome  = ((EditText) findViewById(R.id.edtCadastroNome));
        instanciarBotaoCadastrar();
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

                Usuario usuario = new Usuario(
                        edtEmail.getText().toString(),
                        edtSenha.getText().toString(),
                        edtNome.getText().toString()
                );

                Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                        .baseUrl(WsClient.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = retrofitBuilder.build();
                WsClient wsclient = retrofit.create(WsClient.class);

                Call<Integer> call = wsclient.usuarioSignup(usuario);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful())
                            switch (response.body()) {
                                case 1:
                                    Toast.makeText(getApplicationContext(), R.string.signup_sucesso, Toast.LENGTH_SHORT).show();
                                    break;
                                case 1062:
                                Toast.makeText(getApplicationContext(), R.string.signup_usuario_duplicado, Toast.LENGTH_SHORT).show();
                                break;
                                default:
                                Toast.makeText(getApplicationContext(), R.string.erro_generico, Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
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
