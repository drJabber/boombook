package rnk.bb.rest.blank;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import rnk.bb.domain.blank.AbstractEntity;

import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class CustomController<T extends AbstractEntity, ID> extends AbstractController<T,ID>{
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @Override
    protected EntityManager entityManager(){
        return em;
    }

    protected Response saveInternal(JsonObject info) {
        try{
            return Response.ok().entity(marshal(save(unmarshal(info)))).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    protected Response readInternal(ID id){
        T entity=findById(id);
        if (entity!=null){
            return Response.ok().entity(marshal(entity)).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    protected Response deleteInternal(ID id){
        try{
            deleteById(id);
            return Response.ok().build();
        }catch(Exception ex){
            return Response.serverError().entity("cant find entity").build();
        }
    }

    protected Response readAllInternal(){
        try{
            return Response.ok().entity(marshal(findAll())).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

}
