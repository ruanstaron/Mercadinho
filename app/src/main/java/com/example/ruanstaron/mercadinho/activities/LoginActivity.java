package com.example.ruanstaron.mercadinho.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ruanstaron.mercadinho.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instanciarBtnCadastrar();
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
}
