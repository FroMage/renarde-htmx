package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Room extends PanacheEntity {
    @OneToMany(mappedBy = "room")
    public List<Message> messages;
    public String name;
//    public static void listen(Consumer<T> consumer) {
//        SessionFactoryImplementor sessionFactoryImplementor = getEntityManager().getEntityManagerFactory().unwrap(SessionFactoryImplementor.class);
//        EventListenerRegistry eventListenerRegistry = sessionFactoryImplementor.getServiceRegistry().getService(EventListenerRegistry.class);
//        eventListenerRegistry.prependListeners(EventType.PERSIST, new PersistEventListener() {
//
//            @Override
//            public void onPersist(PersistEvent event) throws HibernateException {
//                System.err.println("onPersist 1: "+event.getObject());
//            }
//
//            @Override
//            public void onPersist(PersistEvent event, Map createdAlready) throws HibernateException {
//                System.err.println("onPersist 2: "+event.getObject());
//            }
//        });
//    }
}
