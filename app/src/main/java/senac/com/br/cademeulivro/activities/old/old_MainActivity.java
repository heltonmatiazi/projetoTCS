package senac.com.br.cademeulivro.activities.old;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.dao.DatabaseHelper;
import senac.com.br.cademeulivro.dao.ObraCursorWrapper;
import senac.com.br.cademeulivro.model.Obra;


public class old_MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ListView listView;
    private old_AdapterListView adapterListView;
    private List<Obra> itens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_main);
        mDatabase = new DatabaseHelper(getApplicationContext()).getReadableDatabase();
        listView = (ListView) findViewById(R.id.ListViewColecao);
        createListView();

    }


    public void createListView(){
        itens = getListaObras();

        adapterListView=new old_AdapterListView(this,itens);
        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.CYAN);

    }

    private List<Obra> getListaObras() {
        Cursor cursor = mDatabase.query("Obra",null, null, null, null, null, null);
        ObraCursorWrapper wrapper = new ObraCursorWrapper(cursor);
        List<Obra> lista = new ArrayList<>();

        try {
            wrapper.moveToFirst();
            while(!wrapper.isAfterLast()) {
                lista.add(wrapper.getObra());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }
        return lista;
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        public void cadastrarLivro(View v) {
            Intent intent = new Intent(this,old_ObraCadastroActivity.class);
            startActivity(intent);
        }

        @Override
        public void onResume() {
            super.onResume();
            if(adapterListView == null) {
                adapterListView = new old_AdapterListView(this,getListaObras());
                listView.setAdapter(adapterListView);
            } else {
                adapterListView.setItens(getListaObras());
                adapterListView.notifyDataSetChanged();
            }
        }

}
