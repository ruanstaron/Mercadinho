package com.example.ruanstaron.mercadinho.activities;

import android.widget.EditText;

/**
 * Created by pucci on 14/01/2018.
 */

public class Validacao {

    public static boolean verificarCamposObrigatorios(EditText edt, String msg){
        if (edt.getText().toString().isEmpty()) {
            edt.setError(msg);
            return false;
        }
        return true;
    }

    public static boolean verificarEmail(String email){
        String[] partes = email.split("@");
        if(partes.length == 2 && !partes[0].isEmpty() && !partes[1].isEmpty())
            return true;
        else
            return false;
    }
}
