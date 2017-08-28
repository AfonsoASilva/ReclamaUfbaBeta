package br.com.reclamaufbabeta.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

import br.com.reclamaufbabeta.Conexao.Database;
import br.com.reclamaufbabeta.modelo.Post;
import br.com.reclamaufbabeta.modelo.Usuario;

public class UsuarioDAO extends Database {

    private final String TABLE_NAME = "usuarios";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";

    public UsuarioDAO(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_NOME + " TEXT, " +
                COLUNA_EMAIL + " TEXT, " +
                COLUNA_SENHA + " TEXT "+
                //COLUNA_BYTES_FOTO + " BLOB" +
                ");";

        db.execSQL(sql);
    }

    public void insert(Usuario usuario) throws Exception {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_SENHA, usuario.getSenha());
        getDatabase().insert(TABLE_NAME, null, values);
    }

    public void update(Usuario usuario) throws Exception {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_SENHA, usuario.getSenha());
        getDatabase().update(TABLE_NAME, values, COLUNA_ID + " = ?", new String[] { "" + usuario.getId() });
    }

    public Usuario buscarUsuarioById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUNA_ID+"=?", new String[]{String.valueOf(id)});
        Usuario usuario = new Usuario();
        if (c.moveToNext()) {
            usuario.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
            usuario.setNome(c.getString(c.getColumnIndex(COLUNA_NOME)));
            usuario.setEmail(c.getString(c.getColumnIndex(COLUNA_EMAIL)));
            usuario.setEmail(c.getString(c.getColumnIndex(COLUNA_EMAIL)));
            //post.setPhotoBytes(c.getBlob(c.getColumnIndex(COLUNA_BYTES_FOTO)));

            return usuario;
        }
        return null;
    }

    public List<Usuario> findAll() throws Exception {
        List<Usuario> retorno = new ArrayList<Usuario>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(criaUsuario(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

     public Usuario criaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex(COLUNA_ID));
        String nome = cursor.getString(cursor.getColumnIndex(COLUNA_NOME));
        String email = cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL));
        String senha = cursor.getString(cursor.getColumnIndex(COLUNA_SENHA));
        return new Usuario(id, nome, email, senha);
    }

    public Usuario findByLogin(String email, String senha) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE "+COLUNA_EMAIL+" = ? AND "+COLUNA_SENHA+" = ?";
        String[] selectionArgs = new String[] { email, senha };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return criaUsuario(cursor);
    }

}