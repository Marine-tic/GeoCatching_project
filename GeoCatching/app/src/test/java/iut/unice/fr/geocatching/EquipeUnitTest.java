package iut.unice.fr.geocatching;

import org.junit.Before;
import org.junit.Test;

import iut.unice.fr.geocatching.Models.Equipe;
import iut.unice.fr.geocatching.Models.Joueur;

import static org.junit.Assert.*;

/**
 * Created by laura on 06/01/2017.
 */

public class EquipeUnitTest {

    private Equipe e;
    private Joueur joueur;
    private Joueur joueur2;
    private Joueur joueur3;

    @Before
    public void initialize() {
        e = new Equipe("Equipe"); // création d'une équipe

        // création de joueurs
        /*joueur = new Joueur("Bidule", "bidule@gmail.com");
        joueur2 = new Joueur("Machin", "machin@gmail.com");
        joueur3 = new Joueur("Chose", "chose@gmail.com");*/
    }

    @Test
    public void testConstructeur() throws Exception {
        assertEquals("Equipe",e.getNom());
    }

    @Test
    public void testAjoutJoueur() throws Exception {

        // ajout de joueurs
        e.ajoutJoueur(joueur);
        e.ajoutJoueur(joueur2);
    }

    @Test
    public void testSupprimerJoueur() throws Exception {

        // suppression de joueurs
        e.supprimerJoueur(joueur);
        e.supprimerJoueur(joueur3); // ce joueur n'est pas dans la liste
    }
}
