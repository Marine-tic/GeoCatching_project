package iut.unice.fr.geocatching.Views;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import iut.unice.fr.geocatching.Models.Partie;
import iut.unice.fr.geocatching.Models.Zone;
import iut.unice.fr.geocatching.R;
import iut.unice.fr.geocatching.ViewsModels.CtrlDeconnexionQuitter;
import iut.unice.fr.geocatching.ViewsModels.VMJoinMapsActivity;
import iut.unice.fr.geocatching.ViewsModels.VMMapsActivity;

public class JoinMapsActivity extends FragmentActivity implements OnMapReadyCallback, NoticeDialogFragment.NoticeDialogListener {

    private GoogleMap mMap;
    private ArrayList<LatLng> listMarker = new ArrayList<>();
    private ArrayList<LatLng> listMarkerTerrain = new ArrayList<>();
    private ArrayList<ArrayList<LatLng>> listMarkerZone = new ArrayList<>();
    private ArrayList<Marker> listMarkerV = new ArrayList<>();
    private ArrayList<Polygon> listTerrain = new ArrayList<>();
    private ArrayList<Polygon> listZone = new ArrayList<>();
    private Marker m = null;
    private Marker maPosition = null;
    private Polygon polygon = null;
    private Polygon polygonClick = null;
    private Boolean detecter = true;
    private LatLng me;
    private VMMapsActivity vmMapsActivity;
    private String username;
    private Boolean hasWon = false;
    private final int REQUEST_WIN = 1;

    // DRAWER
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mActionPartie;

    private VMJoinMapsActivity vmJoinMapsActivity;
    private Partie partiEnCours;
    private String nomPartie;
    private String nameEquipe;

    //Test Création
    private Equipe equipeTest = new Equipe("Equipe 1");
    private Zone zoneTest;
    private ArrayList<String> niveauZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nomPartie = intent.getStringExtra("namePartie");
        nameEquipe = intent.getStringExtra("nameEquipe");
        vmMapsActivity = new VMMapsActivity();
        vmJoinMapsActivity = new VMJoinMapsActivity(nomPartie);

        partiEnCours = vmJoinMapsActivity.getPartie();
        setContentView(R.layout.activity_join_maps);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        username = intent.getStringExtra("name");

