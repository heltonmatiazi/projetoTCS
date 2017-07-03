package br.com.senac.cademeulivro.activity.tag;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.TagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Tag;
import yuku.ambilwarna.AmbilWarnaDialog;

public class TagEditActivity extends AppCompatActivity {

    private EditText editNome;
    private LinearLayout previewColor;
    private TagDAO tagDAO;
    private SQLiteDatabase mDatabase;
    private String cor="#0000FF";
    private Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_tag_edit);
        setTitle("Cadastrar");

        editNome= (EditText) findViewById(R.id.editNomeTag);
        previewColor= (LinearLayout) findViewById(R.id.previewColor);

        Bundle parametros=getIntent().getExtras();

        if (parametros!=null) {
            tag= (Tag) parametros.getSerializable("tag");
            preencheCampos(tag);
            setTitle("Editar");

        } else {
            previewColor.setBackgroundColor(Color.parseColor(cor));
        }

        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        tagDAO = new TagDAO(mDatabase);

    }


    public void escolherCor(View v){
        // biblioteca https://github.com/yukuku/ambilwarna

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, Color.parseColor(cor), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                cor=hexColor;
                previewColor.setBackgroundColor(Color.parseColor(hexColor));
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}
        });

        dialog.show();
    }

    public void concluirEditTag(View v) {

        if (tag==null) {
            tag=new Tag();
        }

        tag.setNomeTag(editNome.getText().toString());
        tag.setCorHex(cor);

        if (tag.getIdTag()==null) {
            tag.setTotalUsos(0);
            tagDAO.insert(tag);
        } else {
            tagDAO.update(tag);
        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);

        finish();
    }

    public void cancelarEditTag(View v) { finish(); }

    public void preencheCampos(Tag tag) {

        editNome.setText(tag.getNomeTag().toString());
        previewColor.setBackgroundColor(Color.parseColor(tag.getCorHex()));
        cor=tag.getCorHex();
    }

}
