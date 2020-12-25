import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.testng.annotations.Test;

import java.io.File;

import static com.jayway.restassured.config.EncoderConfig.encoderConfig;

public class UploadTest {
    private final String token = "T_HHsYQ_bv4AAAAAAAAAAU2mw48OB2EfjFEEm-vlkcdUi1nGt3ygT9AvP-GNKSGT";

    @Test
    @Order(1)
    public void uploadTest() {
        String filename = "FeelsDankMan.png";
        File file = new File("src/main/resources/"+filename);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", "/test/"+filename);

        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Dropbox-API-Arg", jsonObject.toString())
                .header("Content-Type","application/octet-stream")
                .contentType("application/octet-stream")
                .body(file)
                .post("https://content.dropboxapi.com/2/files/upload")
                .then().statusCode(200);
    }

}
