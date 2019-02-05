package rnk.bb.rest.util;

import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.DocumentType;
import rnk.bb.rest.blank.CustomController;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class DocumentTypeController extends CustomController<DocumentType, Long> {

    private List<DocumentType> docTypes;

    private void ensureCached(){
        if (docTypes==null){
            docTypes=findAll();
        }
    }

    public List<DocumentType> findAllCached(){
        ensureCached();
        return docTypes;
    }

    public DocumentType findByIdCached(Long docTypeId){
        ensureCached();
        return docTypes.stream().filter(c->c.getId().equals(docTypeId)).findAny().orElse(null);
    }

    @GET
    @Path("util/doctype/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @GET
    @Path("/util/doctypes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList() {
        return readAllInternal();
    }

}
