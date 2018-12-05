import io.restassured.http.Header;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

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
     * <p>
     * Send request to {@link #baseUrl} {@code /get}
     *
     * @param header - header to be sent
     * @return response from Get request, type Response
     */
    public Response requestGet(Header header) {
        return given().header(header).get(baseUrl + GET);
    }

    /**
     * Execute Post request
     * <p>
     * Send request to {@link #baseUrl} {@code /post}
     *
     * @param dataHashMap - data map to be sent in body
     * @return response from Post request, type Response
     */
    public Response requestPostWithBody(HashMap<String, String> dataHashMap) {
        JSONObject requestBody = new JSONObject(dataHashMap);
        return given().body(requestBody.toString()).post(baseUrl + POST);
    }

    /**
     * Execute Post request
     * <p>
     * Send request to {@link #baseUrl} {@code /post}
     *
     * @param queryParams - data map to be sent in query params
     * @return response from Post request, type Response
     */
    public Response requestPostWithQueryParams(HashMap<String, String> queryParams) {
        return given().queryParams(queryParams).post(baseUrl + POST);
    }


    /**
     * Execute Stream request
     * <p>
     * Send request to {@link #baseUrl} {@code /stream/:number}
     *
     * @param number - number of lines to generate
     * @return response from Stream request, type Response
     */
    public Response requestStream(int number) {
        return given().get(baseUrl + STREAM, number);
    }

    /**
     * Execute Basic-Auth request
     * <p>
     * Send request to {@link #baseUrl} {@code /basic-auth/:user/:passwd}
     *
     * @param validUsr   - expected user name
     * @param validPswd  - expected user password
     * @param actualUsr  - actual user name
     * @param actualPswd - actual password
     * @return response from Basic-Auth request, type Response
     */
    public Response requestBasicAuth(String validUsr, String validPswd, String actualUsr, String actualPswd) {
        String authHeader = encodeBase64((actualUsr + COLON + actualPswd));
        return given().header(new Header(AUTHORIZATION, BASIC + authHeader)).get(baseUrl + BASIC_AUTH, validUsr, validPswd);
    }

    /**
     * Encoding given string to Base64
     *
     * @param strToEncode - string, which has to be encoded
     * @return encoded string, type String
     */
    private String encodeBase64(String strToEncode) {
        return new String(Base64.getEncoder().encode(strToEncode.getBytes()));
    }

}

