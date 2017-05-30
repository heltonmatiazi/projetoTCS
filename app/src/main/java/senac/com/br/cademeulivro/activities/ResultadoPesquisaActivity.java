package senac.com.br.cademeulivro.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import senac.com.br.cademeulivro.R;


public class ResultadoPesquisaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_pesquisa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }//pesquisa por autor ou titulo


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
