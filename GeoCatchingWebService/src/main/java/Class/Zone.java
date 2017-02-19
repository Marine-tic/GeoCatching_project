package Class;

import java.util.ArrayList;

/**
 * Created by Jérémy on 21/01/2017.
 */
public class Zone {
    private ArrayList<String> coordonnees;
    private int niveau;
    private Equipe posseder;


    // Constructor
    public Zone(ArrayList<String> coordonnees, int niveau, Equipe posseder) {
        this.coordonnees = coordonnees;
        this.niveau = niveau;
        this.posseder = posseder;
    }
    // Default constructor
    public Zone() {
        this.coordonnees = new ArrayList<String>();
        this.niveau = 1;
    }


    // Getters
    public ArrayList<String> getCoordonnees() {
        return coordonnees;
    }

    public int getNiveau() {
        return niveau;
    }

    public Equipe getPosseder() {
        return posseder;
    }


    // Setters
    public void setCoordonnees(ArrayList<String> coordonnees) {
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
        this.coordonnees = new ArrayList<String>();
    }
    public void ajouterPoint(String p){
        //TO DOO
        this.coordonnees.add(p);
    }
}
