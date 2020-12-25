import com.jayway.restassured.RestAssured;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static com.jayway.restassured.config.EncoderConfig.encoderConfig;


public class Tests {
    private final String token = "T_HHsYQ_bv4AAAAAAAAAAU2mw48OB2EfjFEEm-vlkcdUi1nGt3ygT9AvP-GNKSGT";


    @Test
    public void test1upload() {
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


    @Test
    public void test2metadata() {
        String filename = "FeelsDankMan.png";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", "/test/" + filename);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(jsonObject.toString())
                .post("https://api.dropboxapi.com/2/files/get_metadata")
                .then().statusCode(200);
    }


    @Test
    public void test3delete() {
        String filename = "FeelsDankMan.png";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", "/test/" + filename);

        RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(jsonObject.toString())
                .post("https://api.dropboxapi.com/2/files/delete_v2")
                .then().statusCode(200);
    }
}
