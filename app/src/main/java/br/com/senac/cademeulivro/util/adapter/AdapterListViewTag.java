package br.com.senac.cademeulivro.util.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Tag;

/**
 * Created by joaos on 01/04/2017.
 */

public class AdapterListViewTag extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Tag> itens;
    private ObraTagDAO obraTagDAO;
    private SQLiteDatabase mDatabase;

    public AdapterListViewTag(Context context, List<Tag> itens) {

        mDatabase = new DatabaseHelper(context).getWritableDatabase();
        obraTagDAO = new ObraTagDAO(mDatabase);

        this.itens = itens;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Tag item= (Tag)itens.get(position);

        view=inflater.inflate(R.layout.c_activity_item_lista_tags,null);

        TextView nome=(TextView) view.findViewById(R.id.textViewNomeTag);
        TextView usos= (TextView) view.findViewById(R.id.textViewUsoTag);
        LinearLayout layoutTags= (LinearLayout) view.findViewById(R.id.layoutTags);

        nome.setText((item.getNomeTag()!=null && item.getNomeTag().length()>20) ? item.getNomeTag().substring(0,20)+"..." : item.getNomeTag());
        usos.setText(String.valueOf(obraTagDAO.getAplicacoes(item.getIdTag())));
        layoutTags.setBackgroundColor(Color.parseColor(item.getCorHex()));

        return view;
    }

}
