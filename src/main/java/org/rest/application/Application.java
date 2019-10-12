package org.rest.application;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

// TODO - authorization
// TODO - return empty list, not 404 response code
// TODO - write comments
// TODO - add MANIFEST for running jar file
// TODO - throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("Not found").build());
public class Application {

    private static final String HOST_NAME = "0.0.0.0";

    /**
     * URI where application started at.
     */
    private static URI getBaseURI(String hostname, int port) {
        return UriBuilder.fromUri(hostname).port(port).build();
    }

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer based on base URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static void main(String[] args) {
        try {
            ResourceConfig rc = new ResourceConfig()
                    .packages("org.glassfish.jersey.examples.linking")
                    .register(DeclarativeLinkingFeature.class).packages("org.rest.endpoints");
            rc.register(org.rest.converters.DateParamConverterProvider.class);
            rc.register(org.rest.exceptions.AppExceptionMapper.class);
            rc.register(org.rest.config.CorsConfig.class);
            int port = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 9998;
            String host = "http://" + HOST_NAME + "/";
            GrizzlyHttpServerFactory.createHttpServer(getBaseURI(host, port), rc);
            System.out.println("Grizzly server runs at " + host + ":" + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}