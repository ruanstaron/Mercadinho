package tech.pucci.mercadinho.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.pucci.mercadinho.R;
import tech.pucci.mercadinho.WebService;
import tech.pucci.mercadinho.WsClient;
import tech.pucci.mercadinho.activities.ListaActivity;
import tech.pucci.mercadinho.activities.LoginActivity;
import tech.pucci.mercadinho.activities.SplashActivity;
import tech.pucci.mercadinho.model.Usuario;

/**
 * Created by joaop on 2/28/2018.
 */

public class LoginTask extends AsyncTask<String, Integer, Boolean> {

    private WeakReference<Context> contextRef;
    private String username;
    private String senha;

    public LoginTask(Context context) {
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        if(contextRef.get() instanceof LoginActivity){
            ProgressBar pbLogin = ((ProgressBar) ((LoginActivity) contextRef.get()).findViewById(R.id.pbLogin));
            pbLogin.setVisibility(View.VISIBLE);
            ((LoginActivity) contextRef.get()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            RelativeLayout llLoginActvDisable = ((RelativeLayout) ((LoginActivity) contextRef.get()).findViewById(R.id.llLoginActvDisable));
            llLoginActvDisable.setVisibility(View.VISIBLE);

            Button btnLogin = ((Button) ((LoginActivity) contextRef.get()).findViewById(R.id.btnLogin));
            btnLogin.setEnabled(false);
            Button btnCadastrar = ((Button) ((LoginActivity) contextRef.get()).findViewById(R.id.btnCadastrar));
            btnCadastrar.setEnabled(false);
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        final String LOGIN_INVALIDO           = "-2";
        final String LOGIN_SUCESSO            = null;
        final int    STRINGS_USUARIO_USERNAME = 0;
        final int    STRINGS_USUARIO_SENHA    = 1;


        boolean sucesso = false;

        username = strings[STRINGS_USUARIO_USERNAME];
        senha    = strings[STRINGS_USUARIO_SENHA];

        String base = username + ":" + senha;
        String authHeader = WsClient.BASIC_AUTH + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(WsClient.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        final WsClient wsclient = retrofit.create(WsClient.class);

        Call<List<Usuario>> call = wsclient.usuarioLogin(authHeader);

        try {
            Response<List<Usuario>> result = call.execute();

            if(result.isSuccessful() && result.body().get(0).getCod_msg() == LOGIN_SUCESSO){
                sucesso = true;
            }
            else if(result.body().get(0).getCod_msg().equals(LOGIN_INVALIDO)){
                sucesso = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            sucesso = false;
        }

        return sucesso;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean b) {
        Context context = contextRef.get();

        if(b && context instanceof LoginActivity){
            SharedPreferences prefs = context.getSharedPreferences(WebService.PREFS_USUARIO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(WebService.PREFS_USUARIO_USERNAME, username);
            editor.putString(WebService.PREFS_USUARIO_SENHA, senha);
            editor.apply();

            Intent itListas = new Intent(context, ListaActivity.class);
            context.startActivity(itListas);

            ((LoginActivity) context).finish();

            ProgressBar pbLogin = ((ProgressBar) ((LoginActivity) contextRef.get()).findViewById(R.id.pbLogin));
            pbLogin.setVisibility(View.GONE);

        }
        else if(!b && context instanceof LoginActivity){
            ProgressBar pbLogin = ((ProgressBar) ((LoginActivity) contextRef.get()).findViewById(R.id.pbLogin));
            pbLogin.setVisibility(View.INVISIBLE);
            ((LoginActivity)context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            RelativeLayout llLoginActvDisable = ((RelativeLayout) ((LoginActivity) contextRef.get()).findViewById(R.id.llLoginActvDisable));
            llLoginActvDisable.setVisibility(View.GONE);

            Button btnLogin = ((Button) ((LoginActivity) contextRef.get()).findViewById(R.id.btnLogin));
            btnLogin.setEnabled(true);
            Button btnCadastrar = ((Button) ((LoginActivity) contextRef.get()).findViewById(R.id.btnCadastrar));
            btnCadastrar.setEnabled(true);

            Toast.makeText(context, "Usuário/Senha inválidos", Toast.LENGTH_SHORT).show();
        }
        else if(b && context instanceof SplashActivity){
            Intent itListas = new Intent(context, ListaActivity.class);
            context.startActivity(itListas);

            ((SplashActivity) context).finish();
        }
        else if(!b && context instanceof  SplashActivity){
            Intent itLogin = new Intent(context, LoginActivity.class);
            context.startActivity(itLogin);

            Toast.makeText(context, "Usuário/Senha inválidos", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Erro desconhecido", Toast.LENGTH_SHORT).show();
        }
    }
}