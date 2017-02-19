package iut.unice.fr.geocatching.ViewsModels;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iut.unice.fr.geocatching.Models.Equipe;
import iut.unice.fr.geocatching.Models.Partie;

/**
 * Created by jérémy on 16/02/2017.
 */

public class VMListePartieActivity {
    private ArrayList<Partie> partieliste = new ArrayList<>();
    private ArrayList<String> equipeliste = new ArrayList<>();

    public VMListePartieActivity() {
        generateListePartie();
    }

    public ArrayList<Partie> getPartieListe() {
        return partieliste;
    }

    public ArrayList<String> getEquipeListe(String namePartie) {
        this.generateListeEquipe(namePartie);
        return equipeliste;
    }

    private void generateListeEquipe(String namePartie) {
        String partieJSON = Partie.listPartie();

        JSONArray jsonObject = null;

        if(partieJSON != null) {
            try {
                jsonObject = new JSONArray(partieJSON);
                // Pour tous les objets on récupère les infos
                for (int i = 0; i < jsonObject.length(); i++) {
                    // On récupère un objet JSON du tableau
                    JSONObject obj = new JSONObject(jsonObject.getString(i));
                    if(obj.getString("nom").equals(namePartie)) {
                        JSONArray jsonArrayEquipeInitial = new JSONArray(obj.getString("equipe"));
                        for(int j = 0 ; j < jsonArrayEquipeInitial.length() ; j++){
                            JSONObject jsonObjectEquipeFinal = jsonArrayEquipeInitial.getJSONObject(j);
                            String tempI = jsonObjectEquipeFinal.getString("name");
                            equipeliste.add(tempI);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateListePartie() {

        String partieJSON = Partie.listPartie();

        JSONArray jsonObject = null;

        if(partieJSON != null) {
            try {
                jsonObject = new JSONArray(partieJSON);
                // Pour tous les objets on récupère les infos
                for (int i = 0; i < jsonObject.length(); i++) {
                    // On récupère un objet JSON du tableau
                    JSONObject obj = new JSONObject(jsonObject.getString(i));
                    // On fait le lien Joueurs - Objet JSON
                    Partie mapartie = new Partie(obj.getString("nom"), new Date());
                    partieliste.add(mapartie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Date stringToDate(String Sdate) {

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        String dateInString = Sdate;

        try {
            Date date = formatter.parse(dateInString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void rejoindreEquipe(String name, String namePartie, String nameEquipe) {
        Equipe.joinEquipe(name, namePartie, nameEquipe);
    }
}
