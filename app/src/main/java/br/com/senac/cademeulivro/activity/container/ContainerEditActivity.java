package br.com.senac.cademeulivro.activity.container;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.senac.cademeulivro.R;
import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.util.DatePickerFragment;


public class ContainerEditActivity extends AppCompatActivity {

    private ImageView imgCont;
    private final int[]  imagens={R.drawable.container_armario_icon,R.drawable.container_caixa_icon,R.drawable.container_estante_icon,R.drawable.container_prateleiras_icon};
    private final String[] iconesNomes={"Armário","Caixa","Estante","Prateleiras"};
    private EditText editNome,editLocal;
    private TextView tvIcone;
    private ImageButton imgBUltLimp;
    private int posicao=1;
    private Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_activity_container_edit);

        editNome= (EditText) findViewById(R.id.editNomeCont);
        editLocal= (EditText) findViewById(R.id.editLocalCont);
        tvIcone= (TextView) findViewById(R.id.textIconeContEdit);
        imgCont= (ImageView) findViewById(R.id.imageViewContEdit);


        Bundle params=getIntent().getExtras();

        if(params!=null) {
            container= (Container) params.getSerializable("container");
            preencheCampos(container);
        }

        imgCont.setImageResource(imagens[posicao]);
        tvIcone.setText(iconesNomes[posicao]);

    }


    public void preencheCampos(){

        //metodo conecta com bo,dao e preenche os edits

    }


    public void mostrarDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void containerSalvar(View v){

        //chama bo para registrar no banco

        finish();
    }

    public void containerCancelar(View v){
        finish();
    }

    public void preencheCampos(Container container){

        editNome.setText(container.getNomeContainer());
        editLocal.setText(container.getLocalContainer());
        tvIcone.setText(container.getContainerTipos().getTipoIcone());
    }

}
