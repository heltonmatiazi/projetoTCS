package br.com.senac.cademeulivro.activity.container;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.dao.ContainerTiposDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.ContainerTipos;
import br.com.senac.cademeulivro.util.classes.AlarmReceiver;
import br.com.senac.cademeulivro.util.classes.GerenciadorDeNotificacoes;

public class CadastroPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<ContainerTipos> mContainerTiposList;
    private SQLiteDatabase mDatabase;
    private EditText localContainer, nomeContainer;
    private TextView ultimaModificacao;
    private ContainerTipos tipo;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_activity_cadastro_pager);

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
        ultimaModificacao = (TextView) findViewById(R.id.textViewData);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        ultimaModificacao.setText(getString(R.string.ultima_modif_container, df.format(new Date())));

        id = (Integer) getIntent().getSerializableExtra("container_id");
        if(id !=null && id > 0) {
            ContainerDAO daoContainer = new ContainerDAO(mDatabase);
            Container c = daoContainer.getById(getIntent().getIntExtra("container_id",0));
            mViewPager.setCurrentItem(c.getContainerTipos().get_id()-1 );
            localContainer.setText(c.getLocalContainer());
            ultimaModificacao.setText(df.format(c.getUltimaModificacao()));
            nomeContainer.setText(c.getNomeContainer());
        }

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
        novoContainer.setContainerTipos(new ContainerTipos(mViewPager.getCurrentItem()+1)); //pager conta a partir do 0
        ContainerDAO containerDAO = new ContainerDAO(mDatabase);
        long result = 0;
        if(id !=null) {
            novoContainer.setIdContainer(id);
            result = containerDAO.update(novoContainer);
        } else {
            result = containerDAO.insert(novoContainer);
        }
        if(result > 0) {

            //criando notification
            GerenciadorDeNotificacoes notificacoes= new GerenciadorDeNotificacoes(CadastroPagerActivity.this,
                    new Intent(CadastroPagerActivity.this, AlarmReceiver.class), novoContainer.getNomeContainer());

            notificacoes.criarNotification((int) result);
            finish();
        } else {
            Toast.makeText(this,"Erro", Toast.LENGTH_SHORT).show();
        }
    }

}
