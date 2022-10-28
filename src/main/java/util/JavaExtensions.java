package util;

import java.math.BigDecimal;

import io.quarkus.qute.TemplateExtension;
import model.Item;

@TemplateExtension
public class JavaExtensions {
    static BigDecimal discountedPrice(Item item) {
        return item.price.multiply(new BigDecimal("0.9"));
    }
}
