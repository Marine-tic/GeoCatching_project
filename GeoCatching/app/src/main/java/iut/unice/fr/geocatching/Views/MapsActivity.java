package iut.unice.fr.geocatching.Views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import java.util.List;

import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.R;

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
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        me = new LatLng(latitude,longitude);

        Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new LatLng(43.616345d, 7.072789d), true);
        Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new LatLng(43.620796d, 7.070508d), true);
        Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new LatLng(43.620007d, 7.065029d), true);
        Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new LatLng(43.616830d, 7.076904d), true);

        // Création d'une liste de joueurs pour récupérer les position
        ArrayList<Joueur> playerPositionList = new ArrayList<>();
        playerPositionList.add(joueur1);
        playerPositionList.add(joueur2);
        playerPositionList.add(joueur3);
        playerPositionList.add(joueur4);

        // Position en dur
        /**
         * =================== Localisation de tous les joueurs ==========================
         */
        for (Joueur joueur : playerPositionList) {
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
                    if (listTerrain.get(0).getStrokeColor() == Color.RED && listTerrain.get(0).getFillColor() == Color.argb(100, 255, 0, 0) && isPointInPolygon(latLng,listTerrain.get(0).getPoints())) {
                        if(listZone.size() == 0){
                            addZone(latLng);
                        }
                        else{
                            for(int i=0; i<listZone.size();i++){
                                if(!isPointInPolygon(latLng,listZone.get(i).getPoints())){
                                    addZone(latLng);
                                }
                            }
                        }
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
                if(polygon.getStrokeColor() == Color.BLUE && polygon.getFillColor() == Color.argb(100, 0, 0, 255)) {
                    polygon.remove();
                    polygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(polygon.getPoints())
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(100, 255, 0, 0))
                            .clickable(true));
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
                else{
                    if(isPointInPolygon(me,polygon.getPoints())){
                        System.out.print("1");
                    }
                    else{
                        System.out.print("2");
                    }
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
                listMarker.remove(temp);
                listMarker.add(temp, marker.getPosition());
            }
        });

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                // TODO Auto-generated method stub
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

    private boolean isPointInPolygon(LatLng tap, List<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
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
}
