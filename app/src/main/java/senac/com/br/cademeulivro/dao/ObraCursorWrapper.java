package senac.com.br.cademeulivro.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.sql.Date;

import senac.com.br.cademeulivro.model.Obra;

public class ObraCursorWrapper extends CursorWrapper {
    private Obra o;
    public ObraCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Obra getObra(){
        o = new Obra();
        o.setTitulo(getString(getColumnIndex("titulo")));
        o.setAutor(getString(getColumnIndex("autor")));
        o.setEditora(getString(getColumnIndex("editora")));
        o.setCapa(getBlob(getColumnIndex("capa")));
        o.setDescricao(getString(getColumnIndex("descricao")));
        o.setAnoPublicacao(getInt(getColumnIndex("anoPublicacao")));
        o.setEmprestado(getInt(getColumnIndex("emprestado")) == 1);
        o.setIsbn(getString(getColumnIndex("isbn")));
        return o;
    }

}
