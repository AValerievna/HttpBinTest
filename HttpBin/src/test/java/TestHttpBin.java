import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.StringContains.containsString;

public class TestHttpBin {
    HttpBin objHttpBin;
    Response response;

    @BeforeTest
    public void setUp(){
        objHttpBin = new HttpBin();
        response = null;
    }
    @Test
    public void getTest() {
        HttpBin objHttpBin = new HttpBin();

       Header head =new Header("X-kek","cheburek");
/*      response = objHttpBin.requestGet(head);
        Assert.assertEquals(200, response.getStatusCode(),"Incorrect code");
        Assert.assertEquals(response.getBody().toString()., head, "Incorrect header");
        System.out.println(response.getBody().asString());
        expect().statusCode(200).body();
        response = expect().statusCode(200).body();*/

        objHttpBin.requestGet(head).then().statusCode(200).and().assertThat().body(containsString(head.toString()));
    }

    @Test
    public void postTest() {
        HttpBin objHttpBin = new HttpBin();
        Response response = objHttpBin.requestPost();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        String successCode = response.jsonPath().get("SuccessCode");
       // Assert.assertEquals(successCode, "OPERATION_SUCCESS");
        System.out.println(response.getBody().asString());
    }
}
