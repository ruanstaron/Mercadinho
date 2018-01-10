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

                Call<Boolean> call = wsclient.usuarioSignup(usuario);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful() && response.body())
                            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), WsClient.SIGNUP_FALHA, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
