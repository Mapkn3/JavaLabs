import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Launcher {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        Connector connector = new SelectChannelConnector();
        connector.setPort(8080);
        server.addConnector(connector);

        WebAppContext context = new WebAppContext("D:/zzz/help/Fourth/web", "/");

        server.setHandler(context);
        server.start();
        server.join();
    }
}