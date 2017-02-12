package Services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Jérémy on 05/02/2017.
 */
public interface PartieService {
    @POST
    @Path("/AddPartie")
    Response AddPartie(String _partie);

    @POST
    @Path("/AddTerrain")
    Response AddTerrain(String _terrain);

    @GET
    @Path("/GetTerrain/{name}")
    Response GetTerrain(@PathParam("name") String name);

    @GET
    @Path("/ListPartie")
    Response GetPartie();
}