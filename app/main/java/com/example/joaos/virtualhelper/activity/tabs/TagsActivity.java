package com.example.joaos.virtualhelper.activity.tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.util.adapter.AdapterListViewTags;
import com.example.joaos.virtualhelper.util.itemListView.ItemListViewTags;

import java.util.ArrayList;

/**
 * Created by joaos on 22/04/2017.
 */

public class TagsActivity extends Fragment {

    private ListView listView;
    private AdapterListViewTags adapterListViewTags;
    private ArrayList<ItemListViewTags> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activity_tags, container, false);

        listView = (ListView) rootView.findViewById(R.id.listaTags);
        createListView();
        return rootView;
    }

    public void createListView(){

        itens=new ArrayList<ItemListViewTags>();

        ItemListViewTags item1=new ItemListViewTags("Novos", Color.rgb(0, 0, 100));
        ItemListViewTags item2=new ItemListViewTags("Velhos", Color.rgb(251, 111, 100));
        ItemListViewTags item3=new ItemListViewTags("Sem capa", Color.rgb(0, 130, 0));

        itens.add(item1);
        itens.add(item3);
        itens.add(item2);
        itens.add(item1);
        itens.add(item2);
        itens.add(item3);
        itens.add(item1);
        itens.add(item2);
        itens.add(item3);

        adapterListViewTags=new AdapterListViewTags(getActivity(),itens);

        listView.setAdapter(adapterListViewTags);



    }

}
