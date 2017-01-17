package iut.unice.fr.geocatching.Views;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ArrayList<LatLng> listMarker = new ArrayList<>();
    private Marker m;
    private int compteur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Joueurs en dur en attendant le provider du Webservice
        try {
            Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new Point(43.616345d,7.072789d), true);
            Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new Point(43.620796d,7.070508d), true);
            Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new Point(43.620007d,7.065029d), true);
            Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new Point(43.616830d,7.076904d), true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;

        LatLng iut = new LatLng(43.616400, 7.071884);
        CameraPosition target = CameraPosition.builder().target(iut).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        GoogleMap.OnMapLongClickListener OnClickObject2 = new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                compteur = compteur+1;
                m = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Pointeur "+(compteur)));

                listMarker.add(m.getPosition());

                if(listMarker.size() >= 3) {
                    Polygon polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(listMarker)
                            .strokeColor(Color.RED)
                            .fillColor(Color.BLUE));
                }
            }
        };
        mMap.setOnMapLongClickListener(OnClickObject2);

        /*
        // Add a marker in S1ydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        GoogleMap.OnMapClickListener OnClickObject2 = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        };
        mMap.setOnMapClickListener(OnClickObject2);
        */
    }
}
