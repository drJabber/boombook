package rnk.bb.rest.util;

import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.DocumentType;
import rnk.bb.helper.json.JsonHelper;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class Document {
    @PersistenceContext(unitName="RNK_PU")
    private EntityManager em;

    @PUT
    @Path("util/doc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        try{
            Document document= JsonHelper.unmarshal(info, Document.class);
            em.persist(document);
            return Response.ok().entity(document).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @POST
    @Path("util/adr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        try{
            Document document= JsonHelper.unmarshal(info, Document.class);
            em.merge(document);
            return Response.ok().entity(document).build();
        }catch(Exception ex){
            return Response.serverError().entity("cant parse query parameters").build();
        }
    }

    @GET
    @Path("util/adr/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readItem(@PathParam("id") Integer documentId) {
        Document document=em.find(Document.class,documentId);
        if (document!=null){
            return Response.ok().entity(document).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @GET
    @Path("/util/doctype/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentType(@PathParam("id") Integer docTypeId) {
        DocumentType dt=em.find(DocumentType.class,docTypeId);
        if (dt!=null){
            return Response.ok().entity(dt).build();
        }else{
            return Response.serverError().entity("cant find entity").build();
        }
    }

    @DELETE
    @Path("util/adr/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Integer documentId) {
        Document document=em.find(Document.class,documentId);
        if (document!=null){
            em.remove(document);
            return Response.ok().build();
        }else{
            return Response.serverError().entity("cand find entity").build();
        }
    }
}
