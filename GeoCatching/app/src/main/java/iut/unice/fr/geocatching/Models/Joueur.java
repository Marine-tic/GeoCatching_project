package iut.unice.fr.geocatching.Models;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Views.FreeMapsActivity;
import iut.unice.fr.geocatching.Views.MenuActivity;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String s = null;
        switch (params[0]) {
            case "connexion": s = connection();
                break;
            default:
                break;
        }
        return s;
    }

    private String username;
    private LatLng position;
    private Boolean isConnected;
    private String information;

    // Constructor
    public Joueur(String username, LatLng position, Boolean isConnected) {
        this.username = username;
        this.position = position;
        this.isConnected = isConnected;
        this.information = "{\"name\" : "+ username +",\"longitude\" : "+ position.longitude +", \"latitude\" : "+ position.latitude +"}";
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
        String response = null;
        URL url = null;
        try {
            url = new URL("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/ListPlayers/Add/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedWriter writer = null;
        try {
            if (url != null) {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                ArrayList<String> Data = new ArrayList<>();
                Data.add(information);
                /*Data.put("longitude", "46,6");
                Data.put("latitude", "45,85");*/
                os = conn.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(Data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // Proper way to ensure that the flush and the close are done in case of problem
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
                if (os != null) {
                    os.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int responseCode = 0;
        try {
            if (conn != null) {
                responseCode = conn.getResponseCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (br != null && (line = br.readLine()) != null) {
                    response += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response = "Fail";
        }

        return responseCode+"";
    }

    @Override
    protected void onPostExecute(String message) {
        System.out.println(message);
    }

    // Methods
    @Override
    public String toString() {
        return "Username : " + username ;
    }

    private String getPostDataString(ArrayList<String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String s : params) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(s, "UTF-8"));
        }

        return result.toString();
    }
}
