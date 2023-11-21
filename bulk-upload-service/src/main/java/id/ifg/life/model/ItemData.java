package id.ifg.life.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemData {
    private String id;
    private String itemName;
    private String price;
    private String description;
}
