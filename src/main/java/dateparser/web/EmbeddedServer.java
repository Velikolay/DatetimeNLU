package dateparser.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URL;
import java.security.ProtectionDomain;

public final class EmbeddedServer {

    private static final int SERVER_PORT = 8080;

    private EmbeddedServer() {
    }

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://localhost").port(SERVER_PORT)
                .build();
        ResourceConfig config = new ResourceConfig(TokenExtractor.class);
        Server server = JettyHttpContainerFactory.createServer(baseUri, config,
                false);

        ContextHandler contextHandler = new ContextHandler("/rest");
        contextHandler.setHandler(server.getHandler());

        ProtectionDomain protectionDomain = EmbeddedServer.class
                .getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setResourceBase(location.toExternalForm());
        System.out.println(location.toExternalForm());
        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] { resourceHandler,
                contextHandler, new DefaultHandler() });
        server.setHandler(handlerCollection);
        server.start();
        server.join();
    }
}
