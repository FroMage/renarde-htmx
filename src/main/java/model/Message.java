package model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Message extends PanacheEntity {
    @ManyToOne
    public Room room;
    
    public String text;
    public Date date;
    
    private static List<Consumer<Message>> listeners = new CopyOnWriteArrayList<>();
    
    @PrePersist
    void prePersist() {
        for (Consumer<Message> listener : listeners) {
            listener.accept(this);
        }
    }
    
    public static Runnable listen(Consumer<Message> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }
}
