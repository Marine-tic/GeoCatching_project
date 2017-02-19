package Class;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jérémy on 21/01/2017.
 */
public class Partie {
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private Terrain terrain;
    private ArrayList<Equipe> equipe;

    public Partie(String nom, Date dateFin, ArrayList<Equipe> equipe) {
        this.nom = nom;
        this.dateDebut = new Date();
        this.dateFin = dateFin;
        this.setEquipe(equipe);
    }

    public void rejouer(Date dateFin) {
        this.dateDebut = new Date();
        this.dateFin = dateFin;
        this.terrain.resetAllZone();
    }

    public void ajouterTerrain(Terrain t) {
        terrain = t;
        this.terrain.resetAllZone();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public ArrayList<Equipe> getEquipe() {
        return equipe;
    }

    public void setEquipe(ArrayList<Equipe> equipe) {
        this.equipe = equipe;
    }
}
