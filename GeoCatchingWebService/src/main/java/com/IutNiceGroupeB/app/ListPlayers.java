package com.IutNiceGroupeB.app;

import Class.Player;
import Model.Model_ListPlayer;
import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/ListPlayers")
public class ListPlayers {

    @GET
    @Path("/Add/{name}/{latitude}/{longitude}")
    public Response AddPlayer(@PathParam("name") String name, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude){
        Player p = new Player(name,latitude,longitude);
        Model_ListPlayer.Add(p);

        return Response.status(200).entity("OK").build();
    }

    @GET
    @Path("/Deconnection/{name}")
    public Response DeletePlayer(@PathParam("name") String name){
        for (int i=0; i<Model_ListPlayer.Size(); i++) {
            if(Model_ListPlayer.Get(i).Getusername().equals(name)){
                Model_ListPlayer.Remove(i);

                return Response.status(200).entity("OK").build();
            }
        }

        return Response.status(404).entity("Player not found").build();
    }

    @GET
    @Path("/UpdatePosistion/{name}/{latitude}/{longitude}")
    public Response Update(@PathParam("name") String name, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude){
        for (int i=0; i<Model_ListPlayer.Size(); i++) {
            if(Model_ListPlayer.Get(i).Getusername().equals(name)){
                Model_ListPlayer.Get(i).SetLatitude(latitude);
                Model_ListPlayer.Get(i).SetLongitude(longitude);
                String s = Model_ListPlayer.Get(i).toString();

                return Response.status(200).entity("OK").build();
            }
        }

        return Response.status(404).entity("Player not found").build();
    }

    @GET
    @Path("/List")
    public Response getListPlayer(){
        Gson gson = new Gson();
        String json = gson.toJson(Model_ListPlayer.GetAll());

        return Response.status(200).entity(json).build();
    }
}
