package Class;

public class Player {
    private String _username;
    private String _latitude;
    private String _longitude;

    public Player(String username, String latitude, String longitude){
        _username = username;
        _latitude = latitude;
        _longitude = longitude;
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
}
