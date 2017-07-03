package br.com.senac.cademeulivro.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

import br.com.senac.cademeulivro.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CadeMeuLivro.db";
    private static int DATABASE_VERSION = 1;
    private static SQLiteDatabase sInstancia;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //TODO deixar banco de acordo com a modelagem
    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria tabela Usuario
        db.execSQL("CREATE TABLE Usuario(_id INTEGER PRIMARY KEY, nomeUsuario TEXT);");

        //cria tabela Biblioteca
        db.execSQL("CREATE TABLE Biblioteca(_id INTEGER PRIMARY KEY, usuario_id INTEGER, FOREIGN KEY(usuario_id) REFERENCES Usuario(_id));");

        //cria tabela Container
        db.execSQL("CREATE TABLE Container(_id INTEGER PRIMARY KEY, nomeContainer TEXT, tipo_id INTEGER, local TEXT, ultimaModificacao TEXT, biblioteca_id INTEGER, FOREIGN KEY(biblioteca_id) REFERENCES Biblioteca(_id), FOREIGN KEY(tipo_id) REFERENCES ContainerTipos(_id));");

        //cria e popula tabela ContainerTipos
        db.execSQL("CREATE TABLE ContainerTipos(_id INTEGER PRIMARY KEY, tipoIcone INTEGER, tipoNome TEXT);");

        Map<Integer,String> valoresDefault = new HashMap<>();
        valoresDefault.put(R.drawable.container_armario_icon,"Armário");
        valoresDefault.put(R.drawable.container_caixa_icon,"Caixa");
        valoresDefault.put(R.drawable.container_estante_icon,"Estante");
        valoresDefault.put(R.drawable.container_prateleiras_icon,"Prateleira");

        ContentValues contentValues = new ContentValues();
        for (Map.Entry<Integer, String> entry : valoresDefault.entrySet())
        {
            contentValues.put("tipoIcone",entry.getKey());
            contentValues.put("tipoNome", entry.getValue());
            db.insert("ContainerTipos",null, contentValues);
        }

        //cria tabela Obra
        db.execSQL("CREATE TABLE Obra(_id INTEGER PRIMARY KEY, autor TEXT, titulo TEXT, editora TEXT, emprestado INTEGER, capa BLOB, isbn TEXT, descricao TEXT, anoPublicacao INTEGER, container_id INTEGER, FOREIGN KEY(container_id) REFERENCES Container(_id));");

        //cria tabela Tag
        db.execSQL("CREATE TABLE Tag(_id INTEGER PRIMARY KEY, nomeTag TEXT, corHex TEXT, totalUsos INTEGER);");

        //cria tabela relacionando Tag e Obra
        db.execSQL("CREATE TABLE ObraTag(tag_id INTEGER, obra_id INTEGER, FOREIGN KEY(tag_id) REFERENCES Tag(_id), FOREIGN KEY(obra_id) REFERENCES Obra(_id));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static SQLiteDatabase newInstance(Context context) {
        if(sInstancia == null) {
            sInstancia = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }
        return sInstancia;
    }
}
