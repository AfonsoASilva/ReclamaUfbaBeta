package br.com.reclamaufbabeta.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

import br.com.reclamaufbabeta.modelo.Post;

public class PostDAO extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Ocorrencias";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_TITULO = "titulo";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_BYTES_FOTO = "bytesFoto";
    private static final String COLUNA_LATITUDE = "latitude";
    private static final String COLUNA_LONGITUDE = "longitude";

    public PostDAO(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_TITULO + " TEXT, " +
                COLUNA_DESCRICAO + " TEXT, " +
                COLUNA_LATITUDE + " REAL, " +
                COLUNA_LONGITUDE + " REAL, " +
                COLUNA_BYTES_FOTO + " BLOB" +
                ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Post post) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(COLUNA_TITULO, post.getTitulo());
        dados.put(COLUNA_DESCRICAO, post.getDescricao());
        dados.put(COLUNA_BYTES_FOTO, post.getPhotoBytes());
        Location localizacao = post.getLocalizacao();
        if (localizacao != null) {
            dados.put(COLUNA_LATITUDE, localizacao.getLatitude());
            dados.put(COLUNA_LONGITUDE, localizacao.getLongitude());
        }

        long id = db.insert(TABLE_NAME, null, dados);
    }

    public List<Post> buscaposts() {
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Post> posts = new ArrayList<Post>();
        while (c.moveToNext()) {
            Post post = new Post();
            post.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
            post.setTitulo(c.getString(c.getColumnIndex(COLUNA_TITULO)));
            post.setDescricao(c.getString(c.getColumnIndex(COLUNA_DESCRICAO)));
            post.setPhotoBytes(c.getBlob(c.getColumnIndex(COLUNA_BYTES_FOTO)));
            double latitude = c.getDouble(c.getColumnIndex(COLUNA_LATITUDE));
            double longitude = c.getDouble(c.getColumnIndex(COLUNA_LONGITUDE));
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            post.setLocalizacao(location);
            posts.add(post);
        }
        c.close();
        return posts;
    }

    public void deleta(Post post) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {
                String.valueOf(post.getId())
        };

        db.delete(TABLE_NAME, COLUNA_ID + "= ?", params);

    }

    public void altera(Post post) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put(COLUNA_TITULO, post.getTitulo());
        dados.put(COLUNA_DESCRICAO, post.getDescricao());
        dados.put(COLUNA_BYTES_FOTO, post.getPhotoBytes());
        ;
        dados.put(COLUNA_LATITUDE, post.getLocalizacao().getLatitude());
        dados.put(COLUNA_LONGITUDE, post.getLocalizacao().getLongitude());

        String[] params = {
                String.valueOf(post.getId())
        };

        db.update(TABLE_NAME, dados, COLUNA_ID + " = ?", params);
    }

    public Post buscarPostById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUNA_ID+"=?", new String[]{String.valueOf(id)});
        Post post = new Post();
        if (c.moveToNext()) {
            post.setId(c.getInt(c.getColumnIndex(COLUNA_ID)));
            post.setTitulo(c.getString(c.getColumnIndex(COLUNA_TITULO)));
            post.setDescricao(c.getString(c.getColumnIndex(COLUNA_DESCRICAO)));
            post.setPhotoBytes(c.getBlob(c.getColumnIndex(COLUNA_BYTES_FOTO)));
            double latitude = c.getDouble(c.getColumnIndex(COLUNA_LATITUDE));
            double longitude = c.getDouble(c.getColumnIndex(COLUNA_LONGITUDE));
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            post.setLocalizacao(location);
            return post;
        }
        return null;
    }
}
