package senac.com.br.cademeulivro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import senac.com.br.cademeulivro.R;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListViewColecao);
        createListView();

    }


    public void createListView(){

        itens=new ArrayList<ItemListView>();

        ItemListView item1=new ItemListView("Manual do Advogado","Valdemar P. da Luz",R.drawable.capa);

        itens.add(item1);
        itens.add(item1);
        adapterListView=new AdapterListView(this,itens);
        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.CYAN);

    }


}
