package br.com.senac.cademeulivro.activity.tag;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaActivity;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaEditActivity;
import br.com.senac.cademeulivro.dao.ObraDAO;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewObra;

public class ObrasComATagActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListViewObra adapter;
    private List<Obra> list;
    private ObraTagDAO obraTagDAO;
    private SQLiteDatabase mDatabase;
    private Obra obra;
    private ObraDAO obraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_obras_com_a_tag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = DatabaseHelper.newInstance(this);
        obraTagDAO = new ObraTagDAO(mDatabase);
        obraDAO = new ObraDAO(mDatabase);
        Bundle parametros= getIntent().getExtras();

        listView= (ListView) findViewById(R.id.listaObrasComATag);

        list=obraTagDAO.getByIdTag(parametros.getInt("idTag"));
        adapter=new AdapterListViewObra(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(cliqueLongo());
        listView.setOnItemClickListener(cliqueCurto());

        if(list.size()==0){
            Toast.makeText(this, R.string.sem_obras, Toast.LENGTH_SHORT).show();
        }
    }


    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                obra = (Obra)adapter.getItem(position);

                Intent intent = new Intent(ObrasComATagActivity.this, ObraDetalhadaActivity.class);
                intent.putExtra("capa",obra.getCapa());
                obra.setCapa(null);
                intent.putExtra("obra",obra);
                startActivity(intent);

            }
        };
    }

    public AdapterView.OnItemLongClickListener cliqueLongo() {

        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long l) {

                PopupMenu popup = new PopupMenu(ObrasComATagActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                obra = (Obra)adapter.getItem(position);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        int idItem= item.getItemId();

                        if (idItem==R.id.popupEditar) {

                            Intent intent=new Intent(ObrasComATagActivity.this, ObraDetalhadaEditActivity.class);
                            intent.putExtra("obra",obra);
                            startActivity(intent);

                        } else {
                            obraDAO.delete(obra.getIdObra());
                            //TODO fazer refresh
                            Toast.makeText(ObrasComATagActivity.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
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
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
