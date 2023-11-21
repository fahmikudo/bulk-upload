package id.ifg.life.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ifg.life.handler.GeneralErrorException;
import id.ifg.life.model.BulkUploadTaskResponse;
import id.ifg.life.model.ItemData;
import id.ifg.life.service.BulkUploadService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import io.smallrye.reactive.messaging.kafka.Record;


@ApplicationScoped
public class BulkUploadServiceImpl implements BulkUploadService {

    @ConfigProperty(name = "upload.directory")
    String UPLOAD_DIR;

    @Inject
    @Channel("data-output")
    Emitter<Record<String, String>> emitter;


    @Override
    public BulkUploadTaskResponse uploadFiles(MultipartFormDataInput input) {

        if (input == null) {
            throw new GeneralErrorException("File cannot be null.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();

                String fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                Path path = writeFile(inputStream, fileName);

                Log.info("Path : " + path.toAbsolutePath());

                String filePath = path.toFile().getAbsolutePath();
                Charset charset = StandardCharsets.UTF_8;

                CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                        .withAllowMissingColumnNames(true);

                Map<String, List<ItemData>> mapItem = new HashMap<>();
                try {
                    Reader reader = Files.newBufferedReader(Paths.get(filePath), charset);
                    CSVParser csvParser = new CSVParser(reader, csvFormat);

                    List<ItemData> itemDataList = new ArrayList<>();
                    for (CSVRecord csvRecord : csvParser) {
                        ItemData itemData = new ItemData();
                        itemData.setId(csvRecord.get(0));
                        itemData.setItemName(csvRecord.get(1));
                        itemData.setPrice(csvRecord.get(2));
                        itemData.setDescription(csvRecord.get(3));
                        itemDataList.add(itemData);
                    }

                    String bulkTaskId = UUID.randomUUID().toString();

                    mapItem.put(bulkTaskId, itemDataList);

                    Log.info("Map Item : " + objectMapper.writeValueAsString(mapItem));

                    // push to kafka
                    emitter.send(Record.of(bulkTaskId, objectMapper.writeValueAsString(itemDataList)));

                    return new BulkUploadTaskResponse(bulkTaskId, filePath);
                } catch (FileNotFoundException e) {
                    throw new GeneralErrorException(e);
                }
            } catch (Exception e) {
                throw new GeneralErrorException(e);
            }
        }
        return new BulkUploadTaskResponse();
    }

    private Path writeFile(InputStream inputStream, String fileName)
            throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        File customDir = new File(UPLOAD_DIR);

        fileName = customDir.getAbsolutePath() +
                File.separator + System.currentTimeMillis() + "-" + fileName;
        return Files.write(Paths.get(fileName), bytes,
                StandardOpenOption.CREATE_NEW);
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.
                getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replace("\"", "");
            }
        }
        return "";
    }

}
