package rnk.bb.rest.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("stringUtils")
@ApplicationScoped
public class StringUtils implements Serializable {
    public Boolean isNotBlank(String value){
        return (value !=null)&&(!value.trim().isEmpty());
    }
}
