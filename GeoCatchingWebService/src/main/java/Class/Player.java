package Class;

import java.util.Date;

public class Player {
    private String _username;
    private String _latitude;
    private String _longitude;
    private Date lastUpdate;

    public Player(String username, String latitude, String longitude){
        _username = username;
        _latitude = latitude;
        _longitude = longitude;
        lastUpdate = new Date();
    }

    public String toString() {
        return "[" + _username + " " + _latitude +
                " " + _longitude +"]";
    }

    public String Getusername(){
        return _username;
    }

    public void SetLatitude(String latitude){
        _latitude = latitude;
    }

    public void SetLongitude(String longitude){
        _longitude = longitude;
    }

    public void SetLastUpdate(){
        lastUpdate = new Date();
    }

    public Boolean toOld(){
        Date now = new Date();
        if(now.getTime() - lastUpdate.getTime() > 30000){
            return true;
        }
        else{
            return false;
        }
    }
}
