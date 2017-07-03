package br.com.senac.cademeulivro.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activity.container.CadastroPagerActivity;
import br.com.senac.cademeulivro.activity.container.ContainerEditActivity;
import br.com.senac.cademeulivro.activity.container.ContainerListFragment;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaEditActivity;
import br.com.senac.cademeulivro.activity.resultados.ResultadoPesquisaActivity;
import br.com.senac.cademeulivro.activity.resultados.ResultadoScannerActivity;
import br.com.senac.cademeulivro.activity.tabs.tab_ObrasActivity;
import br.com.senac.cademeulivro.activity.tabs.tab_RecomendadosActivity;
import br.com.senac.cademeulivro.activity.tabs.tab_TagsActivity;
import br.com.senac.cademeulivro.activity.tag.TagEditActivity;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.classes.Scanner;
import br.com.senac.cademeulivro.util.constante.Constantes;

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton fab;
    private Scanner scanner;
    private List<Obra> lista;
    private int tabPosicao=0;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        mViewPager.setCurrentItem(tab.getPosition());
                        tabPosicao=tab.getPosition();
                    }
                });


        String PREFS_NAME = "MyPrefsFile";
        //primeira vez que o app é aberto
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Boolean status = settings.edit().putBoolean("receberNotificacao", true).commit();
            primeiroAcesso();
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }

    public void fabFuncao(View v){

        CharSequence opcoes[];
        Intent intent;

        switch (tabPosicao){

            case 0:
                intent=new Intent(MainActivity.this, ContainerEditActivity.class);
                startActivity(intent);

                break;
            case 1:
                opcoes = new CharSequence[] {"Manualmente", "Escanear ISBN"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Cadastrar");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            Intent intent=new Intent(MainActivity.this, ObraDetalhadaEditActivity.class);
                            startActivity(intent);

                        }else{
                            //pega isbn via camera
                            try {
                                //instanciando scanner
                                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                                intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
                                startActivityForResult(intent, Constantes.SCANNER_REQUEST);
                            } catch (ActivityNotFoundException e) {

                                Toast.makeText(MainActivity.this, R.string.leitor, Toast.LENGTH_SHORT).show();
                                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        }
                    }
                });
                builder.show();

                break;
            case 2:
                intent=new Intent(MainActivity.this, TagEditActivity.class);
                startActivity(intent);

                break;
            case 3:

                //recomendados
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {

                Intent intent=new Intent(MainActivity.this, ResultadoPesquisaActivity.class);
                intent.putExtra("data",query);
                startActivity(intent);

                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:

                String PREF_NAME = "MyPrefsFile";
                SharedPreferences settingsShared = getSharedPreferences(PREF_NAME, 0);

                Dialog dialog= new Dialog(MainActivity.this);
                dialog.setTitle(getString(R.string.configuracoes));
                dialog.setContentView(R.layout.h_custom_dialog_configuracoes);

                final CheckBox notificacoes= (CheckBox) dialog.findViewById(R.id.checkboxNotificacoes);

                if(settingsShared.getBoolean("receberNotificacao", false)==true){
                    notificacoes.setChecked(true);
                }

                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(final DialogInterface d) {

                    String PREFS_NAME = "MyPrefsFile";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    if(settings.getBoolean("receberNotificacao", false)==true && notificacoes.isChecked()==false) {
                        settings.edit().putBoolean("receberNotificacao", false).commit();
                    }else if(settings.getBoolean("receberNotificacao", false)==false && notificacoes.isChecked()==true){
                        settings.edit().putBoolean("receberNotificacao", true).commit();
                    }

                    }
                });

                break;
            case R.id.action_logout:

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    ContainerListFragment tabContainers=new ContainerListFragment();
                    return tabContainers;
                case 1:
                    tab_ObrasActivity tabObras=new tab_ObrasActivity();
                    return tabObras;
                case 2:
                    tab_TagsActivity tabTags=new tab_TagsActivity();
                    return tabTags;
                case 3:
                    tab_RecomendadosActivity tabRecomendados=new tab_RecomendadosActivity();
                    return tabRecomendados;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Containers";
                case 1:
                    return "Obras";
                case 2:
                    return "Tags";
                case 3:
                    return "Recomendados";
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constantes.SCANNER_REQUEST) {
            if (resultCode == RESULT_OK) {

                //capturando o resultado do scanner
                String contents = data.getStringExtra("SCAN_RESULT");

                Intent intent=new Intent(this, ResultadoScannerActivity.class);
                intent.putExtra("isbn",contents);
                startActivity(intent);

            } else {
                Toast.makeText(this, getString(R.string.falha_leitura), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void primeiroAcesso() {

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setIcon(R.drawable.container_estante_icon);
        alert.setTitle(R.string.bem_vindo);
        alert.setMessage(R.string.primeiro_acesso);
        //alert.setView(R.layout.dialog_bem_vindo);
        alert.setPositiveButton(R.string.seguinte, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, CadastroPagerActivity.class));
            }
        });
        alert.show();
    }

}
