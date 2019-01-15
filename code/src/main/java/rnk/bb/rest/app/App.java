package rnk.bb.rest.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import rnk.bb.auth.rest.AuthController;
import rnk.bb.auth.rest.Authenticator;
import rnk.bb.user.rest.ClientController;

import javax.ws.rs.ApplicationPath;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("api")
public class App extends ResourceConfig {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final String ENCODING_PROPERTY = "encoding";

    public App(){

        register(JacksonFeature.class);
        registerResourceClasses();

        logger.setLevel(Level.ALL);
        register(new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY,65536));

        property(ENCODING_PROPERTY, StandardCharsets.UTF_8.toString());
    }


    private void registerResourceClasses(){
        register(Authenticator.class);
        register(AuthController.class);
        register(ClientController.class);
    }

//    private void registerExceptionMapperClasses(){
//        register(RuntimeExceptionMapper.class);
//	register(UnrecognizedPropertyExceptionMapper.class);
//    }
}
