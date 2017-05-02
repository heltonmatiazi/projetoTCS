package senac.com.br.cademeulivro.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.activities.*;

import java.util.ArrayList;

/**
 * Created by joaos on 01/04/2017.
 */

public class AdapterListView extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ItemListView> itens;


    public AdapterListView(Context context, ArrayList<ItemListView> itens) {

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

        //Resgatar o item do ListView pelo position
        ItemListView item=itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.activity_item_lista,null);

        //Resgatar os dois textviews e o imageview para insercao do conteudo
        TextView titulo=(TextView) view.findViewById(R.id.TextViewTituloLista);
        TextView autor=(TextView) view.findViewById(R.id.TextViewAutorLista);
        ImageView capa=(ImageView) view.findViewById(R.id.ImageViewCapaLista);

        titulo.setText(item.getTitulo());
        autor.setText(item.getAutor());
        capa.setImageResource(item.getCapa());

        return view;
    }

}
