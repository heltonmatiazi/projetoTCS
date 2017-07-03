package br.com.senac.cademeulivro.activity.tabs;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activity.container.ContainerEditActivity;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.dao.ContainerTiposDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewContainer;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewContainerDialog;
import br.com.senac.cademeulivro.util.constante.Constantes;

/**
 * Created by joaos on 22/04/2017.
 */


public class tab_ContainersActivity extends Fragment {

    private ContainerDAO containerDAO;
    private SQLiteDatabase mDatabase;
    private ListView listContainerTab;
    private List<Container> itens;
    private AdapterListViewContainer adapter;
    private Container container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.b_tab_activity_containers, container, false);

        listContainerTab= (ListView) rootView.findViewById(R.id.listContainerTab);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        containerDAO = new ContainerDAO(mDatabase);

        listContainerTab.setOnItemClickListener(cliqueCurto());
        listContainerTab.setOnItemLongClickListener(cliqueLongo());

        return rootView;
    }

    public void createListView(){

        itens = containerDAO.getAll();
        adapter = new AdapterListViewContainer(getActivity(), itens);
        listContainerTab.setAdapter(adapter);
    }

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                container = (Container)adapter.getItem(position);
                Intent intent=new Intent(getContext(), ContainerEditActivity.class);
                intent.putExtra("container",container);
                startActivity(intent);
            }
        };
    }


    public AdapterView.OnItemLongClickListener cliqueLongo() {

        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                container = (Container)adapter.getItem(position);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int idItem= item.getItemId();

                        if (idItem==R.id.popupEditar) {

                            Intent intent=new Intent(getContext(), ContainerEditActivity.class);
                            intent.putExtra("container",container);
                            startActivityForResult(intent, Constantes.REFRESH_REQUEST);

                        } else {
                            //containerDAO.delete
                            //cancelar notificacoes

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
