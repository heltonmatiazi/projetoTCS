package com.example.joaos.virtualhelper.activity.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.joaos.virtualhelper.R;
import com.example.joaos.virtualhelper.activity.ContainerEditActivity;

/**
 * Created by joaos on 22/04/2017.
 */

public class ContainersActivity extends Fragment implements View.OnClickListener {

    private ImageSwitcher ImgSw;
    private final int[]  imagens={R.drawable.armario_icon,R.drawable.caixa_icon,R.drawable.estante_icon, R.drawable.prateleiras_icon};
    private final String[] iconesNomes={"Armário","Caixa","Estante","Prateleiras"};
    private int posicao=1;
    private TextView tvIcone;
    private ImageButton direita,esquerda;
    private Button editar,excluir;

    //private FloatingActionButton fbMain;
    //private Animation FabOpen,FabClose;
    //private boolean isOpen=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_activity_containers, container, false);


        ImgSw= (ImageSwitcher)rootView.findViewById(R.id.imageSwitcherCont);
        tvIcone=(TextView)rootView.findViewById(R.id.TextViewIconeCont);
        direita= (ImageButton) rootView.findViewById(R.id.buttonRight);
        esquerda= (ImageButton) rootView.findViewById(R.id.buttonLeft);
        editar= (Button) rootView.findViewById(R.id.buttonEditar);
        excluir= (Button) rootView.findViewById(R.id.ButtonExcluir);

        excluir.setOnClickListener(this);
        editar.setOnClickListener(this);
        direita.setOnClickListener(this);
        esquerda.setOnClickListener(this);


        //controle de containers, centralizando a imagem e setando funcao dos botoes
        ImgSw.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {

                ImageView myView = new ImageView(rootView.getContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));

                return myView;
            }
        });
        ImgSw.setImageResource(imagens[posicao]);
        tvIcone.setText(iconesNomes[posicao]);

/*
        capturando o FAB e enviando sua animacao quando clicado
        fbMain= (FloatingActionButton) rootView.findViewById(R.id.fbMainContainer);
        FabOpen= AnimationUtils.loadAnimation(rootView.getContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(rootView.getContext(),R.anim.fab_close);
*/

        return rootView;
    }


    //setar os textviews segundo a imagem do container

    public void passaImagemDireita(View v){

        if(posicao!=3) {
            posicao++;
        }else{
            posicao=0;
        }

        ImgSw.setImageResource(imagens[posicao]);

    }

    public void passaImagemEsquerda(View v){

        if(posicao!=0) {
            posicao--;
        }else{
            posicao=3;
        }

        ImgSw.setImageResource(imagens[posicao]);

    }


    public void containerEditar(View v){

        Intent intent=new Intent(getActivity(),ContainerEditActivity.class);

        intent.putExtra("posicaoImagem",posicao);

        startActivityForResult(intent,1);
    }

    public void containerExcluir(View v){



    }
/*
    public void novoContainer(View v) {

        fbMain.startAnimation(FabOpen);

        Intent intent=new Intent(getActivity(),ContainerEditActivity.class);
        startActivity(intent);

        //chamar tela container edit

    }
*/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonLeft:
                passaImagemEsquerda(v);
                break;
            case R.id.buttonRight:
                passaImagemDireita(v);
                break;

            case R.id.buttonEditar:
                containerEditar(v);
                break;

            case R.id.ButtonExcluir:
                containerExcluir(v);
                break;
        }


    }
}
