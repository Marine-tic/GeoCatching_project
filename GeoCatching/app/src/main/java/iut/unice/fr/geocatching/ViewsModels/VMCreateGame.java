package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Models.Partie;

/**
 * Created by Jérémy on 17/02/2017.
 */

public class VMCreateGame {
    public void create(String namePartie, String dateFinale, ArrayList<Polygon> listTerrain, ArrayList<Polygon> listZone, String nameEquipes) {
        Partie.create(namePartie,dateFinale,nameEquipes);
        Partie.addTerrain(namePartie,polygonToString(listTerrain.get(0)),"0");
        for(Polygon p : listZone){
            Partie.addTerrain(namePartie,polygonToString(p),"1");
        }
    }

    private String polygonToString(Polygon polygons) {
        String s = "";
        for(LatLng m : polygons.getPoints()){
            String temp = m.latitude+";"+m.longitude+"-";
            s = s+temp;
        }

        return s;
    }

    public static String getNameEquipe(int nbrEquipe) {
        String nomEquipes = null;
        switch (nbrEquipe) {
            case 2 :
                nomEquipes = "Rouge-Bleu";
                break;
            case 3 :
                nomEquipes = "Rouge-Bleu-Vert";
                break;
            case 4 :
                nomEquipes = "Rouge-Bleu-Vert-Jaune";
                break;
            default :
                nomEquipes = "Rouge-Bleu-Vert-Jaune-Violet";
                break;
        }
        return nomEquipes;
    }
}
