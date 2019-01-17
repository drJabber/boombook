package rnk.bb.domain.auth;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import rnk.bb.rest.auth.AuthController;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AuthTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Auth.class)
                .addClass(Role.class)
                .addClass(AuthController.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @Inject
    AuthController authController;

    @Test
    public void testAddAuth() {
        Auth auth=new Auth();
        auth.setLogin("lalloo");
        auth.setPassword("qwerty");



        Role r=new Role();
        r.setRole("Client");
        Set<Role> roles=new HashSet<>();
        roles.add(r);
        auth.setRoles(roles);
        auth.setEmail("a@b.ru");
        auth.setPhone("111-111");

        Set<Auth> accounts=new HashSet<>();
        accounts.add(auth);

        JsonReader jr= Json.createReader(new StringReader(JsonbBuilder.create().toJson(auth)));
        Response rs= authController.create(jr.readObject());
        assertEquals(200,rs.getStatus());

        rs=authController.read("lalloo");
        assertEquals(200, rs.getStatus());

        auth=(Auth)rs.getEntity();
        assertEquals("lalloo",auth.getLogin());

    }
}
