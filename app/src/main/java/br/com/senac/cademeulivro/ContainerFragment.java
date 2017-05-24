package br.com.senac.cademeulivro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.ContainerTipos;

/**
 * Created by Didi on 5/23/2017.
 */

public class ContainerFragment extends Fragment {
    private ContainerTipos tipo;

    public static ContainerFragment newInstance(ContainerTipos c) {
        Bundle args = new Bundle();
        args.putSerializable("tipo", c);
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipo = (ContainerTipos) getArguments().getSerializable("tipo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container,container, false);

        ImageView img = (ImageView) v.findViewById(R.id.imgContainer);
        TextView txtNome = (TextView) v.findViewById(R.id.txtNome);
        //img.setImageResource(getResources().getIdentifier(tipo.getTipoIcone(), "drawable", getPac));
        txtNome.setText(tipo.getTipoNome());
        return v;
    }
}
