package rnk.bb.rest.app;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonJsonProviderAtRest extends JacksonJaxbJsonProvider {

    private static ObjectMapper objectMapperAtRest = new ObjectMapper();

    static {
        objectMapperAtRest.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapperAtRest.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapperAtRest.configure(SerializationFeature.INDENT_OUTPUT, true); // Different from default so you can test it :)
        objectMapperAtRest.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public JacksonJsonProviderAtRest() {
        super();
        setMapper(objectMapperAtRest);
    }
}