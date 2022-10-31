package rest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestSseElementType;

import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import model.Message;
import model.Room;
import util.tags;

@Blocking
public class Rooms extends Controller {
    
    // This doesn't look like the right way to do it, does it?
    Map<Room, Multi<TemplateInstance>> roomMessages = new ConcurrentHashMap<>();
    
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance rooms(List<Room> rooms);
        public static native TemplateInstance room(Room room);
    }

    @Path("/rooms")
    public TemplateInstance rooms(){
        return Templates.rooms(Room.listAll());
    }

    public TemplateInstance room(@RestPath long roomId){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        return Templates.room(room);
    }

//    @Produces(MediaType.SERVER_SENT_EVENTS)
//    public Multi<TemplateInstance> messagesSse(@RestPath long roomId) {
//        Room room = Room.findById(roomId);
//        notFoundIfNull(room);
//        Room.listen(event -> {
//            
//        });
//        Multi.createFrom().emitter(emitter -> {
//            roomMessages.computeIfAbsent(room, emitter);
//            for(Message msg : room.messages) {
//                emitter.emit(tags.message(msg));
//            }
//        });
//    }
    
    @POST
    public TemplateInstance addMessage(@RestPath long roomId, @RestForm String text){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        Message msg = new Message();
        msg.date = new Date();
        msg.text = text;
        msg.room = room;
        msg.persist();
        return tags.message(msg);
    }

    @POST
    public void add(@RestForm String name){
        Room room = new Room();
        room.name = name;
        room.persist();
        room(room.id);
    }

    @POST
    public TemplateInstance setName(@RestPath long roomId, @RestForm String name){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        room.name = name;
        return tags.roomName(room);
    }

    public TemplateInstance edit(@RestPath long roomId){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        return tags.roomEdit(room);
    }
}
