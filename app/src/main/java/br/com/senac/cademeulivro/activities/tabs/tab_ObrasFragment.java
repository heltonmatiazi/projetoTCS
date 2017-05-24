package br.com.senac.cademeulivro.activities.tabs;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.dao.ObraDAO;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewObra;

public class tab_ObrasFragment extends Fragment {
    private ListView listView;
    private ObraDAO obraDao;
    private SQLiteDatabase mDatabase;
    private AdapterListViewObra adapterListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activity_obras, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        obraDao = new ObraDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaObras);
        refresh();
        return rootView;
    }

    public void refresh() {
        List<Obra> itens = obraDao.getListaObras();
        if(adapterListView == null) {
           adapterListView=new AdapterListViewObra(getActivity(),itens);
           listView.setAdapter(adapterListView);
       } else {
            adapterListView.setItens(itens);
            adapterListView.notifyDataSetChanged();
       }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
