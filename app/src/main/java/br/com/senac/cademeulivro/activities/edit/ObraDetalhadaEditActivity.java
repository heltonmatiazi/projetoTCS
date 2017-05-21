package br.com.senac.cademeulivro.activities.edit;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ObraDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.util.SingleChoiceClass;

public class ObraDetalhadaEditActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private EditText editTitulo, editAutor, editEditora, editDescricao, editISBN, editAnoPublicacao;
    private CheckBox emprestado;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada_edit);

        //falta num de paginas e da edicao
        imgCapa = (ImageView) findViewById(R.id.imageCapa);
        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditora = (EditText) findViewById(R.id.editEditora);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editISBN = (EditText) findViewById(R.id.editISBN);
        editAnoPublicacao = (EditText) findViewById(R.id.editAnoPublicacao);
        emprestado = (CheckBox) findViewById(R.id.checkboxEmprestado);

        mDatabase = DatabaseHelper.newInstance(this);
    }

    public void salvarLivro(View v) {
        Obra o = new Obra();
        ObraDAO obraDAO = new ObraDAO(mDatabase);
        o.setAnoPublicacao(Integer.valueOf(editAnoPublicacao.getText().toString()));
        o.setTitulo(editTitulo.getText().toString());
        o.setEmprestado(emprestado.isChecked());
        o.setAutor(editAutor.getText().toString());
        o.setEditora(editEditora.getText().toString());
        o.setDescricao(editDescricao.getText().toString());
        o.setIsbn(editISBN.getText().toString());
        //o.setCapa();
        long result = obraDAO.insert(o);
        if(result == -1) {
            Toast.makeText(this, "Erro ao cadastrar obra! Todos os campos estão preenchidos?", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Obra cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


/*
    private FloatingActionButton fbMain,fb1,fb2;
    private Animation FabOpen,FabClose,FabRClockWise,FabRantiClockWise;
    private boolean isOpen=false;
    private CheckBox checkBox;
    private LinearLayout layoutTags;
    private AlertDialog dialog;
    private TextView tagCriar,textViewConteiner;
    private AlertDialog.Builder builder;
    private String[] tags = new String[]{// Boolean array for initial selected items
            "horror",
            "velhos",
            "sujos",
            "recentes",
            "capa dura"
    };
    private final boolean[] checkedTags = new boolean[]{
            false,
            false,
            false,
            false,
            false
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada_edit);
        //setTitle(----); pegar nome da obra

        textViewConteiner= (TextView) findViewById(R.id.TextViewConteiner);
        layoutTags= (LinearLayout) findViewById(R.id.layoutTags);
        checkBox= (CheckBox) findViewById(R.id.CheckBoxEmprestado);

        dialogTags();



        //capturando o FAB e enviando sua animacao quando clicado

        fbMain= (FloatingActionButton) findViewById(R.id.fbMain);
        fb1= (FloatingActionButton) findViewById(R.id.fbTags);
        fb2= (FloatingActionButton) findViewById(R.id.fbContainer);
        FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockWise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_cloclwise);
        FabRantiClockWise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        fbMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){

                    fb2.startAnimation(FabClose);
                    fb1.startAnimation(FabClose);
                    fbMain.startAnimation(FabRantiClockWise);
                    fb1.setClickable(false);
                    fb2.setClickable(false);
                    isOpen=false;

                }else{
                    fb2.startAnimation(FabOpen);
                    fb1.startAnimation(FabOpen);
                    fbMain.startAnimation(FabRClockWise);
                    fb1.setClickable(true);
                    fb2.setClickable(true);
                    isOpen=true;

                }
            }
        });



    }

    public void dialogTags(){

        builder = new AlertDialog.Builder(this);

        //dialog de multipla escolha
        final List<String> tagsList = Arrays.asList(tags);
        builder.setMultiChoiceItems(tags, checkedTags, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedTags[which] = isChecked;

                // Get the current focused item
                String currentItem = tagsList.get(which);

                // Notify the current action
                Toast.makeText(getApplicationContext(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setTitle("Tags");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<tagsList.size();i++){

                    if(checkedTags[i]==true){
                        tagCriar=new TextView(new ContextThemeWrapper(ObraDetalhadaEditActivity.this, R.style.tag_style));
                        tagCriar.setText(tagsList.get(i));
                        layoutTags.addView(tagCriar);
                    }
                }
            }
        });
        // Set the negative/no button click listener
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog= builder.create();

    }

    public void scannerIsbn(View v){
    }
    public void obraDetalhadaEditConcluir (View v){
    }
    public void obraDetalhadaEditCancelar(View v){ finish();
    }
    public void adcFoto(View v){
    }
    public void adicionarContainers(View v){

        SingleChoiceClass dialogContainers=new SingleChoiceClass();
        dialogContainers.show(getSupportFragmentManager(),"dialogContainer");
    }*/

}
