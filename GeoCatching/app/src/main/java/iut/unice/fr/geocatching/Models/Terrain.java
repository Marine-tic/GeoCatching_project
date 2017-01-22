package iut.unice.fr.geocatching.Models;

import java.util.ArrayList;

/**
 * Created by Loic Mennella on 06/01/2017.
 */

public class Terrain {
    private ArrayList<Zone> listZone;

    public Terrain() {
        listZone = new ArrayList<>();
    }

    public void resetAllZone() {
        for (Zone zone: listZone) {
            zone.resetZone();
        }
    }

    public void delete(int i) {
        listZone.remove(i);
    }

    public void ajouterZone(Zone zoneA) {
        listZone.add(zoneA);
    }

    public Zone get(int i) {
        return listZone.get(i);
    }

    public ArrayList<Zone> getListZone() {
        return listZone;
    }
}
