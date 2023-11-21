package id.ifg.life.repository;

import id.ifg.life.entity.Item;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collections;
import java.util.Optional;

@ApplicationScoped
public class ItemRepository implements PanacheRepository<Item> {

    public Item findByName(String name) {
        return find("name", name).firstResult();
    }

    public Item findById(String id) {
        return find("id", id).firstResult();
    }

}
