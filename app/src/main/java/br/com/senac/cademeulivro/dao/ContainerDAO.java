package br.com.senac.cademeulivro.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.senac.cademeulivro.model.Container;
import br.com.senac.cademeulivro.model.ContainerTipos;

//refactor com id biblioteca
public class ContainerDAO {
    private SQLiteDatabase mDatabaseHelper;

    public ContainerDAO(SQLiteDatabase databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    public long insert(Container c) {
        return mDatabaseHelper.insert("Container", null, getContentFrom(c));
    }

    public void update(Container c) {
        mDatabaseHelper.update("Container",getContentFrom(c), "_id = ?", new String[] { c.getIdContainer().toString() });
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Container", "_id = ?", new String[] { id.toString() });
    }

    public Container getById(Integer id) {
        Cursor cursor = mDatabaseHelper.query("Container",null, "_id = ?", new String[] { id.toString() },null,null,null);
        return getContainer(cursor);
    }

    public List<Container> getAll() {
        List<Container> lista = new ArrayList<>();
        Cursor cursor = mDatabaseHelper.query("Container",null, null, null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Container c = getContainer(cursor);
            lista.add(c);
            cursor.moveToNext();
        }
        cursor.close();;
        return lista;
    }

    private ContainerTipos getTipo(Integer id) {
        ContainerTipos tp = new ContainerTipos();
        Cursor cursor = mDatabaseHelper.query("ContainerTipos",null, "_id = ?", new String[] { id.toString() },null,null,null);
        tp.setTipoNome(cursor.getString(cursor.getColumnIndex("nomeTipo")));
        tp.setTipoIcone(cursor.getString(cursor.getColumnIndex("iconeTipo")));
        tp.set_id(id);
        return tp;
    }

    private ContentValues getContentFrom(Container c) {
        ContentValues content = new ContentValues();
        content.put("tipo", c.getContainerTipos().get_id());
        content.put("nomeContainer", c.getNomeContainer());
        content.put("local", c.getLocalContainer());
        content.put("ultimaModificacao", c.getUltimaModificacao().toString());
        content.put("biblioteca_id", c.getIdBiblioteca());

        return content;
    }

    private Container getContainer(Cursor cursor) {
        Container container = new Container();
        container.setNomeContainer(cursor.getString(cursor.getColumnIndex("nomeContainer")));
        container.setLocalContainer(cursor.getString(cursor.getColumnIndex("local")));
        container.setUltimaModificacao(Date.valueOf(cursor.getString(cursor.getColumnIndex("ultimaModificacao"))));
        container.setIdContainer(cursor.getInt(cursor.getColumnIndex("_id")));
        container.setIdBiblioteca(cursor.getInt(cursor.getColumnIndex("biblioteca_id")));
        container.setContainerTipos(getTipo(cursor.getInt(cursor.getColumnIndex("tipo_id"))));

        return container;

    }

}


