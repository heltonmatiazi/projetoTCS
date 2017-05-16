package br.com.senac.cademeulivro.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ObraTagDAO {
    private SQLiteDatabase mDatabaseHelper;

    public long insert(Integer idObra, Integer idTag) {
        ContentValues content = new ContentValues();
        content.put("obra_id", idObra);
        content.put("tag_id", idTag);
        return mDatabaseHelper.insert("ObraTag", null, content);
    }

    public int delete(Integer idObra, Integer idTag) {
        return mDatabaseHelper.delete("ObraTag", "obra_id = ? and tag_id = ?", new String[]{idObra.toString(), idTag.toString()});
    }

}
