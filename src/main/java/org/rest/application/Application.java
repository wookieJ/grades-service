package org.rest.application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.rest.data.Data;

import java.net.URI;

public class Application
{
    /**
     * URI where application started at.
     */
    public static final String BASE_URI = "http://localhost:8080";

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer basef on BASE_URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static HttpServer startServer()
    {
        final ResourceConfig rc = new ResourceConfig().packages("com.cognifide.endpoints");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args)
    {
        try
        {
            // loading students to static list
            Data.loadData();

            // starting server
            startServer();
            System.out.println(String.format("Homework app started at " + BASE_URI));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}