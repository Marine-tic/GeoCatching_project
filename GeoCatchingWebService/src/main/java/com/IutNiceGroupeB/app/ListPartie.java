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
            String equipe = part.optString("equipe");
            try {
                Date date = formatter.parse(dateFin);
                String[] parts = equipe.split("-");
                ArrayList<Equipe> listEquipe = new ArrayList<Equipe>();
                for(int i =0; i < parts.length ; i++) {
                    Equipe e = new Equipe(parts[i]);
                    listEquipe.add(e);
                }

                Partie p = new Partie(name, date,listEquipe);
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

    @Override
    public Response SetLevel(String name,String zone) {
        JSONObject terrain = null;
        try {
            terrain = new JSONObject(zone);
            String equipe = terrain.optString("equipe");
            String coordonnee = terrain.optString("coordonnee");
            String niveau = terrain.optString("niveau");

            String[] parts = coordonnee.split("-");
            ArrayList<String> al = new ArrayList<String>();
            for(int i =0; i < parts.length ; i++){
                al.add(parts[i]);
            }
            for (int i=0; i<Model_Partie.Size(); i++) {
                if(Model_Partie.Get(i).getNom().equals(name)){
                    for(int j=0; j < Model_Partie.Get(i).getTerrain().getZone().size(); j++){
                        if(equals(Model_Partie.Get(i).getTerrain().getZone().get(j).getCoordonnees(), al)){
                            Model_Partie.Get(i).getTerrain().getZone().get(j).setNiveau(Integer.parseInt(niveau));
                            for(int l=0; l < Model_Partie.Get(i).getEquipe().size(); l++) {
                                if (Model_Partie.Get(i).getEquipe().get(l).getName().equals(equipe)) {
                                    Model_Partie.Get(i).getTerrain().getZone().get(j).setPosseder((Model_Partie.Get(i).getEquipe().get(l)));
                                }
                            }
                        }
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
    public Response Rejoindre(String name, String equipe) {
        JSONObject terrain = null;
        try {
            terrain = new JSONObject(equipe);
            String _equipe = terrain.optString("equipe");
            String joueur = terrain.optString("joueur");

            for (int i=0; i<Model_Partie.Size(); i++) {
                if(Model_Partie.Get(i).getNom().equals(name)){
                    for(int j=0; j < Model_Partie.Get(i).getEquipe().size(); j++){
                        if(Model_Partie.Get(i).getEquipe().get(j).getName().equals(_equipe)){
                            Model_Partie.Get(i).getEquipe().get(j).addPlayer(joueur);
                            return Response.status(200).entity("OK").build();
                        }
                    }

                    return Response.status(200).entity("Equipe not found").build();
                }
            }

            return Response.status(404).entity("Game not found").build();
        } catch (JSONException e) {
            return Response.status(400).entity("Invalid input.").build();
        }
    }

    @Override
    public Response Quitter(String name, String equipe) {
        JSONObject terrain = null;
        try {
            terrain = new JSONObject(equipe);
            String _equipe = terrain.optString("equipe");
            String joueur = terrain.optString("joueur");

            for (int i=0; i<Model_Partie.Size(); i++) {
                if(Model_Partie.Get(i).getNom().equals(name)){
                    for(int j=0; j < Model_Partie.Get(i).getEquipe().size(); j++){
                        if(Model_Partie.Get(i).getEquipe().get(j).getName().equals(_equipe)){
                            Model_Partie.Get(i).getEquipe().get(j).removePlayer(joueur);
                            return Response.status(200).entity("OK").build();
                        }
                    }
                    return Response.status(200).entity("Equipe not found").build();
                }
            }

            return Response.status(404).entity("Game not found").build();
        } catch (JSONException e) {
            return Response.status(400).entity("Invalid input.").build();
        }
    }

    private boolean equals(ArrayList<String> list1, ArrayList<String> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(Object itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }
}
