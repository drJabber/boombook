package rnk.bb.rest.user;


import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.user.Client;
import rnk.bb.domain.util.Address;
import rnk.bb.domain.util.Document;
import rnk.bb.rest.auth.AuthController;
import rnk.bb.rest.blank.CustomController;
import rnk.bb.rest.util.AddressController;
import rnk.bb.rest.util.DocumentController;
import rnk.bb.views.bean.registration.RegUserBean;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Startup
@DependsOn({"StartupController"})
@Path("v1")
public class ClientController  extends CustomController<Client, Long> {

    @Inject
    AddressController addresses;

    @Inject
    DocumentController documents;

    @Inject
    AuthController auths;

    private Client createClient(RegUserBean clientBean){
        Client client=new Client();

        client.setName(clientBean.getName());
        client.setGender(clientBean.getGender());
        client.setBirthDate(clientBean.getBirthDate());

        Auth auth=auths.createUser(clientBean);
        Address address=addresses.createAddress(clientBean.getAddress());
        Document document=documents.createDocument(clientBean.getDocument());

        client.setLogin(auth);
        client.setAddress(address);
        client.setDocument(document);

        return client;
    }

    public void registerClient(RegUserBean clientBean){
        EntityManager em=entityManager();

        Client client=createClient(clientBean);
        em.merge(client);
    }

    @PUT
    @Path("client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JsonObject info) {
        return saveInternal(info);
    }

    @POST
    @Path("client")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject info) {
        return saveInternal(info);
    }

    @GET
    @Path("client/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Long id) {
        return readInternal(id);
    }

    @DELETE
    @Path("client/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response delete(@PathParam("id") Long id) {
        return readInternal(id);
    }
}
