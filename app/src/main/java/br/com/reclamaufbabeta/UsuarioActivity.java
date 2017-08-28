package br.com.reclamaufbabeta;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import br.com.reclamaufbabeta.Controller.PostController;
import br.com.reclamaufbabeta.Controller.UsuarioController;
import br.com.reclamaufbabeta.DAO.PostDAO;
import br.com.reclamaufbabeta.modelo.Post;
import br.com.reclamaufbabeta.modelo.Usuario;

public class UsuarioActivity extends AppCompatActivity {

    private static final int COD_CAMERA = 678;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private UsuarioController usuarioController;
    private String caminhoFoto;
    private ImageView foto;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;
    private static final int REQUEST_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_usuario);
        Button botaoSalvar = (Button) findViewById(R.id.menu_formulario_salvar);
//        Button botaoCamera = (Button) findViewById(R.id.formulario_button_Camera);
//        this.foto = (ImageView) findViewById(R.id.formulario_foto);
//
//        //recupera informações para edição
//        Intent intent = getIntent();
//        int id = intent.getIntExtra("post_id", 0);
//        Post post = null;
//        if (id != 0) {
//            PostDAO postDAO = new PostDAO(getApplicationContext());
//            post = postDAO.buscarPostById(id);
//        }
//
//        //Função da foto
//        botaoCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, 1);
//                }
//            }
//
//        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_usuario_salvar:
                this.save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {

        try{

            this.usuarioController = new UsuarioController(this);

            if (this.usuarioController.getUsuario().getId() == null) {
                this.usuarioController.insert();
            } else {
                this.usuarioController.update();
            }

            Toast.makeText(UsuarioActivity.this, "Usuário " + this.usuarioController.getUsuario().getNome() + " salvo!", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            foto.setImageBitmap(imageBitmap);
//        }
//    }

}
