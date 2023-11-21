package id.ifg.life.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ifg.life.entity.Item;
import id.ifg.life.model.ItemData;
import id.ifg.life.service.ItemService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.reactive.messaging.kafka.Record;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ItemConsumer {

    @Inject
    ItemService itemService;

    @Incoming("data-input")
    @Blocking
    public void receive(Record<String, String> record) throws JsonProcessingException {
        Log.info("Incoming Message Item...");
        Log.info("Item Key : " + record.key());
        Log.info("Item Value : " + record.value());

        ObjectMapper objectMapper = new ObjectMapper();

        List<ItemData> items = objectMapper.readValue(record.value(), new TypeReference<List<ItemData>>(){});

        for (ItemData item : items) {
            itemService.createItem(item);
        }
        Log.info("Finished Process Message Item...");

    }

}


