package rnk.bb.rest.app;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.auth.Authenticator;
import rnk.bb.rest.hotel.resource.*;
import rnk.bb.rest.hotel.staff.StaffController;
import rnk.bb.rest.user.ClientController;
import rnk.bb.rest.util.AddressController;
import rnk.bb.rest.util.CountryController;

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
        register(StaffController.class);
        register(CountryController.class);
        register(HotelController.class);
        register(FoodConceptController.class);
        register(HotelPaymentPolicyController.class);
        register(RoomController.class);
        register(RoomFeatureController.class);
        register(RoomPoolController.class);
        register(RoomTypeController.class);

        register(AddressController.class);
    }

//    private void registerExceptionMapperClasses(){
//        register(RuntimeExceptionMapper.class);
//	register(UnrecognizedPropertyExceptionMapper.class);
//    }
}
