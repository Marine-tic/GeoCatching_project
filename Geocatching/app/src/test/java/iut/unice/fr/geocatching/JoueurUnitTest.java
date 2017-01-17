package iut.unice.fr.geocatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import iut.unice.fr.geocatching.Models.Joueur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({TextUtils.class})

public class JoueurUnitTest {

    public Joueur j;

    @Before
    public void initialize() {
     //   j = new Joueur("monPseudo", "monEmail@monemail.fr");
    }

    @After
    public void reset(){
        j = null;
    }

    @Test
    public void testConstructeur() throws Exception {
        assertEquals("monPseudo",j.getUsername());
        assertEquals("monEmail@monemail.fr", j.getEmail());
    }

    @Test
    public void testToString()
    {

        assertEquals("Username : monPseudo Email : monEmail@monemail.fr", j.toString());
        assertNotEquals("Username : monNom Email : monEmail@monemail.fr", j.toString());
    }

    @Test
    public void testValidationEmail() throws Exception {
        assertTrue(j.verificationMail("monEmail@monemail.fr"));
        assertFalse(j.verificationMail("monEmail-monemail.fr"));
    }



}
