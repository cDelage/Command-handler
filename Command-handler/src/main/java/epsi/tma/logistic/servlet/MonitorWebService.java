/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.logistic.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * This webservices target is read information for front monitor application
 * Jax-RS is Java API for RESTful Web Services To learn more about it :
 * http://spoonless.github.io/epsi-b3-javaee/javaee_web/jaxrs.html

 * @author Corentin Delage
 */
@Path("/MonitorWebService")
public class MonitorWebService {
    private static final Logger LOG = LogManager.getLogger(MonitorWebService.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(){
    LOG.info("GET MonitorWebService called");
    String version = "1.0";
    return Response.ok("version").build();
    }
}
