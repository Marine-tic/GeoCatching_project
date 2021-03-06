package Class;

import java.util.ArrayList;

/**
 * Created by Jérémy on 21/01/2017.
 */
public class Terrain {
    private ArrayList<String> coordonnees;
    private ArrayList<Zone> listZone;

    public Terrain(ArrayList<String> c) {
        listZone = new ArrayList<Zone>();
        coordonnees = c;
    }

    public void resetAllZone() {
        listZone = new ArrayList<Zone>();
    }

    public void delete(int i) {
        listZone.remove(i);
    }

    public void ajouterZone(Zone zoneA) {
        listZone.add(zoneA);
    }

    public String toString(){
        String c = "";
        for (String s :coordonnees) {
            c += s;
        }
        return c;
    }

    public ArrayList<Zone> getZone() {
        return listZone;
    }
}
