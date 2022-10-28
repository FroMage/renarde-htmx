package model;

import java.math.BigDecimal;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Item extends PanacheEntity {
    public BigDecimal price;
    public String name;

    public Item(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }

    public Item(){}

    public static Item findByName(String name) {
        return find("name", name).singleResult();
    }
}
