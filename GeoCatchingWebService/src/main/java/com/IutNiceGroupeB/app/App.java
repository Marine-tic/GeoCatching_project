package com.IutNiceGroupeB.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Hello world!
 *
 */
/*public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    }
}*/

@Path("/hello")
public class App {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Bonjour " + msg;
		return Response.status(200).entity(output).build();
	}
}
