package br.com.senac.cademeulivro.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.senac.cademeulivro.activities.ContainerFragment;
import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.dao.ContainerTiposDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.ContainerTipos;

public class CadastroPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<ContainerTipos> mContainerTiposList;
    private SQLiteDatabase mDatabase;
    private EditText localContainer, nomeContainer;
    private TextView ultimaModificacao;
    private ContainerTipos tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pager);

        mViewPager = (ViewPager) findViewById(R.id.cadastro_pager);
        mDatabase = DatabaseHelper.newInstance(this);
        ContainerTiposDAO dao = new ContainerTiposDAO(mDatabase);
        mContainerTiposList = dao.getContainersDefault();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                tipo = mContainerTiposList.get(position);
                return ContainerFragment.newInstance(tipo);
            }

            @Override
            public int getCount() {
                return mContainerTiposList.size();
            }
        });

        localContainer = (EditText) findViewById(R.id.editLocalContainer);
        nomeContainer = (EditText) findViewById(R.id.editNomeContainer);
        ultimaModificacao = (TextView) findViewById(R.id.ultimaModificacao);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ultimaModificacao.setText(getString(R.string.ultima_modif_container, df.format(new Date())));
    }

    public void containerCancelar(View v) {
        finish();
    }

    public void containerSalvar(View v) {
        Container novoContainer = new Container();
        novoContainer.setNomeContainer(nomeContainer.getText().toString());
        novoContainer.setLocalContainer(localContainer.getText().toString());
        novoContainer.setUltimaModificacao(new Date());
        novoContainer.setIdBiblioteca(1); //user teste
        novoContainer.setContainerTipos(tipo);
        ContainerDAO containerDAO = new ContainerDAO(mDatabase);
        long result = containerDAO.insert(novoContainer);
        if(result > 0) {
            finish();
        } else {
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }
    }

}
