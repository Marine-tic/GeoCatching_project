package iut.unice.fr.geocatching.Models;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import iut.unice.fr.geocatching.Helpers.Point;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        switch (params[0]) {
            case "connexion": connection();
                break;
            default:
                break;
        }
        return null;
    }

    private String username;
    private LatLng position;
    private Boolean isConnected;
    //private String information;

    // Constructor
    public Joueur(String username, LatLng position, Boolean isConnected) {
        this.username = username;
        this.position = position;
        this.isConnected = isConnected;
        //this.information = "{\"name\" : "+ username +",\"longitude\" : "+ position.longitude +", \"latitude\" : "+ position.latitude +"}";
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public LatLng getPosition() {
        return position;
    }
    public Boolean getIsConnected() {
        return isConnected;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void connection() {
        URL url = null;
        try {
            url = new URL("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/Add");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            if (url != null) {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                HashMap<String, String> Data = new HashMap<>();
                Data.put("username", "test");
                Data.put("longitude", "46,6");
                Data.put("latitude", "45,85");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods
    @Override
    public String toString() {
        return "Username : " + username ;
    }
}
