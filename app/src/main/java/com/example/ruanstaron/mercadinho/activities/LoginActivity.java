package com.example.ruanstaron.mercadinho.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ruanstaron.mercadinho.R;
import com.example.ruanstaron.mercadinho.Validacao;
import com.example.ruanstaron.mercadinho.WebService;

public class LoginActivity extends AppCompatActivity {

    private EditText    edtEmail;
    private EditText    edtSenha;
    private Button      btnCadastrar;
    private Button      btnLogin;

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

                WebService ws = new WebService(LoginActivity.this);
                ws.realizarLogin(edtEmail.getText().toString(), edtSenha.getText().toString());
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
