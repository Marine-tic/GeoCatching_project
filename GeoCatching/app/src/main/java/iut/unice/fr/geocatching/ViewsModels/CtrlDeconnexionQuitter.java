package iut.unice.fr.geocatching.ViewsModels;

import iut.unice.fr.geocatching.Models.Joueur;
import iut.unice.fr.geocatching.Models.Partie;

/**
 * Created by jérémy on 19/02/2017.
 */

public class CtrlDeconnexionQuitter{

    public static boolean deconnexion(String nom){
        return Joueur.deconnexion(nom);
    }
    public static boolean quitterPartie(String nom, String nomPartie, String nameEquipe){
        return Partie.quitterPartie(nom,nomPartie, nameEquipe);
    }
}
