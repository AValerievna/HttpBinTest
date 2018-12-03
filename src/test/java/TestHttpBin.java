import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

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

       Header head =new Header("X-Kek","cheburek");
      //response = objHttpBin.requestGet(head);
 /*       Assert.assertEquals(200, response.getStatusCode(),"Incorrect code");
        Assert.assertEquals(response.getBody().toString(), head, "Incorrect header");
        System.out.println(response.getBody().asString());*/
//        expect().statusCode(200).body();
//        response = expect().statusCode(200).body();

        //objHttpBin.requestGet(head).then().statusCode(200).and().assertThat().body(containsString(head.getName()+"\": \""+head.getValue()));
        //System.out.println(response.getBody().asString());
        objHttpBin.requestGet(head)
                .then().statusCode(200)
                .and().assertThat().body("headers." + head.getName(), equalTo(head.getValue()));

    }

    @Test
    public void postTestBody() {
        HttpBin objHttpBin = new HttpBin();
        HashMap<String,String> hm = new HashMap<>();
        hm.put("starost", "neradost");
        hm.put("molodost", "gadost");
        hm.put("marazm","neskazhu");


        Response response = objHttpBin.requestPost(hm);

        System.out.println(response.getBody().asString());
        //objHttpBin.requestPostWithQueryParams(hm).then().statusCode(200);//.and().assertThat().body("headers."+head.getName(), equalTo(head.getValue()));
        hm.forEach((k, v) -> response.then()
                .assertThat().body("json." + k, equalTo(v)));
    }

    @Test
    public void postTestQueryParams() {
        HttpBin objHttpBin = new HttpBin();
        HashMap<String,String> hm = new HashMap<>();
        hm.put("starost", "neradost");
        hm.put("molodost", "gadost");
        hm.put("marazm","neskazhu");


        Response response = objHttpBin.requestPostWithQueryParams(hm);

        System.out.println(response.getBody().asString());
        response.then().statusCode(200);
        hm.forEach((k, v) -> response.then()
                .assertThat().body("args." + k, equalTo(v)));

        //objHttpBin.requestPostWithQueryParams(hm).then().statusCode(200);//.and().assertThat().body("headers."+head.getName(), equalTo(head.getValue()));
    }

}
