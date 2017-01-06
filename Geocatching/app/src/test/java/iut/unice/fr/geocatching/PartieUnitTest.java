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


    @Test
    public void testPointAppartientAZone() throws Exception {
        Zone maZone =  new Zone();
        Point A = new Point(1,1);
        Point B = new Point(3,1);
        Point C = new Point(1,3);
        Point D = new Point(2,2);
        Point D = new Point(4,4);
        Zone.add(A);
        Zone.add(B);
        Zone.add(C);
        assertTrue(true,Zone.appartient(A));
        assertTrue(p.action(false, Zone.appartient(B)));
    }
    @Test
    public void testSuppressionZoneTerrain(){
        Zone ZoneA = new Zone();
        Zone ZoneB = new Zone();
        Zone ZoneC = new Zone();
        List<Zone> maListeZone = new List<Zone>();
        maListeZone.add(ZoneA);
        maListeZone.add(ZoneB);
        maListeZone.add(ZoneC);
        Terrain T1 = new Terrain(maListeZone);
        T1.delete(0);
        assertTrue(p.action(ZoneB,T1.get(0)));
    }

    @Test
    public void testAjoutZonzTerrain(){
        Zone ZoneA = new Zone();
        Zone ZoneB = new Zone();
        Zone ZoneC = new Zone();
        Zone ZoneD = new Zone();
        List<Zone> maListeZone = new List<Zone>();
        maListeZone.add(ZoneA);
        maListeZone.add(ZoneB);
        maListeZone.add(ZoneC);
        Terrain T1 = new Terrain(maListeZone);
        T1.addZone(D);
        assertTrue(T1.get(3),ZoneD);
    }
    @Test
    public void supprimerTerrain() throws Exception {
        p.supprimerTerrain();

        assertNull(p.getTerrain());
    }
}
