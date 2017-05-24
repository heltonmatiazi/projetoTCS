package br.com.senac.cademeulivro.activities.edit;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import br.com.senac.cademeulivro.ContainerFragment;
import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerTiposDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

}
