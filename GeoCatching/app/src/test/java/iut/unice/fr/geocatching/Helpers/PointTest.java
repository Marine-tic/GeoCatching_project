package iut.unice.fr.geocatching.Helpers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Auna on 06/01/2017.
 */
public class PointTest {

    private Point point;
    private Point invalidPoint1;
    private Point invalidPoint2;

    @Before
    public void setUp() throws Exception {
        point = new Point(-43.616975d, 7.071106d);
    }

    @Test
    public void testLongitude(){
        //System.out.println("Longitude :" + point.getLongitude());
        assertTrue(point.getLongitude() == -43.616975d);
    }

    @Test
    public void testLatitude() {
      //  System.out.println("Latitude :" + point.getLatitude());
        assertTrue(point.getLatitude() == 7.071106d);
    }

    @Test (expected = Exception.class)
    public void testInvalidPoint1() throws Exception {
        invalidPoint1 = new Point(-183.616975d, 7.071106d);
    }

    @Test (expected = Exception.class)
    public void testInvalidPoint2() throws Exception {
        invalidPoint2 = new Point(-13.616975d, 97.071106d);
    }

}