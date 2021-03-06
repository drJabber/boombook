package rnk.bb.util.json;

import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.JAXBException;

public class JsonHelper {

    public static <T> T unmarshal(JsonObject jo, Class<T> clazz) throws JAXBException {
        return JsonbBuilder.create().fromJson(jo.toString(),clazz);
    }

    public static String marshal(Object object){
        return JsonbBuilder.create().toJson(object);
    }
}
