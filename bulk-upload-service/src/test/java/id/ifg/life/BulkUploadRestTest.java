package id.ifg.life;

import id.ifg.life.model.BulkUploadTaskResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.*;

@QuarkusTest
public class BulkUploadRestTest {

    @Test
    @SneakyThrows
    void givenValidFile() {
        File targetFile = new File("src/main/resources/template-item.csv");
        System.out.println("File : " + targetFile.getAbsoluteFile());
        BulkUploadTaskResponse mockResponse = new BulkUploadTaskResponse(UUID.randomUUID().toString(), targetFile.getAbsolutePath());
        given()
                .multiPart("file", targetFile)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .post("/api/bulk-uploads/upload")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

}
