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
import br.com.senac.cademeulivro.activity.tag.ObrasComATagActivity;
import br.com.senac.cademeulivro.activity.tag.TagEditActivity;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.dao.TagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Tag;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewTag;
import br.com.senac.cademeulivro.util.constante.Constantes;


public class tab_TagsActivity extends Fragment {

    private ListView listView;
    private AdapterListViewTag adapterListViewTags;
    private TagDAO tagDAO;
    private ObraTagDAO obraTagDAO;
    private SQLiteDatabase mDatabase;
    private List<Tag> itens;
    private Tag tag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.b_tab_activity_tags, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        tagDAO= new TagDAO(mDatabase);
        obraTagDAO = new ObraTagDAO(mDatabase);
        listView = (ListView) rootView.findViewById(R.id.listaTags);

        createListView();

        listView.setOnItemLongClickListener(cliqueLongo());
        listView.setOnItemClickListener(cliqueCurto());

        return rootView;
    }

    public void createListView(){

        itens = tagDAO.getListaTags();
        adapterListViewTags = new AdapterListViewTag(getActivity(), itens);
        listView.setAdapter(adapterListViewTags);
    }

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                tag = (Tag)adapterListViewTags.getItem(position);
                Intent intent=new Intent(getContext(), ObrasComATagActivity.class);
                intent.putExtra("idTag",tag.getIdTag());
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

                tag = (Tag)adapterListViewTags.getItem(position);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int idItem= item.getItemId();

                        if (idItem==R.id.popupEditar) {

                            Intent intent=new Intent(getContext(), TagEditActivity.class);
                            intent.putExtra("tag",tag);
                            startActivityForResult(intent, Constantes.REFRESH_REQUEST);

                        } else {
                            obraTagDAO.deleteTagFromAll(tag.getIdTag());
                            tagDAO.delete(tag.getIdTag());

                            //obraTagDAO.delete()
                            //itens = tagDAO.getListaTags();
                            //adapterListViewTags.notifyDataSetChanged();
                            //adapterListViewTags = new AdapterListViewTag(getActivity(), itens);
                            //adapter.remove nao existe
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //nunca chega aqui
        if ( requestCode == Constantes.REFRESH_REQUEST){

            itens = tagDAO.getListaTags();
            adapterListViewTags.notifyDataSetChanged();
            adapterListViewTags = new AdapterListViewTag(getActivity(), itens);
        }


    }
}
