package senac.com.br.cademeulivro.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.dao.DatabaseHelper;

public class ObraCadastroActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private EditText editTitulo, editAutor, editEditora, editDescricao, editISBN, editAnoPublicacao;
    private CheckBox emprestado;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_cadastro);
        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        imgCapa = (ImageView) findViewById(R.id.imageCapa);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditora = (EditText) findViewById(R.id.editEditora);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editISBN = (EditText) findViewById(R.id.editISBN);
        editAnoPublicacao = (EditText) findViewById(R.id.editAnoPublicacao);
        emprestado = (CheckBox) findViewById(R.id.checkboxEmprestado);

    }

    public void salvarLivro(View v) {
        ContentValues valores = new ContentValues();

        Bitmap capa =((BitmapDrawable) imgCapa.getDrawable()).getBitmap();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        capa.compress(Bitmap.CompressFormat.JPEG, 100, output);
        byte[] img = output.toByteArray();

        valores.put("capa",img);
        valores.put("titulo", editTitulo.getText().toString());
        valores.put("autor", editAutor.getText().toString());
        valores.put("editora", editEditora.getText().toString());
        valores.put("isbn", editISBN.getText().toString());
        valores.put("anoPublicacao", Integer.parseInt(editAnoPublicacao.getText().toString()));
        valores.put("emprestado", emprestado.isChecked());
        valores.put("descricao", editDescricao.getText().toString());

        mDatabase.insert("Obra",null, valores);
        finish();
    }
    public void cancelarCadastro(View v){
        mDatabase.close();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent );
    }
}
