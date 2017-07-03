package br.com.senac.cademeulivro.activity.obra;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.dao.ContainerDAO;
import br.com.senac.cademeulivro.dao.ObraDAO;
import br.com.senac.cademeulivro.dao.ObraTagDAO;
import br.com.senac.cademeulivro.dao.TagDAO;
import br.com.senac.cademeulivro.helpers.DatabaseHelper;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.model.Tag;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewContainerDialog;
import br.com.senac.cademeulivro.util.adapter.AdapterListViewTagDialog;
import br.com.senac.cademeulivro.util.constante.Constantes;

public class ObraDetalhadaEditActivity extends AppCompatActivity {

    private FloatingActionButton fbMain,fb1,fb2;
    private Animation FabOpen,FabClose,FabRClockWise,FabRantiClockWise;
    private boolean isOpen=false;

    private ObraDAO obraDao;
    private TagDAO tagDao;
    private ObraTagDAO obraTagDAO;
    private ContainerDAO containerDAO;
    private SQLiteDatabase mDatabase;

    private EditText editTitulo, editAutor, editEditora, editDescricao, editISBN, editAnoPublicacao;
    private CheckBox emprestado;
    private TextView TextViewContainer;
    private ImageView imgCapa;
    private LinearLayout layoutTags;
    private AdapterListViewTagDialog adapterListViewTag;
    private AdapterListViewContainerDialog adapterContainer;

    private Obra obra;
    private Bitmap foto=null;
    private String pictureImagePath = "";

