package br.com.reclamaufbabeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.reclamaufbabeta.DAO.PostDAO;
import br.com.reclamaufbabeta.modelo.Post;

public class ListaPostsActivity extends AppCompatActivity {

    private ListView listaPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_posts);

        //Carrega lista de Posts da Activity
        listaPosts = (ListView) findViewById(R.id.lista_posts);

        PostDAO dao = new PostDAO(this);
        List<Post> posts = dao.buscaposts();
        dao.close();

        Button novoPost = (Button) findViewById(R.id.addPost);
        novoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(getApplicationContext(), InserePostActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
        registerForContextMenu(listaPosts);
    }

    private void carregaLista() {
        PostDAO dao = new PostDAO(this);
        List<Post> posts = dao.buscaposts();
        dao.close();
        ArrayAdapter<Post> adapter =
                new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1, posts);
        listaPosts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo x) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) x;
        final Post post = (Post) listaPosts.getItemAtPosition(info.position);
        MenuItem editarPost = menu.add("Editar Post");
        //Intent intentEditarPost= new Intent(Intent.ACTION_VIEW);
        Intent intentEditarPost = new Intent(ListaPostsActivity.this, InserePostActivity.class);
        intentEditarPost.putExtra("post", post);
        startActivity(intentEditarPost);
            /*MenuItem visualizarPost= menu.add("Visualizar Post");
            Intent intentVisualizarPost = new Intent(ListaPostsActivity.this, PostActivity.class);
            intentVisualizarPost.putExtra("post", post);
            startActivity(intentVisualizarPost);*/

    }
}
