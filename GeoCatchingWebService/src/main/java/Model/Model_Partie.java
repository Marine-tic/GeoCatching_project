package Model;

import java.util.ArrayList;
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
        return p.get(i);
    }

    public static int Size() {
        return p.size();
    }

    public static void Remove(int i) {
        p.remove(i);
    }
}
