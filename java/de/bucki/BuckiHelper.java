package de.bucki;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/bucki")

public class BuckiHelper {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/change/{name}")
    public String changeCheck(@PathParam String name) {
        BuckiService bs = new BuckiService();
        return bs.changeCheck(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/changeData/{name}/{key}/{value}")
    public String changeCheckData(@PathParam String name, @PathParam String key, @PathParam String value) {
        BuckiService bs = new BuckiService();
        return bs.changeCheckData(name, key, value);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/deleteData/{name}/{key}")
    public String changeCheckData(@PathParam String name, @PathParam String key) {
        BuckiService bs = new BuckiService();
        return bs.deleteCheckData(name, key);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello Bucki";
    }
}
