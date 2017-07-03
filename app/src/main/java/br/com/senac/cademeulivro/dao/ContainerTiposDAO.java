package br.com.senac.cademeulivro.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import br.com.senac.cademeulivro.model.ContainerTipos;


/**
 * Created by Didi on 5/21/2017.
 */

public class ContainerTiposDAO {
    private SQLiteDatabase mDatabase;

    public ContainerTiposDAO(SQLiteDatabase database) {
        mDatabase = database;
    }

    public List<ContainerTipos> getContainersDefault() {
        List<ContainerTipos> containers = new ArrayList<>();
        Cursor cursor = mDatabase.query("ContainerTipos", null, null, null, null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Integer tipoIcone = cursor.getInt(cursor.getColumnIndex("tipoIcone"));
            String tipoNome =cursor.getString(cursor.getColumnIndex("tipoNome"));
            Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
            containers.add(new ContainerTipos(id,tipoNome,tipoIcone));
            cursor.moveToNext();
        }
        cursor.close();

        return containers;
    }
}
