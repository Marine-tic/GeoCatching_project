package iut.unice.fr.geocatching.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import iut.unice.fr.geocatching.Helpers.Request;

/**
 * Created by laura on 06/01/2017.
 */

public class Equipe {

    private String nom;
    private ArrayList<Joueur> lJoueur;

    public Equipe() {
        this.nom = "";
        lJoueur = new ArrayList<>();
    }

    // Constructor
    public Equipe(String nom) {
        this.nom = nom;
        lJoueur = new ArrayList<>();
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Methods
    @Override
    public String toString() {
        return nom;
    }

    public void ajoutJoueur(Joueur joueur) {
        getlJoueur().add(joueur); // ajout d'un joueur à la liste
    }

    public void supprimerJoueur(Joueur joueur) {
       if (getlJoueur().contains(joueur)) { // si le joueur fait bien partie de la liste
            getlJoueur().remove(joueur); // suppression du joueur de la liste
       }
    }

    public ArrayList<Joueur> getlJoueur() {
        return lJoueur;
    }

    public static void joinEquipe(String name, String namePartie, String nameEquipe) {
        HashMap<String,String> data = new HashMap<>();
        data.put("equipe", nameEquipe);
        data.put("joueur", name);
        Request r = new Request("http://iut-outils-gl.i3s.unice.fr/jetty/dam-b/Partie/Rejoindre/"+namePartie, "POST", data);
        try {
            r.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
