package Model;

import java.util.ArrayList;
import java.util.Date;

import Class.Partie;

/**
 * Created by Jérémy on 21/01/2017.
 */
public class Model_Partie {
    private static ArrayList<Partie> p = new ArrayList<Partie>();

    public static void Add(Partie partie){
        p.add(partie);
    }

    public static ArrayList<Partie> GetAll() {
        return p;
    }

    public static Partie Get(int i) {
        if(p.size() > 0)
            return p.get(i);
        else
            // Création d'une partie par défaut pour éviter un crash du webservice par un pointeur null
            return new Partie("Default", new Date(),null);
    }

    public static int Size() {
        return p.size();
    }

    public static void Remove(int i) {
        p.remove(i);
    }
}
