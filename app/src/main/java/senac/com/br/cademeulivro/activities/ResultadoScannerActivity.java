package senac.com.br.cademeulivro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.model.Obra;
import senac.com.br.cademeulivro.util.Scanner;
import senac.com.br.cademeulivro.util.adapter.AdapterListViewObra;

public class ResultadoScannerActivity extends AppCompatActivity {

    private ListView listView;
    private AdapterListViewObra adapterListViewObra;
    private List<Obra> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_scanner);

        listView= (ListView) findViewById(R.id.listaScanner);

        Bundle parametros=getIntent().getExtras();

        Scanner scanner=new Scanner(this);
        lista=scanner.pesquisar(parametros.getString("isbn"));

        //List<Obra> lista= (List<Obra>) parametros.get("lista");

        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        adapterListViewObra = new AdapterListViewObra(this, lista);
        listView.setAdapter(adapterListViewObra);

        listView.setOnItemClickListener(cliqueCurto());

        listView.refreshDrawableState();

    }//implementar botao volta na action bar

//click curto -instanciar obraedit com as informacoes

    public AdapterView.OnItemClickListener cliqueCurto() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Obra obra = (Obra) adapterListViewObra.getItem(position);

                Intent intent = new Intent(ResultadoScannerActivity.this, ObraDetalhadaActivity.class);
                intent.putExtra("obra",obra);
                startActivity(intent);

            }
        };


    }

}
