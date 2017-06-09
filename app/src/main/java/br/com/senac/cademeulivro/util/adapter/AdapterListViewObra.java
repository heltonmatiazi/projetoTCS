package br.com.senac.cademeulivro.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.model.Obra;


public class AdapterListViewObra extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Obra> itens;


    public AdapterListViewObra(Context context, List<Obra> itens) {

        this.itens = itens;
        inflater=LayoutInflater.from(context);
    }

    public void setItens(List<Obra> itens) {
        this.itens = itens;
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
        Obra item= (Obra) itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.activity_item_lista,null);

        //Resgatar os dois textviews e o imageview para insercao do conteudo
        TextView titulo=(TextView) view.findViewById(R.id.TextViewTituloLista);
        TextView autor=(TextView) view.findViewById(R.id.TextViewAutorLista);
        ImageView capa=(ImageView) view.findViewById(R.id.ImageViewCapaLista);

        titulo.setText(item.getTitulo());
        autor.setText(item.getAutor());
        capa.setImageBitmap(item.getCapa());

        return view;
    }

}
