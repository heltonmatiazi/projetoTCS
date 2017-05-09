package senac.com.br.cademeulivro.dao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

import senac.com.br.cademeulivro.model.Container;


public class ContainerDAO {
    private SQLiteDatabase mDatabaseHelper;

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


    private ContentValues getContentFrom(Container c) {
        ContentValues content = new ContentValues();
        //content.put("icone", c.getIconeContainer()); //*****
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
        //container.setIconeContainer(cursor.getString(cursor.getColumnIndex("icone")));

        return container;

    }

}


