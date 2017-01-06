package iut.unice.fr.geocatching.Models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import iut.unice.fr.geocatching.Helpers.Point;

import static org.junit.Assert.*;

/**
 * Created by Auna on 06/01/2017.
 */
public class ZoneTest {
    private List<Point> coordonnees;
    private Equipe equipe;
    private Zone zone;

    @Before
    public void setUp() throws Exception {
        coordonnees = new ArrayList<>();
        coordonnees.add(new Point(40.0d, 5.0d));
        coordonnees.add(new Point(45.0d, 15.0d));
        coordonnees.add(new Point(60.5d, 55.0d));
        equipe = new Equipe();
        zone = new Zone(coordonnees, 1, equipe);
    }

    @Test
    public void checkResetZone() throws Exception {
        zone.resetZone();
        int cpt=0;
        for (Point point : coordonnees) {
            if(point.getLatitude() == 0.0d && point.getLongitude() == 0.0d){
                ++cpt;
            }
        }
        assertEquals(coordonnees.size(), cpt);
    }

}