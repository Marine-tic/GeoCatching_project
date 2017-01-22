package com.IutNiceGroupeB.app;

import Class.*;
import Model.Model_Partie;
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
public class ListPartie {

    @GET
    @Path("/AddPartie/{name}/{dateFin}")
    public Response AddPartie(@PathParam("name") String name, @PathParam("dateFin") String dateFin){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateFin);

            Partie p = new Partie(name, date);
            Model_Partie.Add(p);

            return Response.status(200).entity("OK").build();
        } catch (ParseException e) {
            return Response.status(Response.Status.CONFLICT).entity("wrong date").build();
        }
    }

    @GET
    @Path("/AddTerrain/{type}/{partie}/{coordonnee}")
    public Response AddTerrain(@PathParam("coordonnee") String name,@PathParam("coordonnee") String coordonnee,@PathParam("type") String type){
        String[] parts = coordonnee.split("-");
        ArrayList<String> al = new ArrayList<String>();
        for(int i =0; i < parts.length-1 ; i++){
            al.add(parts[i]);
        }
        for (int i=0; i<Model_Partie.Size(); i++) {
            if(Model_Partie.Get(i).getNom().equals(name)){

                if(type == "0") {
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
    }
}
