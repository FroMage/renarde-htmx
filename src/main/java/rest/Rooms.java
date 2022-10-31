package rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedFragment;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import model.Message;
import model.Room;
import util.tags;

@Blocking
public class Rooms extends Controller {
    
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance rooms(List<Room> rooms);
        public static native TemplateInstance room(Room room);
        @CheckedFragment
        public static native TemplateInstance room$textfield();
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

    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> messagesSse(@RestPath long roomId) {
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        return Multi.createFrom().emitter(emitter -> {
            // only send new messages for this room
            Runnable removeListener = room.listen(message -> {
                emitter.emit(tags.message(message).render());
            });
            emitter.onTermination(() -> {
                removeListener.run();
            });
        });
    }
    
    @POST
    public TemplateInstance addMessage(@RestPath long roomId, @RestForm String text){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        Message msg = new Message();
        msg.date = new Date();
        msg.text = text;
        msg.room = room;
        msg.persist();
        return Templates.room$textfield();
    }

    @POST
    public String clear(@RestPath long roomId){
        Room room = Room.findById(roomId);
        notFoundIfNull(room);
        Message.delete("room", room);
        // TODO: find better to clear the #messages div
        return "";
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
