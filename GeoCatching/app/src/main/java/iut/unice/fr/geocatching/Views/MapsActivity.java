package iut.unice.fr.geocatching.Views;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;
import iut.unice.fr.geocatching.Models.Equipe;
import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.Models.Zone;
import iut.unice.fr.geocatching.R;
import iut.unice.fr.geocatching.ViewsModels.VMMapsActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> listMarker = new ArrayList<>();
    private ArrayList<Marker> listMarkerV = new ArrayList<>();
    private ArrayList<Polygon> listTerrain = new ArrayList<>();
    private ArrayList<Polygon> listZone = new ArrayList<>();
    private Marker m = null;
    private Marker maPosition = null;
    private Polygon polygon = null;
    private Boolean detecter = true;
    private LatLng me;
    private VMMapsActivity vmMapsActivity;
    private String username;

    //Test Création
    private Equipe equipeTest = new Equipe("Equipe 1");
    private Zone zoneTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vmMapsActivity = new VMMapsActivity();
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //username = getIntent().getStringExtra("name");
        username = "test";
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(location != null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            me = new LatLng(latitude,longitude);
            vmMapsActivity.addJoueur(username, me);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            Toast.makeText(this, "Please check you have authorized the location permission", Toast.LENGTH_LONG).show();
        }

        /**
         * =================== Localisation de tous les joueurs ==========================
         */
        for (Joueur joueur : vmMapsActivity.getPlayerPositionList()) {
            mMap.addMarker(new MarkerOptions()
                    .position(joueur.getPosition())
                    .title(joueur.getUsername())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            );
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                if(listTerrain.size() == 0) {
                    m = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Supprimer").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    listMarkerV.add(m);

                    listMarker.add(m.getPosition());

                    if (listMarker.size() >= 3) {
                        if (polygon != null) {
                            polygon.remove();
                            polygon = mMap.addPolygon(new PolygonOptions()
                                    .addAll(listMarker)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(Color.argb(100, 0, 0, 255)));
                            polygon.setClickable(true);
                        } else {
                            polygon = mMap.addPolygon(new PolygonOptions()
                                    .addAll(listMarker)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(Color.argb(100, 0, 0, 255)));
                            polygon.setClickable(true);
                        }
                    }

                    if (listMarker.size() > 1) {
                        for (int i = 0; i < listMarkerV.size() - 1; i++) {
                            listMarkerV.get(i).setDraggable(false);
                            listMarkerV.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        }
                    }
                }
                else {
                    if (listTerrain.get(0).getStrokeColor() == Color.RED && listTerrain.get(0).getFillColor() == Color.argb(100, 255, 0, 0) && vmMapsActivity.isPointInPolygon(latLng,listTerrain.get(0).getPoints())) {
                        addZone(latLng);
                    }
                }
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                if(listTerrain.size() == 0) {
                    if (marker.getTitle().equals("Supprimer")) {
                        listMarker.remove(marker.getPosition());
                        listMarkerV.remove(marker);
                        marker.remove();
                        if (polygon != null && listMarker.size() > 1) {
                            polygon.remove();
                            polygon = mMap.addPolygon(new PolygonOptions()
                                    .addAll(listMarker)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(Color.argb(100, 0, 0, 255)));
                            polygon.setClickable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                        }

                        if (listMarker.size() == 2 && polygon != null) {
                            polygon.remove();
                            listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }

                        if (listMarker.size() == 1) {
                            listMarkerV.get(0).setDraggable(true);
                            listMarkerV.get(0).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        }
                    }
                }

                else {
                    if (marker.getTitle().equals("Supprimer")) {
                        listMarker.remove(marker.getPosition());
                        listMarkerV.remove(marker);
                        marker.remove();
                        if (polygon != null && listMarker.size() > 1) {
                            polygon.remove();
                            polygon = mMap.addPolygon(new PolygonOptions()
                                    .addAll(listMarker)
                                    .strokeColor(Color.MAGENTA)
                                    .fillColor(Color.argb(100, 100, 100, 100)));
                            polygon.setClickable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                        }
                        if (listMarker.size() == 2 && polygon != null) {
                            polygon.remove();
                            listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                            listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        }
                        if (listMarker.size() == 1) {
                            listMarkerV.get(0).setDraggable(true);
                            listMarkerV.get(0).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        }
                    }
                }
            }
        });

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {

            @Override
            public void onPolygonClick(Polygon polygon) {
                me = new LatLng(maPosition.getPosition().latitude, maPosition.getPosition().longitude);
                if(polygon.getStrokeColor() == Color.BLUE && polygon.getFillColor() == Color.argb(100, 0, 0, 255)) {
                    polygon.remove();
                    polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(polygon.getPoints())
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(100, 255, 0, 0)));
                    listTerrain.add(polygon);
                    for (int i = 0; i <= listMarkerV.size() - 1; i++) {
                        listMarkerV.get(i).remove();
                    }
                    listMarkerV.clear();
                    listMarker.clear();
                }

                else if(polygon.getStrokeColor() == Color.MAGENTA && polygon.getFillColor() == Color.argb(100, 100, 100, 100)) {
                    polygon.remove();
                    polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(polygon.getPoints())
                            .strokeColor(Color.MAGENTA)
                            .fillColor(Color.argb(100, 0, 0, 0))
                            .clickable(true));
                    listZone.add(polygon);
                    for (int i = 0; i <= listMarkerV.size() - 1; i++) {
                        listMarkerV.get(i).remove();
                    }
                    listMarkerV.clear();
                    listMarker.clear();
                }
                else if(polygon.getStrokeColor() == Color.MAGENTA && polygon.getFillColor() == Color.argb(100, 0, 0, 0) && vmMapsActivity.isPointInPolygon(me,polygon.getPoints())) {
                    polygon.remove();
                    polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(polygon.getPoints())
                            .strokeColor(Color.GREEN)
                            .fillColor(Color.argb(100, 0, 255, 0))
                            .clickable(true));
                    for (int i = 0; i <= listMarkerV.size() - 1; i++) {
                        listMarkerV.get(i).remove();
                    }
                    zoneTest = new Zone(polygon.getPoints(), 1, equipeTest);
                    listMarkerV.clear();
                    listMarker.clear();
                }

                else if(polygon.getStrokeColor() == Color.MAGENTA && polygon.getFillColor() == Color.argb(100, 0, 0, 0) && !vmMapsActivity.isPointInPolygon(me,polygon.getPoints())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                    alertDialog.setTitle("Informations sur la Zone");
                    alertDialog.setMessage("La zone n'appartient à aucune équipe");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                else if(polygon.getStrokeColor() == Color.GREEN && polygon.getFillColor() == Color.argb(100, 0, 255, 0)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                    alertDialog.setTitle("Informations sur la Zone");
                    alertDialog.setMessage("La zone appartient à l'équipe : "+zoneTest.getPosseder());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            int temp = 0;

            @Override
            public void onMarkerDragStart(Marker marker) {
                temp = (listMarkerV.size())-1;
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                listMarker.set(temp, marker.getPosition());
                if(listTerrain.size() == 0) {
                    if (polygon != null && listMarker.size() > 2) {
                        polygon.remove();
                        polygon = mMap.addPolygon(new PolygonOptions()
                                .addAll(listMarker)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.argb(100, 0, 0, 255)));
                        polygon.setClickable(true);
                    }
                }

                else {
                    if (polygon != null && listMarker.size() > 2) {
                        polygon.remove();
                        polygon = mMap.addPolygon(new PolygonOptions()
                                .addAll(listMarker)
                                .strokeColor(Color.MAGENTA)
                                .fillColor(Color.argb(100, 100, 100, 100)));
                        polygon.setClickable(true);
                    }
                }
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if(listTerrain.size() == 0) {
                    listMarker.remove(temp);
                    listMarker.add(temp, marker.getPosition());
                }

                else if(polygon != null && vmMapsActivity.isPointInPolygon(marker.getPosition(), listTerrain.get(0).getPoints())) {
                    listMarker.remove(temp);
                    listMarker.add(temp, marker.getPosition());
                }
                else if(polygon != null && !vmMapsActivity.isPointInPolygon(marker.getPosition(),listTerrain.get(0).getPoints())){
                    listMarker.remove(temp);
                    listMarkerV.remove(temp);
                    marker.remove();

                    if (listMarker.size() > 1) {
                        polygon.remove();
                        polygon = mMap.addPolygon(new PolygonOptions()
                                .addAll(listMarker)
                                .strokeColor(Color.MAGENTA)
                                .fillColor(Color.argb(100, 100, 100, 100)));
                        polygon.setClickable(true);
                        listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                        listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                    }

                    if (listMarker.size() == 2) {
                        polygon.remove();
                        listMarkerV.get(listMarkerV.size() - 1).setDraggable(true);
                        listMarkerV.get(listMarkerV.size() - 1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    }

                    if (listMarker.size() == 1) {
                        listMarkerV.get(0).setDraggable(true);
                        listMarkerV.get(0).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    }
                }
            }
        });

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                if(maPosition != null && detecter) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(maPosition.getPosition().latitude, maPosition.getPosition().longitude)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
                if(maPosition != null) {
                    maPosition.remove();
                }
                maPosition = mMap.addMarker(new MarkerOptions().position(myLatLng).title("Ma position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick()
            {
                if(detecter) {
                    detecter = false;
                }
                else {
                    detecter = true;
                }
                return false;
            }
        });
    }

    private void addZone(LatLng latLng){
        m = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Supprimer").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        listMarkerV.add(m);

        listMarker.add(m.getPosition());

        if (listMarker.size() >= 3) {
            if (polygon != null) {
                polygon.remove();
                polygon = mMap.addPolygon(new PolygonOptions()
                        .addAll(listMarker)
                        .strokeColor(Color.MAGENTA)
                        .fillColor(Color.argb(100,100, 100, 100)));
                polygon.setClickable(true);
            } else {
                polygon = mMap.addPolygon(new PolygonOptions()
                        .addAll(listMarker)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(100, 0, 0, 255)));
                polygon.setClickable(true);
            }
        }

        if (listMarker.size() > 1) {
            for (int i = 0; i < listMarkerV.size() - 1; i++) {
                listMarkerV.get(i).setDraggable(false);
                listMarkerV.get(i).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
        }
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
            }
            else {
                //Aucune explication demandee, on affiche la demancde de permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else {
            return true;
        }
    }
}
