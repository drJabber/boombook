package rnk.bb.rest.util;

import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Country;
import rnk.bb.domain.util.Document;
import rnk.bb.domain.util.DocumentType;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.views.bean.order.EditAddressBean;
import rnk.bb.views.bean.order.EditDocumentBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class DocumentController extends CustomController<Document, Long> {

    @Inject
    DocumentTypeController docTypes;


    public Document createDocument(EditDocumentBean documentBean){
        Document document=new Document();

        document.setSerial(documentBean.getSerial());
        document.setNumber(documentBean.getNumber());
        document.setIssueDate(documentBean.getIssueDate());
        document.setExpirationDate(documentBean.getExpirationDate());

        DocumentType docType=docTypes.findByIdCached(documentBean.getDocumentTypeId());
        document.setDocumentType(docType);

        return document;
    }



    @PUT
    @Path("util/doc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("util/doc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("util/doc/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @DELETE
    @Path("util/doc/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long id) {
        return deleteInternal(id);
    }
}
