package id.ifg.life;

import id.ifg.life.model.BulkUploadTaskResponse;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

import static io.restassured.RestAssured.*;

@QuarkusTest
public class UserSecurityTest {

    @Test
    void shouldAccessPublicWhenHealthCheck() {
        get("/api/bulk-uploads/health-check")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void shouldNotAccessAdminWhenBulkUpload() {
        File targetFile = new File("src/main/resources/template-item.csv");
        given()
                .multiPart("file", targetFile)
                .post("/api/bulk-uploads/upload")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    void shouldNotAccessAdminWhenAdminInvalid() {
        File targetFile = new File("src/main/resources/template-item.csv");
        System.out.println("File : " + targetFile.getAbsoluteFile());
        BulkUploadTaskResponse mockResponse = new BulkUploadTaskResponse(UUID.randomUUID().toString(), targetFile.getAbsolutePath());
        given()
                .multiPart("file", targetFile)
                .auth().preemptive().basic("admin", "user")
                .when()
                .post("/api/bulk-uploads/upload")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    void shouldAccessAdminWhenAdminAuthenticated() {
        File targetFile = new File("src/main/resources/template-item.csv");
        System.out.println("File : " + targetFile.getAbsoluteFile());
        BulkUploadTaskResponse mockResponse = new BulkUploadTaskResponse(UUID.randomUUID().toString(), targetFile.getAbsolutePath());
        given()
                .multiPart("file", targetFile)
                .auth().preemptive().basic("admin", "admin")
                .when()
                .post("/api/bulk-uploads/upload")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
