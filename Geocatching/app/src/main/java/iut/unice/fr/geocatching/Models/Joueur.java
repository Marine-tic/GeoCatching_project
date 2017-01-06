package iut.unice.fr.geocatching.Models;

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
        verificationMail(email);
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

    public Boolean verificationMail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
