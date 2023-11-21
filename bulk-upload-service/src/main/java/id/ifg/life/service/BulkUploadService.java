package id.ifg.life.service;

import id.ifg.life.model.BulkUploadTaskResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public interface BulkUploadService {

    BulkUploadTaskResponse uploadFiles(MultipartFormDataInput multipartFormDataInput);

}
