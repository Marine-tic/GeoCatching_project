package iut.unice.fr.geocatching.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lomenne on 06/01/2017.
 */

public class Zone {

    private ArrayList<LatLng> coordonnees;
    private int niveau;
    private Equipe posseder;


    // Constructor
    public Zone(ArrayList<LatLng> coordonnees, int niveau, Equipe posseder) {
        this.coordonnees = coordonnees;
        this.niveau = niveau;
        this.posseder = posseder;
    }
    // Default constructor
    public Zone() {
        this.coordonnees = new ArrayList<LatLng>();
        this.niveau = 1;
        this.posseder = new Equipe();
    }

    // Getters
    public List<LatLng> getCoordonnees() {
        return coordonnees;
    }

    public int getNiveau() {
        return niveau;
    }

    public Equipe getPosseder() {
        return posseder;
    }


    // Setters
    public void setCoordonnees(ArrayList<LatLng> coordonnees) {
        this.coordonnees = coordonnees;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public void setPosseder(Equipe posseder) {
        this.posseder = posseder;
    }

    // Methods
    public void resetZone() {
        niveau = 0;
        posseder = null;
    }
}

