import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;

public class TestHttpBin {

    private static final int SUCCESS_CODE = 200;
    private static final String HEADERS = "headers.";
    private static final String JSON = "json.";
    private static final String ARGS = "args.";
    private static final char NEW_LINE = '\n';
    private static final String AUTHENTICATED = "authenticated";
    private static final String USER = "user";
    private static final int UNAUTHORIZED = 401;
    private static final String HTTPBIN_URL = "http://httpbin.org/";
    private HttpBin objHttpBin;

    @BeforeTest
    public void setUp() {
        objHttpBin = new HttpBin(HTTPBIN_URL);
    }

    @DataProvider(name = "HttpBin_getCustomHeader")
    public Object[][] provideHttpBinGetCustomHeader() {
        return new Object[][]{
                {new Header("X-Kek", "cheburek")},
                {new Header("X-Man", "Wolverine")}
        };
    }

    @Test(dataProvider = "HttpBin_getCustomHeader")
    public void testHttpBinRequestGet_getCustomHeader(Header header) {
        objHttpBin.requestGet(header)
                .then().statusCode(SUCCESS_CODE)
                .and().assertThat().body(HEADERS + header.getName(), equalTo(header.getValue()));

    }

    @DataProvider(name = "HttpBin_checkPostBody")
    public Object[][] provideHttpBinCheckPostBody() {
        return new Object[][]{
                {new HashMap<String, String>() {{
                    put("starost", "neradost");
                    put("molodost", "gadost");
                    put("marazm", "neskazhu");
                }}},
                {new HashMap<String, String>() {{
                    put("mama", "govorit");
                    put("chtoYa", "osobenniy");
                }}}
        };
    }

    @Test(dataProvider = "HttpBin_checkPostBody")
    public void testHttpBinRequestPostWithBody_checkPostBody(HashMap<String, String> data) {

        Response response = objHttpBin.requestPostWithBody(data);
        data.forEach((k, v) -> response.then()
                .assertThat().body(JSON + k, equalTo(v)));
    }

    @DataProvider(name = "HttpBin_checkPostWithQueryParams")
    public Object[][] provideHttpBinCheckPostWithQueryParams() {
        return new Object[][]{
                {new HashMap<String, String>() {{
                    put("starost", "neradost");
                    put("molodost", "gadost");
                    put("marazm", "neskazhu");
                }}},
                {new HashMap<String, String>() {{
                    put("mama", "govorit");
                    put("chtoYa", "osobenniy");
                }}}
        };
    }

    @Test(dataProvider = "HttpBin_checkPostWithQueryParams")
    public void testHttpBinRequestPostWithQueryParams_checkPostWithQueryParams(HashMap<String, String> queryParams) {

        Response response = objHttpBin.requestPostWithQueryParams(queryParams);
        response.then().statusCode(SUCCESS_CODE);
        queryParams.forEach((k, v) -> response.then()
                .assertThat().body(ARGS + k, equalTo(v)));
    }

    @DataProvider(name = "HttpBin_checkStreamLinesCount")
    public Object[][] provideHttpBinCheckStreamLinesCount() {
        return new Object[][]{
                {6},
                {100},
                {150},
                {0}
        };
    }

    @Test(dataProvider = "HttpBin_checkStreamLinesCount")
    public void testHttpBinRequestStream_checkStreamLinesCount(int number) {
        long counter = objHttpBin.requestStream(number).then().statusCode(SUCCESS_CODE)
                .and().extract().body().asString().chars().filter(sep -> sep == NEW_LINE).count();
        Assert.assertEquals(counter, number, "Wrong lines number");
    }

    @DataProvider(name = "HttpBin_checkValidAuth")
    public Object[][] provideHttpBinCheckValidAuth() {
        return new Object[][]{
                {"AllCops", "areBeautiful"},
                {"RestIn", "Pepperoni"}
        };
    }

    @Test(dataProvider = "HttpBin_checkValidAuth")
    public void testHttpBinRequestBasicAuth_checkValidAuth(String validUsr, String validPswd) {
        objHttpBin.requestBasicAuth(validUsr, validPswd, validUsr, validPswd)
                .then().statusCode(SUCCESS_CODE)
                .and().assertThat().body(AUTHENTICATED, equalTo(true), USER, equalTo(validUsr));
    }

    @DataProvider(name = "HttpBin_checkInvalidAuth")
    public Object[][] provideHttpBinCheckInvalidAuth() {
        return new Object[][]{
                {"AllCops", "areBeautiful", "Not", "SoPretty"},
                {"Make", "America", "Great", "Again"},
                {"MayTheForce", "BeWithYou", "MayTheForce", "BeWithDart"},
                {"Why", "MrAnderson", "ForWhat", "MrAnderson"}
        };
    }

    @Test(dataProvider = "HttpBin_checkInvalidAuth")
    public void basicAuthTestInvalidArgs(String validUsr, String validPswd, String actualUsr, String actualPswd) {
        objHttpBin.requestBasicAuth(validUsr, validPswd, actualUsr, actualPswd)
                .then().statusCode(UNAUTHORIZED);
    }
}
