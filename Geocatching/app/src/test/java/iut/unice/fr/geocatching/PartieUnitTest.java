package iut.unice.fr.geocatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import iut.unice.fr.geocatching.Models.Partie;
import iut.unice.fr.geocatching.Models.Zone;

import static org.junit.Assert.*;

/**
 * Created by Lomenne on 06/01/2017.
 */

public class PartieUnitTest {

    private Partie p;

    @Before public void initialize() {
        p = new Partie("maPartie", new Date(2018,01,06));
    }

    @After public void reset(){
        p = null;
    }

    @Test
    public void testConstructeur() throws Exception {
        assertEquals("maPartie",p.getNom());
        assertTrue(new Date().getTime() - p.getDateDebut().getTime() < 1000); //Comparaison de date à une seconde près
        assertEquals(new Date(2018,01,06), p.getDateFin());
    }

    @Test
    public void testRejouer() throws Exception {
        p.rejouer(new Date(2018,02,06));
        assertEquals("maPartie",p.getNom());
        assertTrue(new Date().getTime() - p.getDateDebut().getTime() < 1000); //Comparaison de date à une seconde près
        assertEquals(new Date(2018,02,06), p.getDateFin());
    }

    @Test
    public void testAction() throws Exception {
        for(int i = 0; i < 10; i++) {
            assertTrue(p.action(true, Math.random() * 0.5));
            assertTrue(p.action(false, (Math.random() * 0.5) + 0.5));
            assertFalse(p.action(false, Math.random() * 0.5));
            assertFalse(p.action(true, (Math.random() * 0.5) + 0.5));
        }
    }
}
