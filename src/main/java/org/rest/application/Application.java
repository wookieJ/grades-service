package org.rest.application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

// TODO - authorization
// TODO - return empty list, not 404 response code
// TODO - write comments
public class Application {
    /**
     * URI where application started at.
     */
    public static final String BASE_URI = "http://localhost:8080";

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer basef on BASE_URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static HttpServer startServer() {
        ResourceConfig rc = new ResourceConfig()
                .packages("org.glassfish.jersey.examples.linking")
                .register(DeclarativeLinkingFeature.class)
                .packages("org.rest.endpoints");
        rc.register(org.rest.converters.DateParamConverterProvider.class);
        rc.register(org.rest.exceptions.AppExceptionMapper.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        try {
            // starting server
            startServer();
            System.out.println(String.format("Homework app started at " + BASE_URI));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}