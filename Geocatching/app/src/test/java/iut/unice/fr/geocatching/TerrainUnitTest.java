package iut.unice.fr.geocatching;

import org.junit.Test;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Models.Terrain;
import iut.unice.fr.geocatching.Models.Zone;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;

public class TerrainUnitTest {

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
        T1.addZone(ZoneD);
        assertTrue(T1.get(3),ZoneD);
    }
    @Test
    public void supprimerTerrain() throws Exception {
        p.supprimerTerrain();

        assertNull(p.getTerrain());
    }
}
