package com.example.joaos.virtualhelper.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;

import com.example.joaos.virtualhelper.R;

public class ObraDetalhadaActivity extends AppCompatActivity {

    FloatingActionButton fbMain,fb1,fb2;
    Animation FabOpen,FabClose,FabRClockWise,FabRantiClockWise;
    boolean isOpen=false;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_detalhada);
        //setTitle(----); pegar nome da obra

        checkBox= (CheckBox) findViewById(R.id.CheckBoxEmprestado);


    }


    public void obraDetalhadaEditar(View v){

        //chamar obraDetalhadaEdit prenchendo os edits


    }

    public void obraDetalhadaVoltar(View v){ finish(); }

}
