package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Message extends PanacheEntity {
    @ManyToOne
    public Room room;
    
    public String text;
    public Date date;
}
