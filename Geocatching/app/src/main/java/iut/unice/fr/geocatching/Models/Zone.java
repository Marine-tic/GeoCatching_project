package iut.unice.fr.geocatching.Models;

import java.util.List;

import iut.unice.fr.geocatching.Helpers.Point;

/**
 * Created by Lomenne on 06/01/2017.
 */

public class Zone {

    private List<Point> coordonnees;
    private int niveau;
    private Equipe posseder;


    // Constructor
    public Zone(List<Point> coordonnees, int niveau, Equipe posseder) {
        this.coordonnees = coordonnees;
        this.niveau = niveau;
        this.posseder = posseder;
    }


    // Getters
    public List<Point> getCoordonnees() {
        return coordonnees;
    }

    public int getNiveau() {
        return niveau;
    }

    public Equipe getPosseder() {
        return posseder;
    }


    // Setters
    public void setCoordonnees(List<Point> coordonnees) {
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

        for (Point point : coordonnees) {
            try {
                point.setLatitude(0.0d);
                point.setLongitude(0.0d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

