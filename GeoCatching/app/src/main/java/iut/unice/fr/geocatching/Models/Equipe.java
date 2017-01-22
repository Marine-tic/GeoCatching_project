package iut.unice.fr.geocatching.Models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by laura on 06/01/2017.
 */

public class Equipe {

    private String nom;
    private List<Joueur> lJoueur;

    // Constructor
    public Equipe(String nom) {

        this.nom = nom;
        this.lJoueur =  new LinkedList<>();
    }

    // Default constructor
    public Equipe(){
        this.nom = "";
        this.lJoueur = new LinkedList<>();
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

        lJoueur.add(joueur); // ajout d'un joueur à la liste
    }

    public void supprimerJoueur(Joueur joueur) {

       if (lJoueur.contains(joueur)) { // si le joueur fait bien partie de la liste
            lJoueur.remove(joueur); // suppression du joueur de la liste
       }
    }
}
