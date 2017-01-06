package iut.unice.fr.geocatching;

import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import iut.unice.fr.geocatching.Models.Joueur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by Akeno on 06/01/2017.
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(TextUtils.class)

public class JoueurUnitTest {

    private Joueur j;

    @Before public void initialize() {
        j = new Joueur("monPseudo", "monEmail@monemail.fr");}
        /*public void setup() {
            PowerMockito.mockStatic(TextUtils.class);
            PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
                @Override
                public Boolean answer(InvocationOnMock invocation) throws Throwable {
                    CharSequence a = (CharSequence) invocation.getArguments()[0];
                    return !(a != null && a.length() > 0);
                }
            });
        }*/


    @After public void reset(){
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



}
