package rnk.bb.rest.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("serverUtils")
@ApplicationScoped
public class ServerUtils implements Serializable {
    public Integer computeFirstResult(Integer firstResult, Integer pageSize, Integer totalResult){
        return (firstResult+pageSize)>=totalResult?(totalResult-pageSize)<0?0:totalResult-pageSize:firstResult;
    }
}
