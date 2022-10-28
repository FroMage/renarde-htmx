package util;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import model.Item;

@CheckedTemplate(basePath = "tags")
public class tags {
    public static native TemplateInstance price(Item item);
    public static native TemplateInstance priceEdit(Item item);
}
