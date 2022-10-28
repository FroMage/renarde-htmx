package rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedFragment;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import model.Item;
import util.tags;

@Blocking
public class Items extends Controller {
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance items(List<Item> items);
    }

    @Path("/")
    public TemplateInstance items(){
        return Templates.items(Item.listAll());
    }

    public TemplateInstance itemPrice(@RestPath String name) {
        Item item = Item.findByName(name);
        return tags.price(item);
    }

    @PUT
    public void savePrice(@RestPath String name, @RestForm String price) {
        Item item = Item.findByName(name);
        item.price = new BigDecimal(price);
        itemPrice(name);
    }

    public TemplateInstance editPrice(@RestPath String name) {
        Item item = Item.findByName(name);
        return tags.priceEdit(item);
    }
}
