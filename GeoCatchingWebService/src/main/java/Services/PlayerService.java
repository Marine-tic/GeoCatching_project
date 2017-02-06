package Services;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Jérémy on 05/02/2017.
 */
public interface PlayerService {
    @POST
    @Path("/Add")
    Response AddPlayer(String player);

    @GET
    @Path("/Deconnection/{name}")
    Response DeletePlayer(@PathParam("name") String name);

    @POST
    @Path("/UpdatePosistion/{name}")
    Response Update(@PathParam("name") String name, String Position);


    @GET
    @Path("/List")
    Response getListPlayer();
}
