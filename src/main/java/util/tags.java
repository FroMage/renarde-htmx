package util;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import model.Item;
import model.Message;
import model.Room;

@CheckedTemplate(basePath = "tags")
public class tags {
    public static native TemplateInstance price(Item item);
    public static native TemplateInstance priceEdit(Item item);
    public static native TemplateInstance roomName(Room room);
    public static native TemplateInstance roomEdit(Room room);
    public static native TemplateInstance message(Message message);
    public static native TemplateInstance messages(Room room);
}
