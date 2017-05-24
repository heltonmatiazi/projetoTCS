package br.com.senac.cademeulivro.activities.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activities.edit.CadastroPagerActivity;
import br.com.senac.cademeulivro.activities.edit.ObraDetalhadaEditActivity;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;

public class ContainerListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ContainerDAO containerDAO;
    private ContainerAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    private List<Container> itens;
    //private FloatingActionButton fbMain;
    //private Animation FabOpen,FabClose;
    //private boolean isOpen=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_container, container, false);
        mDatabase = DatabaseHelper.newInstance(getActivity());
        containerDAO = new ContainerDAO(mDatabase);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.container_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private class ContainerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Container mContainer;
        private ImageView imgCapa;
        private TextView txtNome, txtTotalObras, txtDtModificacao;

        public ContainerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_container, parent, false));
            itemView.setOnClickListener(this);
            imgCapa = (ImageView) itemView.findViewById(R.id.imgContainer);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtTotalObras = (TextView) itemView.findViewById(R.id.txtTotalObras);
            txtDtModificacao = (TextView) itemView.findViewById(R.id.txtDtModificacao);
        }

        public void bind(Container c) {
            mContainer = c;
            imgCapa.setImageResource(Integer.parseInt(c.getContainerTipos().getTipoIcone()));
            txtNome.setText(c.getNomeContainer());
            txtDtModificacao.setText(c.getUltimaModificacao().toString()); //format
            txtTotalObras.setText(c.getTotalObras());

        }

        @Override
        public void onClick(View v) {
            //launch new
        }
    }

    private class ContainerAdapter extends RecyclerView.Adapter<ContainerHolder> {
         private List<Container> mContainerList;

         public ContainerAdapter(List<Container> containerList) {
            mContainerList = containerList;
         }

         @Override
         public ContainerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ContainerHolder(layoutInflater, parent);
         }

         @Override
         public void onBindViewHolder(ContainerHolder holder, int position) {
             Container c = mContainerList.get(position);
             holder.bind(c);
         }

         @Override
         public int getItemCount() {
             return mContainerList.size();
         }

         public void setContainerList(List<Container> itens) {
             mContainerList = itens;
         }
    }

    private void refresh() {
        List<Container> itens = containerDAO.getAll(); //get da biblioteca especifica!!!
        checkIfEmpty(itens);

        if (mAdapter == null) {
            mAdapter = new ContainerAdapter(itens);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setContainerList(itens);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void checkIfEmpty(List<Container> itens) {
        if(itens.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage(R.string.container_vazio);
            alert.setPositiveButton(R.string.comecar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), CadastroPagerActivity.class));

                }
            });
            alert.show();
        }
    }
}

