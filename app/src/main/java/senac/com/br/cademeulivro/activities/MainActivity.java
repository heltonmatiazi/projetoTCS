package senac.com.br.cademeulivro.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;

import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.activities.edit.ContainerEditActivity;
import senac.com.br.cademeulivro.activities.edit.ObraDetalhadaEditActivity;
import senac.com.br.cademeulivro.activities.edit.TagEditActivity;
import senac.com.br.cademeulivro.activities.tabs.tab_ContainersActivity;
import senac.com.br.cademeulivro.activities.tabs.tab_ObrasActivity;
import senac.com.br.cademeulivro.activities.tabs.tab_RecomendadosActivity;
import senac.com.br.cademeulivro.activities.tabs.tab_TagsActivity;
import senac.com.br.cademeulivro.model.Obra;
import senac.com.br.cademeulivro.util.Constantes;
import senac.com.br.cademeulivro.util.Scanner;

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
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
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

    }


    public void fabFuncao(View v){

        CharSequence opcoes[];
        Intent intent;

        switch (tabPosicao){

            case 0:
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
                            Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
                            intent.setAction("com.google.zxing.client.android.SCAN");
                            intent.putExtra("SAVE_HISTORY", false);
                            startActivityForResult(intent, Constantes.SCANNER_REQUEST);

                        }

                    }
                });
                builder.show();


                break;
            case 1:

                intent=new Intent(MainActivity.this,TagEditActivity.class);
                startActivity(intent);

                break;
            case 2:

                intent=new Intent(MainActivity.this,ContainerEditActivity.class);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:




                break;
            case R.id.action_logout:

                break;
            case R.id.action_search:

                //...
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
                    tab_ObrasActivity tabObras=new tab_ObrasActivity();
                    return tabObras;
                case 1:
                    tab_TagsActivity tabTags=new tab_TagsActivity();
                    return tabTags;
                case 2:
                    tab_ContainersActivity tabContainers=new tab_ContainersActivity();
                    return tabContainers;
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
                    return "Obras";
                case 1:
                    return "Tags";
                case 2:
                    return "Containers";
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


                Intent intent=new Intent(this,ResultadoScannerActivity.class);
                intent.putExtra("isbn",contents);
                startActivity(intent);
                /*
                Intent intent=new Intent(this,ResultadoScannerActivity.class);
                intent.putExtra("lista",(ArrayList<Obra>)lista);
                startActivity(intent);
*/
            } else {
                Toast.makeText(this, "Falha ao ler o código!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
