package br.com.senac.cademeulivro.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.model.Container;

/**
 * Created by joaos on 03/07/2017.
 */

public class AdapterListViewContainerDialog extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Container> itens;

    public AdapterListViewContainerDialog(Context context, List<Container> itens) {

        this.itens = itens;
        inflater=LayoutInflater.from(context);
    }

    public void setItens(List<Container> itens) {
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
        Container item= (Container) itens.get(position);

        //Resgatar o layout a ser preenchido
        view=inflater.inflate(R.layout.c_activity_item_lista_obras,null);

        //Resgatar os dois textviews e o imageview para insercao do conteudo
        TextView nomeContainer=(TextView) view.findViewById(R.id.textViewContainerDialog);

        nomeContainer.setText((item.getNomeContainer()!=null && item.getNomeContainer().length()>20) ?
                item.getNomeContainer().substring(0,20)+"..." : item.getNomeContainer());

        return view;
    }
}
