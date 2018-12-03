import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

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

       Header head =new Header("kek","cheburek");
       Response response = objHttpBin.requestGet(head);
        Assert.assertEquals(200, response.getStatusCode(),"Incorrect code");
        Assert.assertEquals(response.getHeader("kek"), head, "Incorrect header");
        System.out.println(response.getBody().asString());
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
