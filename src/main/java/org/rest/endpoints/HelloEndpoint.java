package org.rest.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloEndpoint {
    @GET
    public String hello() {
        return "Hello!";
    }
}
