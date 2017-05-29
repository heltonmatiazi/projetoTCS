package senac.com.br.cademeulivro.activities.edit;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import senac.com.br.cademeulivro.R;
import senac.com.br.cademeulivro.dao.DatabaseHelper;
import senac.com.br.cademeulivro.dao.TagDAO;
import senac.com.br.cademeulivro.model.Tag;
import yuku.ambilwarna.AmbilWarnaDialog;

public class TagEditActivity extends AppCompatActivity {

    private EditText editNome;
    private LinearLayout previewColor;
    private TagDAO tagDAO;
    private SQLiteDatabase mDatabase;
    private String cor="#0000FF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_edit);

        editNome= (EditText) findViewById(R.id.editNomeTag);
        previewColor= (LinearLayout) findViewById(R.id.previewColor);

        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        tagDAO=new TagDAO(mDatabase);

        previewColor.setBackgroundColor(Color.parseColor(cor));

    }


    public void escolherCor(View v){
        // biblioteca https://github.com/yukuku/ambilwarna

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, 255, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                //cor=color;

                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                cor=hexColor;
                previewColor.setBackgroundColor(Color.parseColor(hexColor));
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }


        });
        dialog.show();


    }

    public void concluirEditTag(View v) {

        Tag tag=new Tag();
        tag.setNomeTag(editNome.getText().toString());
        tag.setCorHex(cor);

        tagDAO.insert(tag);
        finish();
    }

    public void cancelarEditTag(View v) { finish(); }
}
