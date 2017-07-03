package br.com.senac.cademeulivro.activity.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;



import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaActivity;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaEditActivity;
import br.com.senac.cademeulivro.dao.ObraDAO;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewObra;


public class tab_ObrasActivity extends Fragment {

    private ListView listView;
    private ObraDAO obraDao;
    private ObraTagDAO obraTagDAO;
    private SQLiteDatabase mDatabase;
    private AdapterListViewObra adapterListView;
    private List<Obra> itens;
    private Obra obra;
    private View viewDeletar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.b_tab_activity_obras, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        obraDao = new ObraDAO(mDatabase);
        obraTagDAO = new ObraTagDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaObras);
        createListView();

        listView.setOnItemClickListener(cliqueCurto());
        listView.setOnItemLongClickListener(cliqueLongo());


        return rootView;
    }


    public void createListView() {

        itens = obraDao.getListaObras();
        //Obra obra=obraDao.getById(1);
        adapterListView = new AdapterListViewObra(getActivity(), itens);
        listView.setAdapter(adapterListView);
    }



    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                obra = (Obra)adapterListView.getItem(position);

                Intent intent = new Intent(getActivity(), ObraDetalhadaActivity.class);
                intent.putExtra("capa",obra.getCapa());
                obra.setCapa(null);
                intent.putExtra("obra",obra);
                startActivity(intent);

            }
        };
    }

    public AdapterView.OnItemLongClickListener cliqueLongo() {

        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long l) {

                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                obra = (Obra)adapterListView.getItem(position);
                viewDeletar=view;
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int idItem= item.getItemId();

                        if (idItem==R.id.popupEditar) {

                            Intent intent=new Intent(getContext(), ObraDetalhadaEditActivity.class);
                            intent.putExtra("obra",obra);
                            startActivity(intent);

                        } else {
                            obraTagDAO.deleteObraFromAll(obra.getIdObra());
                            obraDao.delete(obra.getIdObra());

                            //TODO fazer refresh
                            Toast.makeText(getContext(), "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popup.show();

                return true;
            }

        };

    }


}
