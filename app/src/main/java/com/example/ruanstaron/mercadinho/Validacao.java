package com.example.ruanstaron.mercadinho;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

    public static boolean verificarCamposObrigatorios(Spinner spn, String msg, Context context){
        if(spn.getSelectedItem().toString().trim().isEmpty()){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
