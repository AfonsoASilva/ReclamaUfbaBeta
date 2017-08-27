package br.com.reclamaufbabeta.Controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import br.com.reclamaufbabeta.InserePostActivity;
import br.com.reclamaufbabeta.R;
import br.com.reclamaufbabeta.modelo.Post;


public class PostController extends Activity {
    private Post post;
    private EditText titulo;
    private EditText descricao;
    private EditText localizacao;
    private final ImageView foto;

    public ImageView getFoto() {
        return foto;
    }

    private TextView like;
    private TextView deslike;

    public PostController(InserePostActivity activity) {
        post= new Post();
        titulo= (EditText) activity.findViewById(R.id.formulario_titulo);
        descricao= (EditText) activity.findViewById(R.id.formulario_descricao);
        foto= (ImageView) activity.findViewById(R.id.formulario_foto);
        localizacao= (EditText) activity.findViewById(R.id.formulario_localizacao);
    }
    public Post recebePost(Location location){
        post.setTitulo(titulo.getText().toString());
        post.setDescricao(descricao.getText().toString());
        post.setLocalizacao(location);
        Bitmap drawingCache = ((BitmapDrawable)foto.getDrawable()).getBitmap();
        if (drawingCache != null) {
            byte[] bytes = getBytes(drawingCache);
            post.setPhotoBytes(bytes);
        }
        return post;
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void descrevePost(Post post) {
        this.post = post;
        titulo.setText(post.getTitulo());
        descricao.setText(post.getDescricao());
        foto.setImageBitmap(getImage(post.getPhotoBytes()));
   }

   public void somaLike(Post post){
       this.post= post;
       int cont= post.getLike() + 1;
       post.setLike(cont);
   }

    public void carregaImagem(String caminhoFoto){
        if(caminhoFoto != null){
            Bitmap bitmap= BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido= Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(caminhoFoto);
        }
    }
}