    private List<Tag> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_obra_detalhada_edit);
        setTitle("Cadastrar");

        imgCapa = (ImageView) findViewById(R.id.imageViewCapaObraEdit);
        editTitulo = (EditText) findViewById(R.id.editTituloObraEdit);
        editAutor = (EditText) findViewById(R.id.editAutorObraEdit);
        editEditora = (EditText) findViewById(R.id.editEditoraObraEdit);
        editDescricao = (EditText) findViewById(R.id.editDescricaoObraEdit);
        editISBN = (EditText) findViewById(R.id.editIsbnObraEdit);
        editAnoPublicacao = (EditText) findViewById(R.id.editAnoObraEdit);
        emprestado = (CheckBox) findViewById(R.id.checkBoxEmprestadoEdit);
        layoutTags = (LinearLayout) findViewById(R.id.layoutTags);
        TextViewContainer = (TextView) findViewById(R.id.TextViewContainer);

        Bundle parametros=getIntent().getExtras();
        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
        obraDao = new ObraDAO(mDatabase);
        tagDao = new TagDAO(mDatabase);
        obraTagDAO = new ObraTagDAO(mDatabase);
        containerDAO = new ContainerDAO(mDatabase);

        if(parametros!=null) {
            obra= (Obra) parametros.getSerializable("obra");

            tags=obraTagDAO.getByIdObra(obra.getIdObra());

            foto=parametros.getParcelable("capa");
            obra.setCapa(foto);
            preencheCampos(obra);
            setTitle("Editar");
        }

        if(tags!=null && tags.size()!=0) {

            for (int i = 0; i < tags.size(); i++) {

                Tag tag = tags.get(i);
                final Button tagButton = new Button(this);
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

                tagButton.setPadding(10,0,10,0);
                tagButton.setLayoutParams(layoutParams);
                tagButton.setText(tag.getNomeTag());
                tagButton.setTag(tag.getIdTag());
                tagButton.setBackgroundResource(R.drawable.tags_custom_view);

                tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutTags.removeView(tagButton);
                    }
                });

                layoutTags.addView(tagButton);
            }
        }

        //capturando o FAB e enviando sua animacao quando clicado
        fbMain= (FloatingActionButton) findViewById(R.id.fbMainObraEdit);
        fb1= (FloatingActionButton) findViewById(R.id.fbTagsObraEdit);
        fb2= (FloatingActionButton) findViewById(R.id.fbContainersObraEdit);
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

    public void scannerIsbn (View v){

        try {
            //instanciando scanner
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, Constantes.SCANNER_REQUEST);
        } catch (ActivityNotFoundException e) {

            Toast.makeText(this, R.string.leitor, Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public void concluirEditObra (View v){

        if(obra==null){
            obra=new Obra();
        }

        obra.setTitulo(editTitulo.getText().toString());
        obra.setAutor(editAutor.getText().toString());
        obra.setAnoPublicacao((editAnoPublicacao.getText().toString().trim().length()==0) ? 0 : Integer.parseInt(editAnoPublicacao.getText().toString()));
        obra.setDescricao(editDescricao.getText().toString());
        obra.setEditora(editEditora.getText().toString());
        obra.setEmprestado(emprestado.isChecked());
        obra.setIsbn(editISBN.getText().toString());
        obra.setCapa(foto);

        /*if(TextViewContainer.getTag()!=null){
            obra.setContainer(containerDAO.getById((Integer) TextViewContainer.getTag()));
        }*/

        if(obra.getIdObra()!=null){
            obraDao.update(obra);

            int childCount = layoutTags.getChildCount();

            for (int k = 0; k < tags.size(); k++) {

                obraTagDAO.delete(obra.getIdObra(), tags.get(k).getIdTag());
            }

            for (int i = 0; i < childCount; i++) {
                View viewTag = layoutTags.getChildAt(i);

                obraTagDAO.insert(obra.getIdObra(), (int) viewTag.getTag());
            }

        }else {
            long idObra=obraDao.insert(obra);

            int childCount = layoutTags.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View viewTag = layoutTags.getChildAt(i);

                obraTagDAO.insert((int)idObra,(int)viewTag.getTag());
            }
        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void cancelarEditObra(View v){ finish();}

    public void adcFoto(View v){

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, Constantes.CAMERA_REQUEST);

        //instanciando camera
        /*
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Constantes.CAMERA_REQUEST);
        */
    }

    public void adicionarContainers(View v) {

        final Dialog dialog= new Dialog(ObraDetalhadaEditActivity.this);
        dialog.setTitle(getString(R.string.adicionar_container));
        dialog.setContentView(R.layout.h_custom_dialog_lista);

        ListView listViewDialog = (ListView) dialog.findViewById(R.id.listViewDialog);
        Button button = (Button) dialog.findViewById(R.id.buttonDialog);

        List<Container> listaContainers = containerDAO.getAll();
        adapterContainer = new AdapterListViewContainerDialog(ObraDetalhadaEditActivity.this, listaContainers);
        listViewDialog.setAdapter(adapterContainer);
        listViewDialog.setOnItemClickListener(cliqueCurtoContainer());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void adicionarTags(View v) {

        final Dialog dialog= new Dialog(ObraDetalhadaEditActivity.this);
        dialog.setTitle(getString(R.string.adicionar_tag));
        dialog.setContentView(R.layout.h_custom_dialog_lista);

        ListView listViewDialog = (ListView) dialog.findViewById(R.id.listViewDialog);
        Button button = (Button) dialog.findViewById(R.id.buttonDialog);

        List<Tag> listaTags = tagDao.getListaTags();
        adapterListViewTag = new AdapterListViewTagDialog(ObraDetalhadaEditActivity.this, listaTags);
        listViewDialog.setAdapter(adapterListViewTag);
        listViewDialog.setOnItemClickListener(cliqueCurtoTag());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case Constantes.SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {

                    //capturando o resultado do scanner
                    String contents = data.getStringExtra("SCAN_RESULT");
                    editISBN.setText(contents);
                    Toast.makeText(this, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "Falha ao realizar esta ação!", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constantes.CAMERA_REQUEST:

                if (resultCode == RESULT_OK) {

                    //setando imageview com a imagem pega pela camera
                    /*
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imgCapa.setImageBitmap(photo);
                    */

                    File imgFile = new  File(pictureImagePath);

                    if(imgFile.exists()) {
                        foto = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        foto=Bitmap.createBitmap(foto, 0, 0, foto.getWidth(), foto.getHeight(), matrix, true);

                        imgCapa.setImageBitmap(foto);
                        imgCapa.setScaleX(2);
                        imgCapa.setScaleY(2);
                    }

                    Toast.makeText(this, "Ação realizada com sucesso!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "Falha ao realizar esta ação!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public AdapterView.OnItemClickListener cliqueCurtoTag() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Tag tag= (Tag) adapterListViewTag.getItem(position);

                //pega todos os componentes do layout e verifica se já foram inseridos
                final int childCount = layoutTags.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View v = layoutTags.getChildAt(i);

                    if(v.getTag()==tag.getIdTag()){
                        return;
                    }
                }

                final Button tagButton=new Button(ObraDetalhadaEditActivity.this);
                LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

                tagButton.setLayoutParams(layoutParams);
                tagButton.setText(tag.getNomeTag());
                tagButton.setTag(tag.getIdTag());
                tagButton.setBackgroundResource(R.drawable.tags_custom_view);

                tagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutTags.removeView(tagButton);
                    }
                });

                layoutTags.addView(tagButton);
            }
        };
    }

    public AdapterView.OnItemClickListener cliqueCurtoContainer() {

        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Container container= (Container) adapterContainer.getItem(position);

                TextViewContainer.setText(container.getNomeContainer());
                TextViewContainer.setTag(container.getIdContainer());
            }
        };
    }

    public void preencheCampos (Obra obra){

        editTitulo.setText(obra.getTitulo());
        editAutor.setText(obra.getAutor());
        editISBN.setText(obra.getIsbn());
        editAnoPublicacao.setText(String.valueOf(obra.getAnoPublicacao()));
        editEditora.setText(obra.getEditora());
        editDescricao.setText(obra.getDescricao());
        emprestado.setChecked(obra.isEmprestado());
        imgCapa.setImageBitmap(obra.getCapa());
        imgCapa.setScaleX(1.5F);
        imgCapa.setScaleY(1.5F);

        if(obra.getContainer()!=null){
            TextViewContainer.setText(obra.getContainer().getNomeContainer());
            TextViewContainer.setTag(obra.getContainer().getIdContainer());
        }
    }

}
