package br.gov.sp.fatecdiadema.bancodedados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes do DB:
    public static final String DATABASE_NAME = "dados.db";
    public static final String TABLE_NAME = "pessoas";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USUARIO";
    public static final String COL_3 = "SENHA";

    // Construtor da classe que chama o construtor da superclasse passando o contexto.
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    // Metodo que chama automaticamente ao criar o DB.
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USUARIO TEXT, SENHA INTEGER)");
    }

    // Metodo que chama quando o DB é atualizado para nova versão:
    @Override
    public void onUpgrade(SQLiteDatabase db, int  oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Metodo GRAVAR um novo registro no DB. Retorna true caso seja gravado com sucesso
    public boolean inserirDados(String usuario, int senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, usuario);
        contentValues.put(COL_3, senha);
        long resultado = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return resultado != -1;
    }

    // Metodo CONSULTAR um registro no DB. Retorna Cursor com o resultado.
    public Cursor obterSenhaPorUsuario(String usuario){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_3};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {usuario};
        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    // Metodo para atualizar os dados de um usuário no banco
    public boolean atualizarDados(String usuario, int novaSenha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, novaSenha);

        String whereClause = COL_2 + " = ?";
        String[] whereArgs = {usuario};

        int resultado = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
        return resultado > 0; // Retorna true se pelo menos 1 linha foi alterada
    }

    // Metodo para deletar um usuário do banco
    public boolean deletarUsuario(String usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = COL_2 + " = ?";
        String[] whereArgs = {usuario};

        int resultado = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return resultado > 0; // Retorna true se pelo menos 1 linha foi deletada
    }


}






