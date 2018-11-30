import java.io.*;
import java.net.*;

public class HttpBin {
    private String url;
    private HttpURLConnection connection;
    String urlParameters =
            "fName=" + URLEncoder.encode("???", "UTF-8") +
                    "&lName=" + URLEncoder.encode("???", "UTF-8");

    public HttpBin() throws IOException {
        url = "http://httpbin.org/" ;
    }

    /**
    * Execute Get request*/
    public void requestGet() throws IOException {
        createConnection();
        connection.setRequestMethod("GET");
        System.out.println(getResponse());
    }

    /**
     * Execute Stream request*/
    public void requeatStream() {
    }

    /**
     * Execute Basic-Auth request*/
    public void requeatBasicAuth() {
    }

    /**
     * Execute Post request*/
    public void requestPost() throws IOException {
        createConnection();
        connection.setRequestMethod("POST");
        sendRequest();
        System.out.println(getResponse());
    }

    private void createConnection() throws IOException {
        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length", "" +
                Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches (false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    private void sendRequest() throws IOException {
        DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream ());
        wr.writeBytes (urlParameters);
        wr.flush ();
        wr.close ();
    }

    private String getResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append('\r');
        }
        in.close();
        return response.toString();
    }

}
