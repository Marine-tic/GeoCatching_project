package iut.unice.fr.geocatching.Models;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur {

    private String username;
    private String email;


    // Constructor
    public Joueur(String username, String email) {
        this.username = username;
        this.email = email;
        //verificationMail(email);
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }


    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }


    // Methods
    @Override
    public String toString() {
        return "Username : " + username +
                " Email : " + email ;
    }

    /*public Boolean verificationMail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }*/
}
