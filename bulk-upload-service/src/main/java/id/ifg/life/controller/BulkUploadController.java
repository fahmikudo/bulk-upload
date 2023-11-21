package id.ifg.life.controller;

import id.ifg.life.model.BulkUploadTaskResponse;
import id.ifg.life.service.BulkUploadService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;


@Path("/api/bulk-uploads")
@ApplicationScoped
public class BulkUploadController {

    @Inject
    BulkUploadService bulkUploadService;

    @GET
    @Path("/health-check")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String healthCheck() {
        return "Producer Bulk Upload Service UP!";
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response fileUpload(@MultipartForm MultipartFormDataInput input) {
        BulkUploadTaskResponse bulkUploadTaskResponse = bulkUploadService.uploadFiles(input);
        return Response.ok().entity(bulkUploadTaskResponse).build();
    }

}
