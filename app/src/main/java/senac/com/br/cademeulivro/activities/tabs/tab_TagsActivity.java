package senac.com.br.cademeulivro.activities.tabs;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.dao.DatabaseHelper;
import senac.com.br.cademeulivro.dao.TagDAO;
import senac.com.br.cademeulivro.model.Tag;
import senac.com.br.cademeulivro.util.adapter.AdapterListViewTags;
import senac.com.br.cademeulivro.util.itemListView.ItemListViewTags;

/**
 * Created by joaos on 22/04/2017.
 */

public class tab_TagsActivity extends Fragment {

    private ListView listView;
    private AdapterListViewTags adapterListViewTags;
    private TagDAO tagDAO;
    private SQLiteDatabase mDatabase;
    private List<Tag> itens;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_activity_tags, container, false);

        mDatabase = DatabaseHelper.newInstance(getActivity());
        tagDAO= new TagDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaTags);

        //createListView();

        return rootView;
    }

    public void createListView(){

        itens = tagDAO.getListaTags();

        adapterListViewTags = new AdapterListViewTags(getActivity(), itens);
        listView.setAdapter(adapterListViewTags);

    }


}
