package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Models.Joueur;

/**
 * Created by Lomenne on 22/01/2017.
 */

public class VMMapsActivity {
    private ArrayList<Joueur> playerPositionList = new ArrayList<>();

    public VMMapsActivity() {
        Joueur joueur1 = new Joueur("Johnny", "johnny@gmail.com", new LatLng(43.616345d, 7.072789d), true);
        Joueur joueur2 = new Joueur("Paul", "Paul@gmail.com", new LatLng(43.620796d, 7.070508d), true);
        Joueur joueur3 = new Joueur("Germaine", "Germaine@gmail.com", new LatLng(43.620007d, 7.065029d), true);
        Joueur joueur4 = new Joueur("Michou", "Michou@gmail.com", new LatLng(43.616830d, 7.076904d), true);

        // Création d'une liste de joueurs pour récupérer les position
        playerPositionList.add(joueur1);
        playerPositionList.add(joueur2);
        playerPositionList.add(joueur3);
        playerPositionList.add(joueur4);
    }

    public ArrayList<Joueur> getPlayerPositionList() {
        return playerPositionList;
    }
}
