package iut.unice.fr.geocatching.Views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleMap mMap;
    private ArrayList<LatLng> listMarker = new ArrayList<>();
    private  ArrayList<Polygon> listPolygon = new ArrayList<>();
    private Marker m;
    private Polygon polygon;
    private int compteur = 0;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
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
    public void onMapReady(final GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new LatLng(43.616345d,7.072789d), true);
        Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new LatLng(43.620796d,7.070508d), true);
        Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new LatLng(43.620007d,7.065029d), true);
        Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new LatLng(43.616830d,7.076904d), true);
        mMap = googleMap;

        // Création d'un eliste de joueurs pour récupérer les position
        ArrayList<Joueur> playerPositionList = new ArrayList<>();
        playerPositionList.add(joueur1);
        playerPositionList.add(joueur2);
        playerPositionList.add(joueur3);
        playerPositionList.add(joueur4);
        //mMap.setMyLocationEnabled(true);

        // Position en dur
        /**
         * =================== Localisation de tous les joueurs ==========================
         */
        for (Joueur joueur: playerPositionList) {
            mMap.addMarker(new MarkerOptions()
                    .position(joueur.getPosition())
                    .title(joueur.getUsername())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
        }

        /*LatLng iut = new LatLng(43.616400, 7.071884);
        CameraPosition target = CameraPosition.builder().target(iut).zoom(14).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));*/
        GoogleMap.OnMapLongClickListener OnClickObject2 = new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                compteur = compteur+1;
                m = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Pointeur "+(compteur)));


                listMarker.add(m.getPosition());

                if(listMarker.size() >= 3) {
                    if(polygon != null) {
                        polygon.remove();
                        polygon = mMap.addPolygon(new PolygonOptions()
                                .addAll(listMarker)
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(100,255,0,0)));
                        polygon.setClickable(true);
                    }
                    else {
                        polygon = mMap.addPolygon(new PolygonOptions()
                                .addAll(listMarker)
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(100,255,0,0)));
                        polygon.setClickable(true);
                    }
                }
            }
        };
        mMap.setOnMapLongClickListener(OnClickObject2);


        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                listMarker.remove(marker.getPosition());
                marker.remove();
                if(polygon != null && listMarker.size() > 1) {
                    polygon.remove();
                    polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(listMarker)
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(100,255,0,0)));
                    polygon.setClickable(true);
                }

                if(listMarker.size() == 2 && polygon != null) {
                    polygon.remove();
                }

                if(listMarker.size() == 0) {
                    compteur = 0;
                }
            }
        });

        googleMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {

            @Override
            public void onPolygonClick(Polygon polygon) {
                polygon.remove();
                polygon = mMap.addPolygon(new PolygonOptions()
                        .addAll(polygon.getPoints())
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(100,255,0,0)));
                listPolygon.add(polygon);
                listMarker.clear();
            }
        });

        //Initialisation des Services Google Play

        // Verification de la version du SDK pour les permissions
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Met un marqueur à la position actuelle du joueur
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


        //Mouvement et zoom de la camera sur la position du joueur
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop les mises a jour de la localisation
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //Verification des permissions pour la localisation
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Demande à l'utilisateur s'il veut une explication sur la demande de permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Affiche une explication a l'utilisateur *asynchronously*,
                //Une fois que l'utilisateur a vu l'explication, on lui redemande l'autorisation

                //Demande de permission pour la localisation
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                //Aucune explication demandee, on affiche la demancde de permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // Si la demande est refusee, le tableau est vide
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission acceptee.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission refusee, la fonctionnalité est desactivee.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}
