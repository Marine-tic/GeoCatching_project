package iut.unice.fr.geocatching.Models;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import iut.unice.fr.geocatching.Helpers.Request;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur extends AsyncTask<String, Void, String> {
    private String username;
    private LatLng position;
    private Boolean isConnected;
    private String PlayerJSON;

    // Constructor
    public Joueur(String username, LatLng position, Boolean isConnected) {
        this.username = username;
        this.position = position;
        this.isConnected = isConnected;
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

    private String connection() {
        HashMap<String,String> data = new HashMap<>();
        data.put("name", username.toString());
        data.put("latitude", position.latitude+"");
        data.put("longitude", position.longitude+"");
        return Request.exectute("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/Add/","POST",data);
    }

    private String listPlayer() {
        return Request.exectute("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/List/","GET",null);
    }

    @Override
    protected String doInBackground(String... params) {
        String s = null;
        switch (params[0]) {
            case "connexion": s = connection();
                break;
            case "listPlayer": s = listPlayer(); PlayerJSON = s;
                break;
            default:
                break;
        }
        return s;
    }

    // Methods
    @Override
    public String toString() {
        return "Username : " + username ;
    }

    public String PlayerJSON() {
        return PlayerJSON;
    }
}