        mTitle = mDrawerTitle = getTitle();
        mActionPartie = getResources().getStringArray(R.array.action_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mActionPartie));
        mDrawerList.setOnItemClickListener(new JoinMapsActivity.DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        if(position == 0){
            if(CtrlDeconnexionQuitter.quitterPartie(username, nomPartie, nameEquipe)) {
                startActivity(new Intent(JoinMapsActivity.this, MenuActivity.class));
                finish();
            }

        }else if(position == 1){
            if(CtrlDeconnexionQuitter.deconnexion(username)){
                startActivity(new Intent(JoinMapsActivity.this, MainActivity.class));
                finish();
            }
        }else {

        }
        mDrawerList.setItemChecked(position, true);
        setTitle(mActionPartie[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            me = new LatLng(latitude, longitude);
            vmMapsActivity.addJoueur(username, me);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            Toast.makeText(this, "Please check you have authorized the location permission", Toast.LENGTH_LONG).show();
        }

        /**
         *  Récupération du booléen qui défini si la capture à été réussi ou non
         */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hasWon = extras.getBoolean("hasWon");
        }

        /**
         * =================== Localisation de tous les joueurs ==========================
         */
        for (Joueur joueur : vmMapsActivity.getPlayerPositionList()) {
            if (!(joueur.getUsername().equals(username))) {
                mMap.addMarker(new MarkerOptions()
                        .position(joueur.getPosition())
                        .title(joueur.getUsername())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
            }
        }

        listMarkerTerrain = vmJoinMapsActivity.getTerrain();
        listMarkerZone = vmJoinMapsActivity.getZones();
        niveauZone = vmJoinMapsActivity.getNiveauZone();
        
        for (int i = 0; i < vmJoinMapsActivity.getNombreTerrain(); i++) {
            polygon = mMap.addPolygon(new PolygonOptions()
                    .addAll(listMarkerTerrain)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(100, 255, 0, 0)));
            polygon.setClickable(false);
        }

        for (int i = 0; i < vmJoinMapsActivity.getNombreZones(); i++) {
            if(niveauZone.get(i).equals("0")) {
                polygon = mMap.addPolygon(new PolygonOptions()
                        .addAll(listMarkerZone.get(i))
                        .strokeColor(Color.MAGENTA)
                        .fillColor(Color.argb(100, 100, 100, 100)));
            }
            else{
                polygonClick = mMap.addPolygon(new PolygonOptions()
                        .addAll(listMarkerZone.get(i))
                        .strokeColor(Color.GREEN)
                        .fillColor(Color.argb(100, 0, 255, 0)));
            }
            polygon.setClickable(true);
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {

            @Override
            public void onPolygonClick(Polygon polygon) {
                if (maPosition != null)
                {
                    me = new LatLng(maPosition.getPosition().latitude, maPosition.getPosition().longitude);
                    // Check if the zone is captured
                    if (polygon.getStrokeColor() == Color.MAGENTA && polygon.getFillColor() == Color.argb(100, 100, 100, 100) && !vmMapsActivity.isPointInPolygon(me, polygon.getPoints()))
                    {
                        // TODO Récupérer le nom du possesseur
                        showNoticeDialog("La zone n'appartient à aucune équipe");

                        // Launch the game to capture the zone
                    }

                    else if (polygon.getStrokeColor() == Color.MAGENTA && polygon.getFillColor() == Color.argb(100, 100, 100, 100) && vmMapsActivity.isPointInPolygon(me, polygon.getPoints()))
                    {
                        polygonClick = polygon;
                        Intent intent = new Intent(JoinMapsActivity.this, GameActivity.class);
                        // startActivityForResult permet d'avoir un callback de l'activité fille (jeu)
                        Toast.makeText(getBaseContext(), "Lancement du jeu pour capturer la zone", Toast.LENGTH_LONG).show();
                        startActivityForResult(intent, REQUEST_WIN);

                    }
                    /*else if (polygon.getStrokeColor() == Color.GREEN && polygon.getFillColor() == Color.argb(100, 0, 255, 0)) {
                    }*/
                }

                else
                {
                    Toast.makeText(getBaseContext(), "Your position is unavailable", Toast.LENGTH_LONG).show();

                }
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            int temp = 0;

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                if (maPosition != null && detecter) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(maPosition.getPosition().latitude, maPosition.getPosition().longitude)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
                if (maPosition != null) {
                    maPosition.remove();
                }
                maPosition = mMap.addMarker(new MarkerOptions().position(myLatLng).title("Ma position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {
                if (detecter) {
                    detecter = false;
                } else {
                    detecter = true;
                }
                return false;
            }
        });
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //Verification des permissions pour la localisation
    public boolean checkLocationPermission() {
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

    public void showNoticeDialog(String monTexte) {
        // Create an instance of the dialog fragment and show it
        NoticeDialogFragment newFragment = NoticeDialogFragment.newInstance(monTexte);

        newFragment.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    /**
     * Vérification et modification en fonction du résultat au retour du jeu
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_WIN && resultCode == Activity.RESULT_OK) {
            Boolean hasWon = data.getBooleanExtra("hasWon", false);
            Boolean hasAlreadyPlayed = data.getBooleanExtra("hasAlreadyPlayed", false);
            // do something with B's return values
            if (hasAlreadyPlayed.equals(true)) {
                if (hasWon.equals(true)) {
                    if (polygonClick != null) {
                        polygonClick.remove();
                        polygonClick = mMap.addPolygon(new PolygonOptions()
                                .addAll(polygonClick.getPoints())
                                .strokeColor(Color.GREEN)
                                .fillColor(Color.argb(100, 0, 255, 0))
                                .clickable(true));
                        listZone.add(polygonClick);
                        vmJoinMapsActivity.captureZone(polygonClick,nameEquipe, nomPartie);
                        Toast.makeText(getBaseContext(), R.string.zone_captured, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), R.string.zone_capture_failed, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    public void onBackPressed() {

    }
}
