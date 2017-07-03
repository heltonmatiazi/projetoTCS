package br.com.senac.cademeulivro.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.senac.cademeulivro.model.Obra;
import br.com.senac.cademeulivro.model.Tag;

public class ObraTagDAO {
    private SQLiteDatabase mDatabaseHelper;

    public ObraTagDAO(SQLiteDatabase databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    public long insert(Integer idObra, Integer idTag) {
        ContentValues content = new ContentValues();
        content.put("obra_id", idObra);
        content.put("tag_id", idTag);
        return mDatabaseHelper.insert("ObraTag", null, content);
    }

    public int delete(Integer idObra, Integer idTag) {
        return mDatabaseHelper.delete("ObraTag", "obra_id = ? and tag_id = ?", new String[]{idObra.toString(), idTag.toString()});
    }

    public int deleteTagFromAll(Integer idTag) {
        return mDatabaseHelper.delete("ObraTag", "tag_id = ?", new String[] { idTag.toString() });
    }

    public int deleteObraFromAll(Integer idObra){
        return mDatabaseHelper.delete("ObraTag", "obra_id = ?", new String[] { idObra.toString() });
    }

    public List<Tag> getByIdObra(Integer id) {

        Cursor cursor = mDatabaseHelper.rawQuery("SELECT * FROM ObraTag WHERE obra_id = " + id, null);

        List<Tag> lista=new ArrayList<>();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {

                Cursor cursorTags = mDatabaseHelper.rawQuery("SELECT * FROM Tag WHERE _id = " + cursor.getInt(cursor.getColumnIndex("tag_id")), null);

                lista.add(getTag(cursorTags));
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return lista;
    }

    public List<Obra> getByIdTag(Integer id) {

        Cursor cursor = mDatabaseHelper.rawQuery("SELECT * FROM ObraTag WHERE tag_id = " + id, null);

        List<Obra> lista=new ArrayList<>();

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Cursor cursorObras = mDatabaseHelper.rawQuery("SELECT * FROM Obra WHERE _id = " + cursor.getInt(cursor.getColumnIndex("obra_id")), null);

                if(cursor.moveToLast()==false){
                    return lista;
                }

                lista.add(getObra(cursorObras));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    private Obra getObra(Cursor cursor) {

        boolean valor=cursor.moveToLast();

        Obra o = new Obra();

        o.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
        o.setIdObra(cursor.getInt(cursor.getColumnIndex("_id")));
        o.setAutor(cursor.getString(cursor.getColumnIndex("autor")));
        o.setEditora(cursor.getString(cursor.getColumnIndex("editora")));

        if (cursor.getBlob(cursor.getColumnIndex("capa"))!=null) {
            //o.setCapa(ImageConverter.toBitmap(cursor.getBlob(cursor.getColumnIndex("capa"))));
        }else {
            o.setCapa(null);
        }

        o.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        o.setAnoPublicacao(cursor.getInt(cursor.getColumnIndex("anoPublicacao")));
        o.setEmprestado(cursor.getInt(cursor.getColumnIndex("emprestado")) == 1);
        o.setIsbn(cursor.getString(cursor.getColumnIndex("isbn")));

        return o;
    }

    private Tag getTag(Cursor cursor) {

        boolean valor=cursor.moveToLast();

        Tag tag = new Tag();
        tag.setNomeTag(cursor.getString(cursor.getColumnIndex("nomeTag")));
        tag.setIdTag((int) cursor.getLong(cursor.getColumnIndex("_id")));
        tag.setCorHex(cursor.getString(cursor.getColumnIndex("corHex")));
        tag.setTotalUsos(cursor.getInt(cursor.getColumnIndex("totalUsos")));

        return tag;
    }

    public int getAplicacoes(Integer id){

        Cursor cursor = mDatabaseHelper.rawQuery("SELECT * FROM ObraTag WHERE tag_id= " + id, null);
        int numero=cursor.getCount();

        return numero;
    }
}
