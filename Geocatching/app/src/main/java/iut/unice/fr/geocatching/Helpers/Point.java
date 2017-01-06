package iut.unice.fr.geocatching.Helpers;

/**
 * Created by Auna on 06/01/2017.
 */

public class Point {

    /**
     * Logitude is valid between -180 degrees and 180 degrees inclusive
     */
    private Double longitude;
    private Double latitude;


    // Constructor
    public Point(double longitude, double latitude) throws Exception {
        if(isValidLongitude(longitude)) {
            this.longitude = longitude;
        } else {
            throw new Exception("Longitude must be between -180 and 180 degrees inclusive.");
        }
        if(isValidLatitude(latitude)) {
            this.latitude = latitude;
        } else {
            throw new Exception("Latitude must be between -90 and 90 degrees inclusive.");
        }

    }

    // Getters
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }


    // Setters
    public void setLongitude(double longitude) throws Exception {
        if(isValidLongitude(longitude)) {
            this.longitude = longitude;
        } else {
            throw new Exception("Longitude must be between -180 and 180 degrees inclusive.");
        }
    }
    public void setLatitude(double latitude) throws Exception {
        if(isValidLatitude(latitude)) {
            this.latitude = latitude;
        } else {
            throw new Exception("Latitude must be between -90 and 90 degrees inclusive.");
        }
    }


    // Methods
    @Override
    public String toString() {
        return "Longitude : " + longitude +
                "Latitude : " + latitude ;
    }

    private boolean isValidLongitude(double longitude){
        return longitude >= -180.0d && longitude <= 180.0d;
    }
    private boolean isValidLatitude(double latitude){
        return latitude >= -90.0d && latitude <= 90.0d;
    }


}
