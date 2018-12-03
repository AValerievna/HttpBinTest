import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

import static io.restassured.RestAssured.*;

public class HttpBin {

    /**
     * Execute Get request
     */
    public Response requestGet(Header header) {
        return given().header(header).get("http://httpbin.org/get");
    }

    public Response requestPost(HashMap<String,String> dataHashMap) {

        JSONObject requestBody = new JSONObject();

        for(Map.Entry<String,String> entry : dataHashMap.entrySet()) {
            requestBody.put(entry.getKey(),entry.getValue());
        }

/*        requestBody.put("access-control-allow-credentials", "true");
        requestBody.put("access-control-allow-origin:", "http://httpbin.org");
        requestBody.put("connection:  ", "keep-alive");
        requestBody.put("Password", "some");
        requestBody.put("Email", "some" + "@gmail.com");*/
        //RequestSpecification request = RestAssured.given();
        //request.header("Accept", "application/json");
      ;
        //request.body(dataHashMap);


        return   given().body(requestBody.toString()).post("http://httpbin.org/post");
    }

    public Response requestPostWithQueryParams(HashMap<String,String> dataHashMap) {
        return given().queryParams(dataHashMap).post("http://httpbin.org/post");
    }


    /**
     * Execute Stream request
     */
    public void requeatStream() {
    }

    /**
     * Execute Basic-Auth request
     */
    public void requeatBasicAuth() {
    }
}

