package iut.unice.fr.geocatching.Helpers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Auna on 06/01/2017.
 */

public class Points {

    private ArrayList<LatLng> pointListe;

    // Constructor
    public Points(ArrayList<LatLng> pointListe) {
        this.setPointListe(pointListe);
    }

    public ArrayList<LatLng> getPointListe() {
        return pointListe;
    }

    public void setPointListe(ArrayList<LatLng> pointListe) {
        this.pointListe = pointListe;
    }
}
