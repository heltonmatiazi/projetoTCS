package br.com.senac.cademeulivro.activity.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



import java.util.ArrayList;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewObra;
import br.com.senac.cademeulivro.util.itemListView.ItemListViewObras;

/**
 * Created by joaos on 22/04/2017.
 */

public class tab_RecomendadosActivity extends Fragment {

    private ListView listView;
    private AdapterListViewObra adapterListView;
    private ArrayList<ItemListViewObras> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.b_tab_activity_recomendados, container, false);

        listView= (ListView) rootView.findViewById(R.id.listaRecomendados);
        createListView();

        return rootView;
    }


    public void createListView(){

        itens=new ArrayList<ItemListViewObras>();

        //ItemListViewObras item1=new ItemListViewObras("Manual do Advogado","Valdemar P. da Luz",R.drawable.capa);
        //ItemListViewObras item2=new ItemListViewObras("O livro Azul","Joao VS",R.drawable.capa);
/*
        itens.add(item1);
        itens.add(item2);
        itens.add(item2);
        itens.add(item1);
        itens.add(item2);
        itens.add(item2);*/

        //adapterListView=new AdapterListViewObra(getActivity(),itens);

        //listView.setAdapter(adapterListView);



    }

}
