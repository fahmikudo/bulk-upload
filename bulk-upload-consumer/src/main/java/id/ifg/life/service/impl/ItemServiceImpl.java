package id.ifg.life.service.impl;

import id.ifg.life.entity.Item;
import id.ifg.life.handler.GeneralErrorException;
import id.ifg.life.model.ItemData;
import id.ifg.life.repository.ItemRepository;
import id.ifg.life.service.ItemService;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@Singleton
public class ItemServiceImpl implements ItemService {

    @Inject
    ItemRepository itemRepository;

    @Override
    @Transactional
    public void createItem(ItemData item) {
        Item byId = itemRepository.findById(item.getId());
        if (byId != null)
            throw new GeneralErrorException("Item Id is already exists.");

        Item newItem = Item.builder()
                .id(item.getId())
                .name(item.getItemName())
                .price(BigDecimal.valueOf(Long.parseLong(item.getPrice())))
                .description(item.getDescription())
                .build();
        itemRepository.persist(newItem);
    }
}
