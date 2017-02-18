package iut.unice.fr.geocatching;

import org.junit.Test;

import iut.unice.fr.geocatching.Helpers.Points;

import iut.unice.fr.geocatching.Models.Zone;

/**
 * Created by jérémy on 16/01/2017.
 */

public class ZoneUnitTest {

    @Test
    public void testPointAppartientAZone() throws Exception {
        Zone  Zone1= new Zone();
        Zone maZone =  new Zone();
        Points A = new Points(1,1);
        Points B = new Points(3,1);
        Points C = new Points(1,3);
        Points D = new Points(2,2);
        Points E = new Points(6,6);
        /*Zone1.ajouterPoint(A);
        Zone1.ajouterPoint(B);
        Zone1.ajouterPoint(C);
        assertFalse(Zone1.appartient(E));
        assertTrue(Zone1.appartient(D));*/
    }



}
