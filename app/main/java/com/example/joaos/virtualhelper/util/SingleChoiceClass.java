package com.example.joaos.virtualhelper.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by joaos on 22/04/2017.
 */
        //classe dialog de uma escolha
public class SingleChoiceClass extends DialogFragment{

    private final CharSequence[] items={ "escrivaninha",
            "craido mudo",
            "escravo",
            "armario",
            "estante-sala"};// irao ser pegos do banco
    private String selection;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Containers").setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                selection=(String)items[which];

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //conectar com bo e levar a dados para o banco
                Toast.makeText(getActivity(),"Ação realizada com sucesso!",Toast.LENGTH_SHORT).show();

            }
        });

            return builder.create();
    }




}
