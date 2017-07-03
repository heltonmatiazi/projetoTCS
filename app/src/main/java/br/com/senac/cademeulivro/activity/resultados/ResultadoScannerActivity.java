package br.com.senac.cademeulivro.activity.resultados;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.activity.obra.ObraDetalhadaActivity;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewObra;
import br.com.senac.cademeulivro.util.classes.Scanner;

public class ResultadoScannerActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListViewObra adapterListViewObra;
    private List<Obra> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_activity_resultado_scanner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView= (ListView) findViewById(R.id.listaScanner);

        Bundle parametros=getIntent().getExtras();
        Scanner scanner=new Scanner(this);

        lista=scanner.pesquisar(parametros.getString("isbn"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //botar thread sleep

        adapterListViewObra = new AdapterListViewObra(this, lista);
        listView.setAdapter(adapterListViewObra);

        listView.setOnItemClickListener(cliqueCurto());
    }

//click curto -instanciar obraedit com as informacoes

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Obra obra = (Obra) adapterListViewObra.getItem(position);

                Intent intent = new Intent(ResultadoScannerActivity.this, ObraDetalhadaActivity.class);
                //imagem irá bugar se for com o objeto
                intent.putExtra("capa",obra.getCapa());
                obra.setCapa(null);
                intent.putExtra("obra",obra);
                startActivity(intent);
            }
        };


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
