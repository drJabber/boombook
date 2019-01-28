package rnk.bb.rest.util;

import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.auth.Role;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Country;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.util.json.JsonHelper;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.util.List;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class CountryController {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;


    @GET
    @Path("util/country/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer id) {
        Country r=em.find(Country.class,id);
        if (r!=null){
            return Response.ok().entity(r).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @GET
    @Path("/util/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries() {

        List<Country> countries= findAll();

        if (countries!=null){
            return Response.ok().entity(countries).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    public List<Country> findAll() {

        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        CriteriaQuery<Country> q = cb.createQuery(Country.class);
        Root<Country> c = q.from(Country.class);

        return em.createQuery(q).getResultList();
    }


}
