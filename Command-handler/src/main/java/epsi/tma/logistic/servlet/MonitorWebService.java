/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.tma.logistic.servlet;

import epsi.tma.logistic.service.IDatabaseVersioningService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This webservices target is read information for front monitor application
 * Jax-RS is Java API for RESTful Web Services To learn more about it :
 * http://spoonless.github.io/epsi-b3-javaee/javaee_web/jaxrs.html
 *
 * @author Corentin Delage
 */
@Path("/monitorWebService")
public class MonitorWebService {

    private static final Logger LOG = LogManager.getLogger(MonitorWebService.class);

    private IDatabaseVersioningService databaseVersioningService;

    /*
     *
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDatabaseVersion")
    public Response getVersion(@Context ServletContext servletContext) {
        LOG.info("get version of database call");
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.databaseVersioningService = appContext.getBean(IDatabaseVersioningService.class);
            return Response.ok(databaseVersioningService.findMyVersionByKey("DatabaseVersion")).build();
        } catch (Exception e) {
            LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateDatabase")
    public Response updataDatabase(@Context ServletContext servletContext) {
        try {
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.databaseVersioningService = appContext.getBean(IDatabaseVersioningService.class);
            return Response.ok(databaseVersioningService.updateDatabaseVersion()).build();
        } catch (Exception e) {
            LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/readDatabaseInfo")
    public Response readDatabaseInfo(@Context ServletContext servletContext) {
        try {
            System.out.println("ReadDatabaseInfo webservice call");
            ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.databaseVersioningService = appContext.getBean(IDatabaseVersioningService.class);
            return Response.ok(databaseVersioningService.readDatabaseInformation()).build();
        } catch (Exception e) {
            LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timeStamp")
    public Response timeStamp() {
        try {
            Timestamp maDate = new Timestamp(System.currentTimeMillis());
            return Response.ok(maDate.toString()    ).build();
        } catch (Exception e) {
            LOG.error("Catch error during databaseVersioningService running by monitor web service", e);
            return Response.ok(e).build();
        }
    }

}
