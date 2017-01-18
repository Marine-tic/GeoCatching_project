package com.IutNiceGroupeB.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Path("/validate")
public class AuthenticationCheck {

    /**
     * Validates login token with authentication web service.
     *
     * @param token - the token to check.
     * @throws AuthorizationCheckException if an error occurs during check
     * @throws UnauthorizedException if the token is invalid
     */
    @GET
    @Path("/{param}")
    public static Response validateToken(@PathParam("param") String token) throws AuthorizationCheckException {
        if (token == null) {
            throw new UnauthorizedException("Authorization token must be provided");
        }
        String url = "http://iut-outils-gl.i3s.unice.fr/jetty/authentication/session";
        String charset = "UTF-8";

        try {
            String query = String.format("token=%s", URLEncoder.encode(token, charset));
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            OutputStream output = connection.getOutputStream();
            output.write(query.getBytes(charset));

            int status = connection.getResponseCode();
            if (status != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new UnauthorizedException("Invalid token");
            }
            String result = "OK";
            connection.disconnect();

            return Response.status(200).entity(result).build();
        } catch (IOException e) {
            throw new AuthorizationCheckException("Error while authentication", e);
        }
    }

}