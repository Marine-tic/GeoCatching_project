package Class;

import java.util.ArrayList;

/**
 * Created by Jérémy on 19/02/2017.
 */
public class Equipe {
    private String name;
    private ArrayList<String> listPlayer;

    public Equipe(String name) {
        this.setName(name);
        setListPlayer(new ArrayList<String>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getListPlayer() {
        return listPlayer;
    }

    public void setListPlayer(ArrayList<String> listPlayer) {
        this.listPlayer = listPlayer;
    }

    public void addPlayer(String p){
        listPlayer.add(p);
    }

    public void removePlayer(String name){
        int temp = 0;
        for(int i=0; i<listPlayer.size(); i++){
            if(listPlayer.get(i).equals(name)){
                temp = i;
            }
        }
        listPlayer.remove(temp);
    }
}
