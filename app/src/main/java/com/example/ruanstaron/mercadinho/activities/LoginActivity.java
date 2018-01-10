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
import com.example.ruanstaron.mercadinho.WsClient;

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

                Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                        .baseUrl(WsClient.SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = retrofitBuilder.build();
                final WsClient wsclient = retrofit.create(WsClient.class);

                String base = edtEmail.getText().toString() + ":" + edtSenha.getText().toString();
                String authHeader = WsClient.BASIC_AUTH + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                Call<Boolean> call = wsclient.usuarioLogin(authHeader);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful() && response.body())
                            Toast.makeText(getApplicationContext(), "Autorizado", Toast.LENGTH_SHORT).show(); // mudar
                        else
                            Toast.makeText(getApplicationContext(), WsClient.LOGIN_FALHA, Toast.LENGTH_SHORT).show();
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
