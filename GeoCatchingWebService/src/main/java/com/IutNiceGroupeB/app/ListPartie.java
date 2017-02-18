package com.IutNiceGroupeB.app;

import Class.*;
import Model.Model_Partie;
import Services.PartieService;
import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jérémy on 21/01/2017.
 */
@Path("/Partie")
public class ListPartie implements PartieService{

    @Override
    public Response AddPartie(String _partie){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JSONObject part = null;
        try {
            part = new JSONObject(_partie);
            String name = part.optString("name");
            String dateFin = part.optString("dateFin");
            try {
                Date date = formatter.parse(dateFin);

                Partie p = new Partie(name, date);
                Model_Partie.Add(p);

                return Response.status(200).entity("OK").build();
            } catch (ParseException e) {
                return Response.status(Response.Status.CONFLICT).entity("Date wrong format").build();
            }
        } catch (JSONException e) {
            return Response.status(400).entity("Invalid input.").build();
        }
    }

    @Override
    public Response AddTerrain(String _terrain){
        JSONObject terrain = null;
        try {
            terrain = new JSONObject(_terrain);
            String name = terrain.optString("partie");
            String coordonnee = terrain.optString("coordonnee");
            String type = terrain.optString("type");

            String[] parts = coordonnee.split("-");
            ArrayList<String> al = new ArrayList<String>();
            for(int i =0; i < parts.length ; i++){
                al.add(parts[i]);
            }
            for (int i=0; i<Model_Partie.Size(); i++) {
                if(Model_Partie.Get(i).getNom().equals(name)){
                    if(type.equals("0")) {
                        Terrain t = new Terrain(al);
                        Model_Partie.Get(i).ajouterTerrain(t);
                    }
                    else{
                        Zone z = new Zone(al,0,null);
                        Model_Partie.Get(i).getTerrain().ajouterZone(z);
                    }

                    return Response.status(200).entity("OK").build();
                }
            }

            return Response.status(404).entity("Game not found").build();
        } catch (JSONException e) {
            return Response.status(400).entity("Invalid input.").build();
        }
    }

    @Override
    public Response GetTerrain(String name) {
        for (int i=0; i<Model_Partie.Size(); i++) {
            if(Model_Partie.Get(i).getNom().equals(name)){
                Gson gson = new Gson();
                String json = gson.toJson(Model_Partie.Get(i).getTerrain());
               // json += gson.toJson(Model_Partie.Get(i).getTerrain().getZone());

                return Response.status(200).entity(json).build();
            }
        }
        return Response.status(404).entity("Game not found").build();
    }

    @Override
    public Response DeletePartie(String name) {
        for (int i=0; i<Model_Partie.Size(); i++) {
            if(Model_Partie.Get(i).getNom().equals(name)){
                Model_Partie.Remove(i);

                return Response.status(200).entity("OK").build();
            }
        }
        return Response.status(404).entity("Game not found").build();
    }

    @Override
    public Response GetPartie() {
        Gson gson = new Gson();
        String json = gson.toJson(Model_Partie.GetAll());

        return Response.status(200).entity(json).build();
    }
}
