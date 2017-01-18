package iut.unice.fr.geocatching.Views;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
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

        final Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new LatLng(43.616345d,7.072789d), true);
        Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new LatLng(43.620796d,7.070508d), true);
        Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new LatLng(43.620007d,7.065029d), true);
        Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new LatLng(43.616830d,7.076904d), true);

        // Création d'un eliste de joueurs pour récupérer les position
        ArrayList<Joueur> playerPositionList = new ArrayList<>();
        playerPositionList.add(joueur1);
        playerPositionList.add(joueur2);
        playerPositionList.add(joueur3);
        playerPositionList.add(joueur4);


        LatLng iut = new LatLng(43.616400, 7.071884);
        CameraPosition target = CameraPosition.builder().target(iut).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));
        GoogleMap.OnMapLongClickListener OnClickObject2 = new GoogleMap.OnMapLongClickListener() {

            public void onMapLongClick(LatLng latLng) {
                compteur = compteur+1;
                mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Pointeur "+(compteur)));
                //joueur1.getPosition().latitude
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

        /*if(listMarker.size() > 4) {
            Polygon polygon = mMap.addPolygon(new PolygonOptions()
                    .add(listMarker.get(0).getPosition(), listMarker.get(1).getPosition(),listMarker.get(2).getPosition(), listMarker.get(3).getPosition())
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));
        }*/

        /**
         * =================== Localisation de tous les joueurs ==========================
         */
        for (Joueur joueur: playerPositionList) {
            mMap.addMarker(new MarkerOptions()
                    .position(joueur.getPosition())
                    .title(joueur.getUsername()));
        }




    }







}
