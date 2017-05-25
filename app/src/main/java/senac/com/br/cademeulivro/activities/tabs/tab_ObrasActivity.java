package senac.com.br.cademeulivro.activities.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.activities.ObraDetalhadaActivity;
import senac.com.br.cademeulivro.dao.DatabaseHelper;
import senac.com.br.cademeulivro.dao.ObraDAO;
import senac.com.br.cademeulivro.model.Obra;
import senac.com.br.cademeulivro.util.adapter.AdapterListViewObra;


public class tab_ObrasActivity extends Fragment {
    private ListView listView;
    private ObraDAO obraDao;
    private SQLiteDatabase mDatabase;
    private AdapterListViewObra adapterListView;
    private List<Obra> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_activity_obras, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        obraDao = new ObraDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaObras);
        createListView();

        listView.setOnItemClickListener(cliqueCurto());
        return rootView;
    }


    public void createListView() {

        itens = obraDao.getListaObras();

        adapterListView = new AdapterListViewObra(getActivity(), itens);
        listView.setAdapter(adapterListView);
    }

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Obra obra = (Obra) adapterListView.getItem(position);

                Intent intent = new Intent(getActivity(), ObraDetalhadaActivity.class);
                intent.putExtra("obra",obra);
                startActivity(intent);

            }
        };


    }

}
