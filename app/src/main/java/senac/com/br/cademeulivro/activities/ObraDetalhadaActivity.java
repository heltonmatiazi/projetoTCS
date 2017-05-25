package senac.com.br.cademeulivro.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.model.Obra;


public class ObraDetalhadaActivity extends AppCompatActivity {

    private Obra obra;
    private ImageView capa;
    private TextView tvTitulo, tvEditora, tvAutor, tvIsbn, tvAno, tvDescricao;
    private CheckBox emprestado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada);
        //setTitle(----); pegar nome da obra

        tvTitulo= (TextView) findViewById(R.id.TextViewTitulo);
        tvAno= (TextView) findViewById(R.id.TextViewAno);
        tvAutor= (TextView) findViewById(R.id.TextViewAutor);
        tvDescricao= (TextView) findViewById(R.id.TextViewBreveDesc);
        tvEditora= (TextView) findViewById(R.id.TextViewEditora);
        tvIsbn= (TextView) findViewById(R.id.TextViewIsbn);
        emprestado= (CheckBox) findViewById(R.id.CheckBoxEmprestado);
        capa= (ImageView) findViewById(R.id.ImageViewCapa);

        Bundle parametros = getIntent().getExtras();

        if(parametros != null) {

            obra= (Obra) parametros.getSerializable("obra");
            preencheCampos(obra);
        }


    }


    public void obraDetalhadaEditar(View v){

        //chamar obraDetalhadaEdit prenchendo os edits


    }

    public void obraDetalhadaVoltar(View v){ finish(); }


    public void preencheCampos(Obra obra){

        tvTitulo.setText(obra.getTitulo());
        tvEditora.setText(obra.getEditora());
        tvIsbn.setText(obra.getIsbn());
        tvDescricao.setText(obra.getDescricao());
        tvAno.setText(String.valueOf(obra.getAnoPublicacao()));
        tvAutor.setText(obra.getAutor());
        emprestado.setChecked(obra.isEmprestado());
/*
        Bitmap bitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeByteArray(obra.getCapa(), 0, obra.getCapa().length, options);
        capa.setImageBitmap( bitmap);*/
    }
}
