import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.*;

import static io.restassured.RestAssured.*;

class HttpBin {

    private String baseUrl;
    private static final String GET = "get";
    private static final String POST = "post";
    private static final String STREAM = "stream/{number}";
    private static final String COLON = ":";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String BASIC_AUTH = "basic-auth/{validUsr}/{validPswd}";

    HttpBin(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Execute Get request
     */
    Response requestGet(Header header) {
        return given().header(header).get(baseUrl + GET);
    }

    Response requestPostWithBody(HashMap<String, String> dataHashMap) {
        JSONObject requestBody = new JSONObject(dataHashMap);
        return   given().body(requestBody.toString()).post(baseUrl +POST);
    }

    Response requestPostWithQueryParams(HashMap<String, String> dataHashMap) {
        return given().queryParams(dataHashMap).post(baseUrl +POST);
    }


    /**
     * Execute Stream request
     */
    Response requestStream(int number) {
        return given().get(baseUrl + STREAM, number);
    }

    /**
     * Execute Basic-Auth request
     */
    Response requestBasicAuth(String validUsr, String validPswd, String actualUsr, String actualPswd) {
        String authHeader = encodeBase64((actualUsr+ COLON +actualPswd));
        return given().header(new Header(AUTHORIZATION, BASIC +authHeader)).get(baseUrl + BASIC_AUTH,validUsr,validPswd);
    }

    private String encodeBase64(String strToEncode) {
        return new String(Base64.getEncoder().encode(strToEncode.getBytes()));
    }

}

