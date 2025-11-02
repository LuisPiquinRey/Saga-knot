package com.luispiquinrey.product.Event;

import java.util.ArrayList;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import com.luispiquinrey.Enums.StatusProduct;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCreatedEvent {
    private String idProduct;

    private String name;

    private String brand;

    private float price;

    private Integer stock;

}
