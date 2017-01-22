package iut.unice.fr.geocatching;

import org.junit.Test;

import iut.unice.fr.geocatching.Models.Terrain;
import iut.unice.fr.geocatching.Models.Zone;
import static junit.framework.Assert.assertEquals;

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
        int size = T1.getListZone().size();
        T1.delete(0);
        assertEquals(size-1,T1.getListZone().size());
    }
    @Test
    public void testAjoutZonzTerrain(){
        Zone ZoneD = new Zone();
        Terrain T1 = new Terrain();
        T1.ajouterZone(ZoneD);
        assertEquals(T1.get(0),ZoneD);
    }
}
