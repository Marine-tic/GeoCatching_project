package iut.unice.fr.geocatching.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

import iut.unice.fr.geocatching.Helpers.Point;

/**
 * Created by Lomenne on 06/01/2017.
 */

public class Zone {

    private List<LatLng> coordonnees;
    private int niveau;
    private Equipe posseder;


    // Constructor
    public Zone(List<LatLng> coordonnees, int niveau, Equipe posseder) {
        this.coordonnees = coordonnees;
        this.niveau = niveau;
        this.posseder = posseder;
    }
    // Default constructor
    public Zone() {
        this.coordonnees = new LinkedList<>();
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
    public void setCoordonnees(List<LatLng> coordonnees) {
        this.coordonnees = coordonnees;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public void setPosseder(Equipe posseder) {
        this.posseder = posseder;
    }


    // Methods
    /*public void resetZone() {

        for (Point point : coordonnees) {
            try {
                point.setLatitude(0.0d);
                point.setLongitude(0.0d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
    public void ajouterPoint(LatLng p){
        //TO DOO
        this.coordonnees.add(p);
    }
    public Boolean appartient(Point a) {
        //TO DOO
        return true;
    }
}

