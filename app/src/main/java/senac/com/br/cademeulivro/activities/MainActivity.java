package senac.com.br.cademeulivro.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import senac.com.br.cademeulivro.R;


public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListViewColecao);
        createListView();


    }


    public void createListView(){

        itens=new ArrayList<ItemListView>();

        ItemListView item1=new ItemListView("Manual do Advogado","Valdemar P. da Luz",R.drawable.capa);

        itens.add(item1);
        itens.add(item1);
        adapterListView=new AdapterListView(this,itens);
        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.CYAN);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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