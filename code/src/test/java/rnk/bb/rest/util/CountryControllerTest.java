package rnk.bb.rest.util;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


public class CountryControllerTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://xjabber:18080/boombook/api/v1";
        RestAssured.port = 80;
    }

    @Test
    public void testCountryRussia() {
        given().pathParam("country",643).when().get("util/country/{country}").then().body("nameEn",equalTo("Russia")).statusCode(200);
    }

    @Test
    public void testCountries() {
        when()
                .request("GET", "util/countries")
                .then()
                .body("items.size()",greaterThan(200))
                .statusCode(200);
    }

}
