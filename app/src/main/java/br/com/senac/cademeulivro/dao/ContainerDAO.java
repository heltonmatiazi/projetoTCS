package br.com.senac.cademeulivro.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public long update(Container c) {
        return mDatabaseHelper.update("Container",getContentFrom(c), "_id = ?", new String[] { c.getIdContainer().toString() });
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Container", "_id = ?", new String[] { id.toString() });
    }

    public Container getById(Integer id) {
        Cursor cursor = mDatabaseHelper.query("Container",null, "_id = ?", new String[] { id.toString() },null,null,null);
        cursor.moveToFirst();
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
        cursor.close();
        return lista;
    }

    private ContainerTipos getTipo(Integer id) {
        ContainerTipos tp = new ContainerTipos();
        Cursor cursor = mDatabaseHelper.query("ContainerTipos",null, "_id = ?", new String[] { id.toString() },null,null,null);
        cursor.moveToFirst();
        tp.setTipoNome(cursor.getString(cursor.getColumnIndex("tipoNome")));
        tp.setTipoIcone(cursor.getInt(cursor.getColumnIndex("tipoIcone")));
        tp.set_id(id);
        cursor.close();
        return tp;
    }

    private ContentValues getContentFrom(Container c) {
        ContentValues content = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        content.put("tipo_id", c.getContainerTipos().get_id());
        content.put("nomeContainer", c.getNomeContainer());
        content.put("local", c.getLocalContainer());
        content.put("ultimaModificacao", df.format(c.getUltimaModificacao()));
        content.put("biblioteca_id", c.getIdBiblioteca());

        return content;
    }

    private Container getContainer(Cursor cursor) {
        Container container = new Container();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        container.setNomeContainer(cursor.getString(cursor.getColumnIndex("nomeContainer")));
        container.setLocalContainer(cursor.getString(cursor.getColumnIndex("local")));
        try {
            container.setUltimaModificacao(df.parse(cursor.getString(cursor.getColumnIndex("ultimaModificacao"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        container.setIdContainer(cursor.getInt(cursor.getColumnIndex("_id")));
        container.setIdBiblioteca(cursor.getInt(cursor.getColumnIndex("biblioteca_id")));
        container.setContainerTipos(getTipo(cursor.getInt(cursor.getColumnIndex("tipo_id"))));

        return container;

    }

}


