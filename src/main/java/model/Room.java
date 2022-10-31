package model;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Room extends PanacheEntity {
    @OneToMany(mappedBy = "room")
    public List<Message> messages;
    public String name;
    
    public Runnable listen(Consumer<Message> consumer) {
        return Message.listen(msg -> {
           if(msg.room.id == this.id) {
               consumer.accept(msg);
           }
        });
    }
}
