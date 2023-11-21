package id.ifg.life.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BulkUploadTaskResponse {

    private String id;
    private String filePath;

}
