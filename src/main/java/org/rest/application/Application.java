package org.rest.application;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.net.UnknownHostException;

// TODO - authorization
// TODO - return empty list, not 404 response code
// TODO - write comments
// TODO - add MANIFEST for running jar file
public class Application {
    /**
     * URI where application started at.
     */
    private static final String BASE_URI = "http://localhost:"+(System.getenv("PORT")!=null?System.getenv("PORT"):"9998")+"/";

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer based on base URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static HttpServer startServer() throws UnknownHostException {
        ResourceConfig rc = new ResourceConfig().packages("org.glassfish.jersey.examples.linking").register(DeclarativeLinkingFeature.class).packages("org.rest.endpoints");
        rc.register(org.rest.converters.DateParamConverterProvider.class);
        rc.register(org.rest.exceptions.AppExceptionMapper.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        try {
            startServer();
            System.out.println(String.format("Homework app started at " + BASE_URI));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//public class Main {


//    protected static HttpServer startServer(URI uri) throws IOException {
//        System.out.println("Starting grizzly...");
//        ResourceConfig rc = new PackagesResourceConfig("com.agile.spirit.openapi");
//        return GrizzlyServerFactory.createHttpServer(uri, rc);
//    }
//
//    public static void main(String[] args) throws IOException {
//
//
//        URI uri = getBaseURI(hostname, Integer.valueOf(port));
//
//        HttpServer httpServer = startServer(uri);
//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", uri, uri));
//        if (isOnLocal) {
//            System.in.read();
//            httpServer.stop();
//        } else {
//            while (true) {
//                System.in.read();
//            }
//        }
//
//    }
//}