package iut.unice.fr.geocatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import iut.unice.fr.geocatching.Helpers.Point;

import static org.junit.Assert.*;

import iut.unice.fr.geocatching.Models.Terrain;
import iut.unice.fr.geocatching.Models.Zone;

/**
 * Created by jérémy on 16/01/2017.
 */

public class ZoneUnitTest {

    @Test
    public void testPointAppartientAZone() throws Exception {
        Zone  Zone1= new Zone();
        Zone maZone =  new Zone();
        Point A = new Point(1,1);
        Point B = new Point(3,1);
        Point C = new Point(1,3);
        Point D = new Point(2,2);
        Point E = new Point(6,6);
        /*Zone1.ajouterPoint(A);
        Zone1.ajouterPoint(B);
        Zone1.ajouterPoint(C);
        assertFalse(Zone1.appartient(E));
        assertTrue(Zone1.appartient(D));*/
    }



}
