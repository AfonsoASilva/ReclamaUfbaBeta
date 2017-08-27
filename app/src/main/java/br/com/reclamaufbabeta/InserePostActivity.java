package br.com.reclamaufbabeta;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import br.com.reclamaufbabeta.Controller.PostController;
import br.com.reclamaufbabeta.DAO.PostDAO;
import br.com.reclamaufbabeta.modelo.Post;


public class InserePostActivity extends AppCompatActivity {
    private static final int COD_CAMERA = 678;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private PostController postController;
    private String caminhoFoto;
    private ImageView foto;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;
    private static final int REQUEST_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_FINE_LOCATION);

        setContentView(R.layout.activity_insere_post);
        Button botaoSalvar = (Button) findViewById(R.id.menu_formulario_salvar);
        Button botaoCamera = (Button) findViewById(R.id.formulario_button_Camera);
        this.foto = (ImageView) findViewById(R.id.formulario_foto);

        //recupera informações para edição
        Intent intent = getIntent();
        int id = intent.getIntExtra("post_id", 0);
        Post post = null;
        if (id != 0) {
            PostDAO postDAO = new PostDAO(getApplicationContext());
            post = postDAO.buscarPostById(id);
        }
        postController = new PostController(this);
        if (post != null) {
            postController.descrevePost(post);
        }

        //Função da foto
        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }

        });

        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(InserePostActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(InserePostActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(InserePostActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                ActivityCompat.requestPermissions(InserePostActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return;
        }
        this.mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocation = location;
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_salvar:
                savePost();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePost() {
        if (currentLocation == null) {
            Toast.makeText(InserePostActivity.this, "Espere até o gps carregar a localização atual.", Toast.LENGTH_SHORT).show();
        }
        Post post = postController.recebePost(currentLocation);
        PostDAO dao = new PostDAO(this);
        if (post.getId() == 0) {
            dao.insere(post);
        } else {
            dao.altera(post);
        }
        dao.close();
        Toast.makeText(InserePostActivity.this, "Post " + post.getTitulo() + " salvo!", Toast.LENGTH_SHORT).show();
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);
        }
    }

    private void loadPermissions(String perm,int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                }
                else{
                    // no granted
                }
                return;
            }

        }

    }
}