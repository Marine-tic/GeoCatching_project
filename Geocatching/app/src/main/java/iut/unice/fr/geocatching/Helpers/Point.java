package iut.unice.fr.geocatching.Helpers;

/**
 * Created by Auna on 06/01/2017.
 */

public class Point {

    private Double longitude;
    private Double latitude;


    // Constructor
    public Point(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters
    public Double getLongitude() {
        return longitude;
    }
    public Double getLatitude() {
        return latitude;
    }


    // Setters
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }


    // Methods
    @Override
    public String toString() {
        return "Longitude : " + longitude +
                "Latitude : " + latitude ;
    }
}
