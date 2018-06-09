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
    private static String hostname;
    private static int port;

    /**
     * URI where application started at.
     */
    private static URI getBaseURI(String hostname, int port) {
        URI uri = URI.create("http://" + hostname + ":" + port);
        System.out.println(uri);
        return uri;
    }

    /**
     * Creating new resources where JAX-RS can finds endpoints and returning GrizzlyServer based on base URI.
     *
     * @return GrizzlyServer from GrizzlyHttpServerFactory based on BASE_URI.
     */
    public static HttpServer startServer() throws UnknownHostException {
        ResourceConfig rc = new ResourceConfig().packages("org.glassfish.jersey.examples.linking").register(DeclarativeLinkingFeature.class).packages("org.rest.endpoints");
        rc.register(org.rest.converters.DateParamConverterProvider.class);
        rc.register(org.rest.exceptions.AppExceptionMapper.class);
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

//        hostname = System.getenv("HOSTNAME");
        hostname = "localhost";
        if (hostname == null)
            hostname = "localhost";

        String portS = System.getenv("PORT");
        if (portS == null)
            portS = "8080";

        port = Integer.valueOf(portS);
        return GrizzlyHttpServerFactory.createHttpServer(getBaseURI(hostname, port), rc);
    }

    public static void main(String[] args) {
        try {
            startServer();
            System.out.println(String.format("Homework app started at http://" + hostname + ":" + port));
            System.out.println("Example endpoint: http://" + hostname + ":" + port + "/courses");
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