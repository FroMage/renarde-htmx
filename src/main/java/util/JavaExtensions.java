package util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.quarkus.qute.TemplateExtension;
import model.Item;

@TemplateExtension
public class JavaExtensions {
    static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    static BigDecimal discountedPrice(Item item) {
        return item.price.multiply(new BigDecimal("0.9"));
    }
    static String niceFormat(Date date) {
        return DATE_FORMATTER.format(date);
    }
}
