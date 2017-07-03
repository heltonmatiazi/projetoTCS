package br.com.senac.cademeulivro.activity.obra;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.model.Tag;
import br.com.senac.cademeulivro.util.constante.Constantes;


public class ObraDetalhadaActivity extends AppCompatActivity {

    private Obra obra;
    private ImageView capa;
    private TextView tvTitulo, tvEditora, tvAutor, tvIsbn, tvAno, tvDescricao, TextViewConteinerObra;
    private CheckBox emprestado;
    private Button botaoConcluir;
    private LinearLayout layoutTagsDetalhada;
    private Bitmap imagem;

    private ObraTagDAO obraTagDAO;
    private SQLiteDatabase mDatabase;
    private List<Tag> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_obra_detalhada);
        setTitle("Informações");

        tvTitulo= (TextView) findViewById(R.id.TextViewTituloObra);
        tvAno= (TextView) findViewById(R.id.TextViewAnoObra);
        tvAutor= (TextView) findViewById(R.id.TextViewAutorObra);
        tvDescricao= (TextView) findViewById(R.id.TextViewDescricaoObra);
        tvEditora= (TextView) findViewById(R.id.TextViewEditoraObra);
        tvIsbn= (TextView) findViewById(R.id.TextViewIsbnObra);
        emprestado= (CheckBox) findViewById(R.id.CheckBoxEmprestadoObra);
        capa= (ImageView) findViewById(R.id.ImageViewCapaObra);
        botaoConcluir= (Button) findViewById(R.id.ButtonEditarObra);
        layoutTagsDetalhada= (LinearLayout) findViewById(R.id.layoutTagsDetalhada);
        TextViewConteinerObra = (TextView) findViewById(R.id.TextViewConteinerObra);

        Bundle parametros = getIntent().getExtras();
        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        obraTagDAO= new ObraTagDAO(mDatabase);

        if (parametros!=null) {
            obra = (Obra) parametros.getSerializable("obra");
            tags=obraTagDAO.getByIdObra(obra.getIdObra());

            imagem=(Bitmap) parametros.getParcelable("capa");
            capa.setImageBitmap(imagem);
            capa.setScaleX(1.5F);
            capa.setScaleY(1.5F);

            preencheCampos(obra);

            if (obra.getIdObra() == null) {
                botaoConcluir.setText("Adicionar");
            }
        }

        //criando os botões para apresentar as tags
        if(tags!=null && tags.size()!=0) {

            for (int i = 0; i < tags.size(); i++) {

                Tag tag = tags.get(i);
                Button tagButton = new Button(this);
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

                tagButton.setPadding(10,0,10,0);
                tagButton.setLayoutParams(layoutParams);
                tagButton.setText(tag.getNomeTag());
                tagButton.setClickable(false);
                tagButton.setTag(tag.getIdTag());
                tagButton.setBackgroundResource(R.drawable.tags_custom_view);

                layoutTagsDetalhada.addView(tagButton);
            }
        }
    }

    public void obraDetalhadaEditar(View v) {

        Intent intent=new Intent(this, ObraDetalhadaEditActivity.class);

        intent.putExtra("capa",imagem);

        obra.setCapa(null);
        intent.putExtra("obra",obra);

        startActivityForResult(intent, Constantes.CLOSE_REQUEST);
        //TODO fazer finish no activity for result
    }

    public void obraDetalhadaVoltar(View v){ finish(); }

    public void preencheCampos(Obra obra){

        tvTitulo.setText((obra.getTitulo()!=null && obra.getTitulo().length()>30) ? obra.getTitulo().substring(0,30)+"..." : obra.getTitulo());
        tvEditora.setText((obra.getEditora()!=null && obra.getEditora().length()>30) ? obra.getEditora().substring(0,30)+"..." : obra.getEditora());
        tvIsbn.setText(obra.getIsbn());
        tvDescricao.setText((obra.getDescricao()!=null && obra.getDescricao().length()>150) ? obra.getDescricao().substring(0,150)+"..." : obra.getDescricao());
        tvAno.setText(String.valueOf(obra.getAnoPublicacao()));
        tvAutor.setText((obra.getAutor()!=null && obra.getAutor().length()>30) ? obra.getAutor().substring(0,30)+"..." : obra.getAutor());
        emprestado.setChecked(obra.isEmprestado());
        //TextViewConteinerObra.setText(obra.getContainer().getNomeContainer());
        //capa.setImageBitmap(obra.getCapa());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==Constantes.CLOSE_REQUEST) {
                if (resultCode == RESULT_OK) {
                    finish();
                }
        }
    }

}
