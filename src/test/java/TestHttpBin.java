import org.testng.annotations.Test;

import java.io.IOException;

public class TestHttpBin {

    @Test
    public void getTest() throws IOException {
        HttpBin objHttpBin = new HttpBin();
        System.out.print(objHttpBin.requestGet());
    }
}
