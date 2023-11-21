package id.ifg.life;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title="Bulk Upload Task API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Example API Support",
                        url = "http://exampleurl.com/contact",
                        email = "techsupport@example.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class BulkUploadServiceApplication extends Application {


    protected BulkUploadServiceApplication(boolean auxiliaryApplication) {
        super(auxiliaryApplication);
    }

    @Override
    protected void doStart(String[] args) {
        Log.info("Application Started...");
    }

    @Override
    protected void doStop() {
        Log.info("Application Stopped...");
    }

    @Override
    public String getName() {
        return "Bulk Upload Service";
    }
}
