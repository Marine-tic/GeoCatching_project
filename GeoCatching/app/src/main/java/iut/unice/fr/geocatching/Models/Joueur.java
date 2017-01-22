package iut.unice.fr.geocatching.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import iut.unice.fr.geocatching.Helpers.Point;

/**
 * Created by Ludivine Fafournoux on 06/01/2017.
 */

public class Joueur {

    private String username;
    private String email;
    private LatLng position;
    private Boolean isConnected;

    // Constructor
    public Joueur(String username, String email, LatLng position, Boolean isConnected) {
        this.username = username;
        this.email = email;
        this.position = position;
        verificationMail(email);
        this.isConnected = isConnected;
    }

    // Getters
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public LatLng getPosition() {
        return position;
    }
    public Boolean getIsConnected() {
        return isConnected;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

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
