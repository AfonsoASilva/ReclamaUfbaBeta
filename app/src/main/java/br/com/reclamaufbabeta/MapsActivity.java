package br.com.reclamaufbabeta;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import br.com.reclamaufbabeta.DAO.PostDAO;
import br.com.reclamaufbabeta.modelo.Post;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<Marker, Post> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Button button = (Button) findViewById(R.id.addPost);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFormulario = new Intent(getApplicationContext(), InserePostActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        this.hashMap = new HashMap<>();

        PostDAO dao = new PostDAO(this);
        List<Post> posts = dao.buscaposts();
        dao.close();
        for (Post post : posts) {
            LatLng latLng = new LatLng(post.getLocalizacao().getLatitude(), post.getLocalizacao().getLongitude());
            Marker postMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(post.toString()));
            postMarker.showInfoWindow();
            hashMap.put(postMarker, post);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Post post = hashMap.get(marker);
                    Intent intent = new Intent(getApplicationContext(), InserePostActivity.class);
                    intent.putExtra("post_id", post.getId());
                    startActivity(intent);
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
            PostDAO dao = new PostDAO(this);
            List<Post> posts = dao.buscaposts();
            dao.close();
            for (Post post : posts) {
                LatLng latLng = new LatLng(post.getLocalizacao().getLatitude(), post.getLocalizacao().getLongitude());
                Marker postMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(post.toString()));
                postMarker.showInfoWindow();
                hashMap.put(postMarker, post);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

            }
        }
    }
}
