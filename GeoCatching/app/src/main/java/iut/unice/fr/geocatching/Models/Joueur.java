package iut.unice.fr.geocatching.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import iut.unice.fr.geocatching.Helpers.Request;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur{
    private String username;
    private LatLng position;
    private Boolean isConnected;

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

    public String connection() {
        HashMap<String,String> data = new HashMap<>();
        data.put("name", username);
        data.put("latitude", position.latitude+"");
        data.put("longitude", position.longitude+"");
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/Add/","POST",data);

        try {
            return r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String listPlayer() {
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/List/","GET",null);
        try {
            return r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        }
    }

    // Methods
    @Override
    public String toString() {
        return "Username : " + username ;
    }

    public String update(String username) {
        HashMap<String,String> data = new HashMap<>();
        data.put("latitude", position.latitude+"");
        data.put("longitude", position.longitude+"");
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/UpdatePosistion/"+username,"POST",data);
        try {
            return r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Boolean deconnexion(String nom) {
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/Deconnection/"+nom,"GET",null);
        try {
            r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
