package senac.com.br.cademeulivro.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.dao.DatabaseHelper;
import senac.com.br.cademeulivro.dao.ObraCursorWrapper;
import senac.com.br.cademeulivro.model.Obra;


public class MainActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private SQLiteDatabase mDatabase;
    private ListView listView;
    private AdapterListView adapterListView;
    private List<Obra> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        listView = (ListView) findViewById(R.id.ListViewColecao);
        createListView();
    }


    public void createListView(){
        itens = getListaObras();
        adapterListView=new AdapterListView(this,itens);
        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.CYAN);

    }
    private List<Obra> getListaObras() {
        Cursor cursor = mDatabase.query("Obra",null, null, null, null, null, null);
        ObraCursorWrapper wrapper = new ObraCursorWrapper(cursor);
        List<Obra> lista = new ArrayList<>();

        try {
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()) {
                lista.add(wrapper.getObra());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return lista;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void cadastrarLivro(View v) {
        Intent intent = new Intent(this,ObraCadastroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapterListView == null) {
            adapterListView = new AdapterListView(this,getListaObras());
            listView.setAdapter(adapterListView);
        } else {
            adapterListView.setItens(getListaObras());
            adapterListView.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // instanciando a opção de logout do menu
            case R.id.action_settings:
                // invocando o método de log out
                signOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    //
    private void signOut() {
        // o primeiro passo é checar se o usuário fez login pelo google ou pelo facebook
        // se a instance da sessão do google estiver rodando,ele vai ir para esse bloco
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            // invocamos o signOut para finalizar a sessão
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(getApplicationContext(),"Log out bem suscedido",Toast.LENGTH_SHORT).show();
                            // levamos o usuário de volta a tela de login
                            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                        }
                    });
            // caso o token do google não exista, então o usuario só pode ter feito login pelo facebook
        } else {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken != null){
                // invocamos o logOut para finalizar a sessão
                LoginManager.getInstance().logOut();
            }
            Toast.makeText(getApplicationContext(),"Log out bem suscedido",Toast.LENGTH_SHORT).show();
            // levamos o usuário para a tela de login
            Intent i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }

    }

    // implementação necessária para mais tarde - esse método remove o usuário da base de tokens com acesso ao plug-in
    // e remove as permissões do aplicativo da conta do usuário
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(),"Permissão revogada",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}