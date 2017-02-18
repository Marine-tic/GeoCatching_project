package iut.unice.fr.geocatching.ViewsModels;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Models.Partie;

/**
 * Created by jérémy on 18/02/2017.
 */

public class VMJoinMapsActivity extends VMListePartieActivity {
    Partie partieChoisi;
    public VMJoinMapsActivity(String nom) {
        for (Partie maPartie: this.getPartieListe()){
            if(maPartie.getNom().equals(nom)){
                System.out.println("la");
                partieChoisi = maPartie;
            }
        }
    }
    public Partie getPartie(){
        return partieChoisi;
    }
}
