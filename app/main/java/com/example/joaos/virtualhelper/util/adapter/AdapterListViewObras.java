package com.example.joaos.virtualhelper.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.util.itemListView.ItemListViewObras;

import java.util.ArrayList;

/**
 * Created by joaos on 01/04/2017.
 */

public class AdapterListViewObras extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ItemListViewObras> itens;


    public AdapterListViewObras(Context context, ArrayList<ItemListViewObras> itens) {

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
        ItemListViewObras item=itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.activity_item_lista_obras,null);

        //Resgatar components para insercao do conteudo
        TextView titulo=(TextView) view.findViewById(R.id.TextViewTituloLista);
        TextView autor=(TextView) view.findViewById(R.id.TextViewAutorLista);
        ImageView capa=(ImageView) view.findViewById(R.id.ImageViewCapaLista);
        //TextView edicao= (TextView) view.findViewById(R.id.TextViewEdicaoLista);

        titulo.setText(item.getTitulo());
        autor.setText(item.getAutor());
        capa.setImageResource(item.getCapa());
        //edicao.setText(item.getEdicao());


        return view;
    }

}
