package com.IutNiceGroupeB.app;

import Class.Player;
import Model.Model_ListPlayer;
import Services.PlayerService;
import com.google.gson.Gson;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/ListPlayers")
public class ListPlayers implements PlayerService{

    @Override
    public Response AddPlayer(String player){
        JSONObject joueur = null;
        try {
            joueur = new JSONObject(player);
            String name = joueur.optString("name");
            String latitude = joueur.optString("latitude");
            String longitude = joueur.optString("longitude");
            if(canAdd(name)){
                Player p = new Player(name,latitude,longitude);
                Model_ListPlayer.Add(p);

                return Response.status(200).entity("OK").build();
            }
            else{
                return Response.status(400).entity("Player exist").build();
            }
        } catch (JSONException e) {
            return Response.status(400).entity("Invalid input.").build();
        }
    }

    private boolean canAdd(String name) {
        for (int i=0; i<Model_ListPlayer.Size(); i++) {
            if(Model_ListPlayer.Get(i).Getusername().equals(name)){
                return false;
            }
        }

        return true;
    }

    @Override
    public Response DeletePlayer(String name){
        for (int i=0; i<Model_ListPlayer.Size(); i++) {
            if(Model_ListPlayer.Get(i).Getusername().equals(name)){
                Model_ListPlayer.Remove(i);

                return Response.status(200).entity("OK").build();
            }
        }

        return Response.status(400).entity("Player not found").build();
    }

    @Override
    public Response Update(String name, String Position){
        for (int i=0; i<Model_ListPlayer.Size(); i++) {
            if(Model_ListPlayer.Get(i).Getusername().equals(name)){
                JSONObject pos = null;
                try {
                    pos = new JSONObject(Position);
                    String latitude = pos.optString("latitude");
                    String longitude = pos.optString("longitude");
                    Model_ListPlayer.Get(i).SetLatitude(latitude);
                    Model_ListPlayer.Get(i).SetLongitude(longitude);
                    String s = Model_ListPlayer.Get(i).toString();

                    return Response.status(200).entity("OK").build();
                } catch (JSONException e) {
                    return Response.status(400).entity("Invalid input.").build();
                }
            }
        }

        return Response.status(400).entity("Player not found").build();
    }

    @Override
    public Response getListPlayer(){
        Gson gson = new Gson();
        String json = gson.toJson(Model_ListPlayer.GetAll());

        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
}
