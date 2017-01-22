package iut.unice.fr.geocatching;

import com.google.android.gms.maps.model.LatLng;

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
        joueur = new Joueur("Bidule", "bidule@gmail.com", new LatLng(255,8546),true);
        joueur2 = new Joueur("Machin", "machin@gmail.com", new LatLng(255,7536),true);
        joueur3 = new Joueur("Chose", "chose@gmail.com", new LatLng(822,86),true);
    }

    @Test
    public void testConstructeur() throws Exception {
        assertEquals("Equipe",e.getNom());
    }

    @Test
    public void testAjoutJoueur() throws Exception {
        int size = e.getlJoueur().size();
        // ajout de joueurs
        e.ajoutJoueur(joueur);
        e.ajoutJoueur(joueur2);
        assertEquals(size+2, e.getlJoueur().size());
    }

    @Test
    public void testSupprimerJoueur() throws Exception {
        e.ajoutJoueur(joueur);
        e.ajoutJoueur(joueur2);
        e.ajoutJoueur(joueur3);
        int size = e.getlJoueur().size();
        // suppression de joueurs
        e.supprimerJoueur(joueur);
        e.supprimerJoueur(joueur3);
        assertEquals(size-2, e.getlJoueur().size());
    }
}
