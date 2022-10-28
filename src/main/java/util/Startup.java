package util;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import model.Item;

@ApplicationScoped
public class Startup {
    @Transactional
    public void start(@Observes StartupEvent evt){
        new Item(new BigDecimal(10), "Apple").persist();
        new Item(new BigDecimal(16), "Pear").persist();
        new Item(new BigDecimal(30), "Orange").persist();
    }
}
