package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;

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
        Joueur joueur1 = new Joueur("Johnny", new LatLng(43.616345d, 7.072789d), true);
        Joueur joueur2 = new Joueur("Paul", new LatLng(43.620796d, 7.070508d), true);
        Joueur joueur3 = new Joueur("Germaine", new LatLng(43.620007d, 7.065029d), true);
        Joueur joueur4 = new Joueur("Michou", new LatLng(43.616830d, 7.076904d), true);

        // Création d'une liste de joueurs pour récupérer les position
        playerPositionList.add(joueur1);
        playerPositionList.add(joueur2);
        playerPositionList.add(joueur3);
        playerPositionList.add(joueur4);
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
        j = new Joueur(name, position, true);
        j.execute("connexion");
    }

    public String listPlayer(){
        LatLng l= new LatLng(52,5);
        j = new Joueur("test", l, true);
        j.execute("listPlayer");
        String reponse = j.PlayerJSON();
        return null;
    }


    public void deconnection(String test) {
    }
}
