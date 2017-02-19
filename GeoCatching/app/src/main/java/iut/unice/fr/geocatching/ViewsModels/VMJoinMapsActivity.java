package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Models.Partie;

/**
 * Created by jérémy on 18/02/2017.
 */

public class VMJoinMapsActivity extends VMListePartieActivity {
    Partie partieChoisi;
    ArrayList<LatLng> listTerrain = new ArrayList<>();
    ArrayList<ArrayList<LatLng>> listZones = new ArrayList<>();

    public ArrayList<String> getNiveauZone() {
        return niveauZone;
    }

    ArrayList<String> niveauZone = new ArrayList<>();
    int nombreZones = 0;
    int nombreTerrain = 0;
    public VMJoinMapsActivity(String nom) {
        for (Partie maPartie: this.getPartieListe()){
            if(maPartie.getNom().equals(nom)){
                partieChoisi = maPartie;
            }
        }
    }
    public Partie getPartie(){
        return partieChoisi;
    }

    public ArrayList<LatLng> getTerrain() {
        this.getCoordonnees();
        return listTerrain;
    }

    public int getNombreZones() {
        return nombreZones;
    }

    public int getNombreTerrain() {
        return nombreTerrain;
    }

    public ArrayList<ArrayList<LatLng>> getZones() {
        return listZones;
    }

    private void getCoordonnees() {

        String coordonneesJSON = Partie.listTerrain(partieChoisi.getNom());

        JSONObject jsonObject = null;

        if(coordonneesJSON != null) {
            try {
                jsonObject = new JSONObject(coordonneesJSON);
                // Pour tous les objets on récupère les infos
                JSONArray jsonArrayTerrain = new JSONArray(jsonObject.getString("coordonnees"));
                nombreTerrain = 1;
                for(int j = 0 ; j < jsonArrayTerrain.length() ; j++){
                    String[] temp = (jsonArrayTerrain.get(j).toString()).split(";");
                    listTerrain.add(new LatLng(Double.parseDouble(temp[0]), Double.parseDouble(temp[1])));
                }

                JSONArray jsonArrayZoneInitial = new JSONArray(jsonObject.getString("listZone"));
                nombreZones = jsonArrayZoneInitial.length();
                for(int j = 0 ; j < jsonArrayZoneInitial.length() ; j++){
                    ArrayList<LatLng> tempList = new ArrayList<>();
                    JSONObject jsonObjectZoneFinal = jsonArrayZoneInitial.getJSONObject(j);
                    String tempI = jsonObjectZoneFinal.getString("coordonnees");
                    String tempN = jsonObjectZoneFinal.getString("niveau");
                    JSONArray jsonArrayZoneFinal = new JSONArray(tempI);
                    niveauZone.add(tempN);
                    for(int i = 0; i < jsonArrayZoneFinal.length() ; i++) {
                        String[] tempF = (jsonArrayZoneFinal.get(i).toString()).split(";");
                        tempList.add(new LatLng(Double.parseDouble(tempF[0]), Double.parseDouble(tempF[1])));
                    }
                    listZones.add(tempList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void captureZone(Polygon polygonClick, String nameEquipe,String namePartie) {
        String coordonnee = "";
        for(LatLng l : polygonClick.getPoints()){
            coordonnee += l.latitude+";"+l.longitude+"-";
        }

        Partie.capureZone(coordonnee, nameEquipe, namePartie);
    }
}
