package br.com.senac.cademeulivro.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.senac.cademeulivro.activities.ContainerFragment;
import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerTiposDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.ContainerTipos;

public class CadastroPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<ContainerTipos> mContainerTiposList;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pager);

        mViewPager = (ViewPager) findViewById(R.id.cadastro_pager);
        mDatabase = DatabaseHelper.newInstance(this);
        ContainerTiposDAO dao = new ContainerTiposDAO(mDatabase);
        mContainerTiposList = dao.getContainersDefault();

        for(ContainerTipos ct : mContainerTiposList) {
            System.out.println(ct.getTipoNome());
        }

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                ContainerTipos tipo = mContainerTiposList.get(position);
                return ContainerFragment.newInstance(tipo);
            }

            @Override
            public int getCount() {
                return mContainerTiposList.size();
            }
        });

        EditText localContainer = (EditText) findViewById(R.id.editLocalContainer);
        EditText nomeContainer = (EditText) findViewById(R.id.editNomeContainer);
        TextView totalObras = (TextView) findViewById(R.id.txtTotalObras);
        TextView ultimaModificacao = (TextView) findViewById(R.id.ultimaModificacao);
        TextView ultimaObraAdd = (TextView) findViewById(R.id.ultimaObraAdd);
        totalObras.setText("0 obras");

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ultimaModificacao.setText(df.format(new Date()));
    }

}
