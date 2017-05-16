package br.com.senac.cademeulivro.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.senac.cademeulivro.model.Tag;

public class TagDAO {
    private SQLiteDatabase mDatabaseHelper;

    public long insert(Tag t) {
        return mDatabaseHelper.insert("Tag", null, getContentFrom(t));
    }

    public void update(Tag t) {
        mDatabaseHelper.update("Tag",getContentFrom(t), "_id = ?", new String[] { t.getIdTag().toString() });
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Tag", "_id = ?", new String[] { id.toString() });
    }

    public Tag getById(Integer id) {
        Cursor cursor = mDatabaseHelper.query("Tag",null, "_id = ?", new String[] { id.toString() },null,null,null);
        return getTag(cursor);
    }


    private ContentValues getContentFrom(Tag t) {
        ContentValues content = new ContentValues();
        content.put("nomeTag", t.getNomeTag());
        content.put("corHex", t.getCorHex());
        //content.put("totalUsos", t.getTotalUsos()); //**** implementar lógica da contagem

        return content;
    }

    private Tag getTag(Cursor cursor) {
        Tag tag = new Tag();
        tag.setNomeTag(cursor.getString(cursor.getColumnIndex("nomeTag")));
        tag.setIdTag(cursor.getInt(cursor.getColumnIndex("_id")));
        tag.setCorHex(cursor.getString(cursor.getColumnIndex("corHex")));
        tag.setTotalUsos(cursor.getInt(cursor.getColumnIndex("totalUsos")));

        return tag;

    }
}
