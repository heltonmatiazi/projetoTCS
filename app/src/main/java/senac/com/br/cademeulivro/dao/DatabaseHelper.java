package senac.com.br.cademeulivro.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CadeMeuLivro.db";
    private static int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria tabela Usuario
        db.execSQL("CREATE TABLE Usuario(_id INTEGER PRIMARY KEY, nomeUsuario TEXT);");

        //cria tabela Biblioteca
        db.execSQL("CREATE TABLE Biblioteca(_id INTEGER PRIMARY KEY, ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
