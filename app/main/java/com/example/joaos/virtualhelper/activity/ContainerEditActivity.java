package com.example.joaos.virtualhelper.activity;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joaos.virtualhelper.util.DatePickerFragment;
import com.example.joaos.virtualhelper.R;

public class ContainerEditActivity extends AppCompatActivity {

    private ImageView imgCont;
    private final int[]  imagens={R.drawable.armario_icon,R.drawable.caixa_icon,R.drawable.estante_icon,R.drawable.prateleiras_icon};
    private final String[] iconesNomes={"Armário","Caixa","Estante","Prateleiras"};
    private EditText editNome,editLocal;
    private TextView tvIcone;
    private ImageButton imgBUltLimp;
    private int posicao=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_edit);

        editNome= (EditText) findViewById(R.id.editNomeCont);
        editLocal= (EditText) findViewById(R.id.editLocalCont);
        imgBUltLimp= (ImageButton) findViewById(R.id.imgBUltimaLimpCont);
        tvIcone= (TextView) findViewById(R.id.textIconeContEdit);
        imgCont= (ImageView) findViewById(R.id.imageViewContEdit);


        //temporario
        Bundle params=getIntent().getExtras();
        if(params!=null) {
            posicao = params.getInt("posicaoImagem");
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






}
