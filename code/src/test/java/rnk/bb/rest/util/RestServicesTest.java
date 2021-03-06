package rnk.bb.rest.util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rnk.bb.domain.auth.Auth;
import rnk.bb.domain.book.RoomOrder;
import rnk.bb.domain.hotel.resource.*;
import rnk.bb.domain.hotel.resource.Guest;
import rnk.bb.domain.hotel.schedule.ScheduleItem;
import rnk.bb.domain.payment.PaymentStub;
import rnk.bb.util.json.JsonHelper;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


public class RestServicesTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://xjabber:18080/boombook/api/v1";
        RestAssured.port = 80;
    }

    @Test
    public void testCountries() {
        given()
                .pathParam("country",643)
                .when()
                .get("util/country/{country}")
                .then()
                .body("nameEn",equalTo("Russia"))
                .appendRoot("lsdkjflsdkjlsdkjflsd")
                .log().status()
                .statusCode(200)
                ;

        when()
                .request("GET", "util/countries")
                .then()
                .body("items.size()",greaterThan(200))
                .log().status()
                .statusCode(200)
                ;
    }

    @Test
    public void testAuthController() {
        Auth auth=new Auth();
        auth.setLogin("lalloo");
        auth.setPassword("1");
        auth.setPhone("123");
        auth.setEmail("a@b.ru");

        given()
                .pathParam("login",auth.getLogin())
                .delete("auth/{login}")
                .then()
                .statusCode(Matchers.anyOf(equalTo(200),equalTo(204)));

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(auth))
                .put("auth")
                .then()
                .body("login",equalTo(auth.getLogin()))
                .statusCode(200);

        auth.setPhone("567");

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(auth))
                .post("auth")
                .then()
                .body("phone",equalTo(auth.getPhone()))
                .statusCode(200);

        given()
                .pathParam("login",auth.getLogin())
                .get("auth/{login}")
                .then()
                .body("phone",equalTo(auth.getPhone()))
                .statusCode(200);

    }

    @Test
    public void testHotelController() throws ParseException {
        Hotel hotel=new Hotel();
        hotel.setName("Supper poopper hotel");
        hotel.setEmail("hotel@hotel.ru");
        hotel.setPhone("0000");
        hotel.setSite("https://www.sooper.ru");
        hotel.setPublished(true);

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(hotel))
                .put("hotel/resource/hotel")
                .then()
                .body("name",equalTo(hotel.getName()))
                .statusCode(200);

        hotel.setPhone("XXXX");

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(hotel))
                .post("hotel/resource/hotel");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("phone",equalTo(hotel.getPhone()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/hotel/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/hotel/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testFoodConceptController() throws ParseException {
        FoodConcept fc=new FoodConcept();
        fc.setName("All inclusive");
        fc.setBasePrice(40.);
        fc.setDescription("Here you can eat and drink everything you want");

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(fc))
                .put("hotel/resource/fc")
                .then()
                .body("name",equalTo(fc.getName()))
                .statusCode(200);

        fc.setBasePrice(50.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(fc))
                .post("hotel/resource/fc");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("basePrice",(equalTo(fc.getBasePrice().floatValue())))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/fc/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/fc/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testHotelPaymentPolicyController() throws ParseException {
        HotelPaymentPolicy pp=new HotelPaymentPolicy();
        pp.setPrePayPercent(5.);

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(pp))
                .put("hotel/resource/pp")
                .then()
                .body("prePayPercent",equalTo(pp.getPrePayPercent().floatValue()))
                .statusCode(200);

        pp.setPrePayPercent(100.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(pp))
                .post("hotel/resource/pp");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("prePayPercent",(equalTo(pp.getPrePayPercent().floatValue())))
                .statusCode(200);

        pp.setPrePayPercent(101.);

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(pp))
                .post("hotel/resource/pp")
                .then()
                .statusCode(500);

        given()
                .pathParam("id",id)
                .get("hotel/resource/pp/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/pp/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testRoomController() throws ParseException {
        Room room=new Room();
        room.setBasePrice(35.);
        room.setNumber("4004");

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(room))
                .put("hotel/resource/room")
                .then()
                .body("number",equalTo(room.getNumber()))
                .statusCode(200);

        room.setBasePrice(53.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(room))
                .post("hotel/resource/room");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("number",equalTo(room.getNumber()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/room/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/room/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testRoomFeatureController() throws ParseException {
        RoomFeature rf=new RoomFeature();
        rf.setName("Extra bed");
        rf.setDescription("You could purchase an extra bed for your child");
        rf.setPrice(10.);

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rf))
                .put("hotel/resource/rf")
                .then()
                .body("name",equalTo(rf.getName()))
                .statusCode(200);

        rf.setPrice(11.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rf))
                .post("hotel/resource/rf");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("price",(equalTo(rf.getPrice().floatValue())))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/rf/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/rf/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testRoomPoolController() throws ParseException {
        RoomPool rp=new RoomPool();
        rp.setName("Double rooms on the seaside");
        rp.setBasePrice(45.);

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rp))
                .put("hotel/resource/rp")
                .then()
                .body("name",equalTo(rp.getName()))
                .statusCode(200);

        rp.setBasePrice(71.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rp))
                .post("hotel/resource/rp");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("basePrice",(equalTo(rp.getBasePrice().floatValue())))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/rp/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/rp/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testRoomTypeController() throws ParseException {
        RoomType rt=new RoomType();
        rt.setName("Double rooms on the seaside");
        rt.setDescription("");

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rt))
                .put("hotel/resource/rt")
                .then()
                .body("name",equalTo(rt.getName()))
                .statusCode(200);

        rt.setName("very expensive rooms");

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(rt))
                .post("hotel/resource/rt");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("name",(equalTo(rt.getName())))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("hotel/resource/rt/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/resource/rt/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testScheduleController() throws ParseException {
        ScheduleItem schedule=new ScheduleItem();
        schedule.setStartPeriod((new SimpleDateFormat("yyyy-MM-dd")).parse("2018-11-05"));
        schedule.setEndPeriod((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-03-05"));

        Response response= given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(schedule))
                .put("hotel/schedule");

        String startPeriod=response.jsonPath().get("startPeriod");
        response
                .then()
                .statusCode(200);

        Assert.assertEquals(schedule.getStartPeriod(), (new SimpleDateFormat("yyyy-MM-dd")).parse(startPeriod));

        schedule.setEndPeriod((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-04-05"));

        response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(schedule))
                .post("hotel/schedule");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());
        String endPeriod=response.jsonPath().get("endPeriod");

        response
                .then()
                .statusCode(200);

        Assert.assertEquals(schedule.getEndPeriod(), (new SimpleDateFormat("yyyy-MM-dd")).parse(endPeriod));

        given()
                .pathParam("id",id)
                .get("hotel/schedule/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("hotel/schedule/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testGuestController() throws ParseException {
        Guest guest=new Guest();
        guest.setName("Doddo Zik");

        guest.setBirthDate((new SimpleDateFormat("yyyy-MM-dd")).parse("1998-12-05"));
        guest.setGender("Z");

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(guest))
                .put("book/guest")
                .then()
                .body("name",equalTo(guest.getName()))
                .statusCode(200);

        guest.setGender("X");

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(guest))
                .post("book/guest");
        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .body("gender",equalTo(guest.getGender()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("book/guest/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("book/guest/{id}")
                .then()
                .statusCode(200);

    }

    @Test
    public void testRoomOrderController() throws ParseException {
        RoomOrder ro=new RoomOrder();
        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(ro))
                .put("book/ro")
                .then()
                .statusCode(200);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(ro))
                .post("book/ro");

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("book/ro/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("book/ro/{id}")
                .then()
                .statusCode(200);

    }
    @Test
    public void testPaymentController() throws ParseException {
        PaymentStub p=new PaymentStub();
        p.setSum(400.);
        p.setTimestamp(Date.valueOf("2019-20-05"));

        given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(p))
                .put("pay")
                .then()
                .statusCode(200);

        p.setTimestamp((new SimpleDateFormat("yyyy-MM-dd")).parse("2019-04-05"));
        p.setSum(500.);

        Response response=given()
                .contentType(ContentType.JSON)
                .body(JsonHelper.marshal(p))
                .post("pay");

        String ts=response.jsonPath().get("timeStamp");
        response
                .then()
                .statusCode(200);

        Assert.assertEquals(p.getTimestamp(), (new SimpleDateFormat("yyyy-MM-dd")).parse(ts));

        Long id=Long.valueOf(((Integer)response.jsonPath().get("id")).longValue());

        response
                .then()
                .statusCode(200);

        given()
                .pathParam("id",id)
                .get("book/ro/{id}")
                .then()
                .body("id",equalTo(id.intValue()))
                .statusCode(200);

        given()
                .pathParam("id",id)
                .delete("book/ro/{id}")
                .then()
                .statusCode(200);

    }
}
