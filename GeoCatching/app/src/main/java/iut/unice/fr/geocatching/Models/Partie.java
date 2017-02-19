package iut.unice.fr.geocatching.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import iut.unice.fr.geocatching.Helpers.Request;

/**
 * Created by Loic Mennella on 06/01/2017.
 */
public class Partie {

    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private Terrain terrain;

    public Partie(String nom, Date dateFin) {
        this.nom = nom;
        this.dateDebut = new Date();
        this.dateFin = dateFin;
        ajouterTerrain();
    }

    public void rejouer(Date dateFin) {
        this.dateDebut = new Date();
        this.dateFin = dateFin;
        this.terrain.resetAllZone();
    }

    public void ajouterTerrain() {
        terrain = new Terrain();
        this.terrain.resetAllZone();
    }

    public boolean action(boolean choix, double alea) {
        if (alea <= 0.5 && choix){
            return true;
        }
        else if(alea > 0.5 && choix) {
            return false;
        }
        else if (alea <= 0.5 && !choix){
            return false;
        }
        else {
            return true;
        }
    }

    public void supprimerTerrain() {
        this.terrain = null;
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

    public static String listPartie() {
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/ListPartie", "GET", null);
        String string = null;
        try {
            string = r.execute().get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String listTerrain(String partie) {
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/GetTerrain/"+partie+"", "GET", null);
        String string = null;
        try {
            string = r.execute().get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static void create(String namePartie, String dateFinale) {
        HashMap<String,String> data = new HashMap<>();
        data.put("name", namePartie);
        data.put("dateFin", dateFinale);
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/AddPartie/","POST",data);
        try {
            r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void addTerrain(String namePartie, String coordonnee,String type) {
        HashMap<String,String> data = new HashMap<>();
        data.put("partie", namePartie);
        data.put("coordonnee", coordonnee);
        data.put("type", type);
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/AddTerrain/","POST",data);
        try {
            r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static boolean quitterPartie(String nom, String nomPartie) {
        HashMap<String,String> data = new HashMap<>();
        data.put("partie", nomPartie);
        data.put("joueur", nom);
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/Quitter","POST",data);
        try {
            r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
