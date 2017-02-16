package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import iut.unice.fr.geocatching.Models.Joueur;

/**
 * Created by Lomenne on 22/01/2017.
 */

public class VMMapsActivity {
    private ArrayList<Joueur> playerPositionList = new ArrayList<>();
    private Joueur j;

    public VMMapsActivity() {
        j = new Joueur("", new LatLng(0,0), true);
        getJoueurs();
    }

    public static String InputStreamToString (InputStream in, int bufSize) {
        final StringBuilder out = new StringBuilder();
        final byte[] buffer = new byte[bufSize];
        try {
            for (int ctr; (ctr = in.read(buffer)) != -1;) {
                out.append(new String(buffer, 0, ctr));
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot convert stream to string", e);
        }
        // On retourne la chaine contenant les donnees de l'InputStream
        return out.toString();
    }

    /**
     * @param in : buffer with the php result
     * @return : the string corresponding to the buffer
     */
    public static String InputStreamToString (InputStream in) {
        // On appelle la methode precedente avec une taille de buffer par defaut
        return InputStreamToString(in, 1024);
    }

    public ArrayList<Joueur> getPlayerPositionList() {
        return playerPositionList;
    }

    public boolean isPointInPolygon(LatLng tap, List<LatLng> vertices) {
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

    public void addJoueur(String name, LatLng position) {
        j.setUsername(name);
        j.setPosition(position);
        j.connection();
    }

    private void getJoueurs() {

        String joueurJSON =  j.listPlayer();

        JSONArray jsonObject = null;

        if(joueurJSON != null) {
            try {
                jsonObject = new JSONArray(joueurJSON);
                // Pour tous les objets on récupère les infos
                for (int i = 0; i < jsonObject.length(); i++) {
                    // On récupère un objet JSON du tableau
                    JSONObject obj = new JSONObject(jsonObject.getString(i));
                    // On fait le lien Joueurs - Objet JSON
                    LatLng latlng = new LatLng(Double.parseDouble(obj.getString("_latitude")), Double.parseDouble(obj.getString("_longitude")));
                    Joueur joueur = new Joueur(obj.getString("_username"), latlng, true);
                    // On ajoute le joueur à la liste
                    playerPositionList.add(joueur);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String username, LatLng position) {
        j.setPosition(position);
        j.update(username);
    }
}
