package iut.unice.fr.geocatching;

import org.junit.Test;

import iut.unice.fr.geocatching.Helpers.Point;
import iut.unice.fr.geocatching.Models.Terrain;
import iut.unice.fr.geocatching.Models.Zone;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TerrainUnitTest {

    @Test
    public void testSuppressionZoneTerrain(){
        Zone ZoneA = new Zone();
        Zone ZoneB = new Zone();
        Zone ZoneC = new Zone();
        Terrain T1 = new Terrain();
        T1.ajouterZone(ZoneA);
        T1.ajouterZone(ZoneB);
        T1.ajouterZone(ZoneC);
        T1.delete(0);
        assertEquals(null,T1.get(0));
    }
    @Test
    public void testAjoutZonzTerrain(){
        Zone ZoneD = new Zone();
        Terrain T1 = new Terrain();
        T1.ajouterZone(ZoneD);
        assertEquals(T1.get(0),ZoneD);
    }
}
