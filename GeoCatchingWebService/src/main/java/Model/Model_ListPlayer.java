package Model;

import Class.Player;

import java.util.ArrayList;

/**
 * Created by Jérémy on 18/01/2017.
 */
public class Model_ListPlayer {

    private static ArrayList<Player> lp = new ArrayList<Player>();

    public static void Add(Player p){
        lp.add(p);
    }

    public static ArrayList<Player> GetAll() {
        return lp;
    }

    public static Player Get(int i) {
        return lp.get(i);
    }

    public static int Size() {
        return lp.size();
    }

    public static void Remove(int i) {
        lp.remove(i);
    }
}
