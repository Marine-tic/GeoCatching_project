package iut.unice.fr.geocatching.Views;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.R;

import static android.R.id.list;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Joueurs en dur en attendant le provider du Webservice
       /* try {
            Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new Point(43.616345d,7.072789d), true);
            Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new Point(43.620796d,7.070508d), true);
            Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new Point(43.620007d,7.065029d), true);
            Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new Point(43.616830d,7.076904d), true);
            
            // Création d'un eliste de joueurs pour récupérer les position
            ArrayList<Joueur> playerPositionList = new ArrayList<>();
            playerPositionList.add(joueur1);
            playerPositionList.add(joueur2);
            playerPositionList.add(joueur3);
            playerPositionList.add(joueur4);
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/


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
        GoogleMap.OnMapClickListener OnClickObject2 = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng));
                //joueur1.getPosition().latitude
            }
        };
        mMap.setOnMapClickListener(OnClickObject2);



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
