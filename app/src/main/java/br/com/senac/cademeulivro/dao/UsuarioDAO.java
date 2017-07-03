package br.com.senac.cademeulivro.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.senac.cademeulivro.model.Usuario;


public class UsuarioDAO {
    private SQLiteDatabase mDatabaseHelper;


    public long insert(Usuario u) {
        return mDatabaseHelper.insert("Usuario", null, getContentFrom(u));
    }

    public void update(Usuario u) {
        mDatabaseHelper.update("Usuario",getContentFrom(u), "_id = ?", new String[] { u.getIdUsuario().toString() });
    }

    public int delete(Integer id) {
        return mDatabaseHelper.delete("Usuario", "_id = ?", new String[] { id.toString() });
    }

    public Usuario getById(Integer id) {
        Cursor cursor = mDatabaseHelper.query("Usuario",null, "_id = ?", new String[] { id.toString() },null,null,null);
        return getUsuario(cursor);
    }


    private ContentValues getContentFrom(Usuario u) {
        ContentValues content = new ContentValues();
        content.put("nomeUsuario", u.getNomeUsuario());

        return content;
    }

    private Usuario getUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(cursor.getString(cursor.getColumnIndex("nomeUsuario")));
        usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndex("_id")));

        return usuario;

    }

}
