package com.grandmasfood.v1.service.menu;

import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.entity.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PlainTextMenuGenerator implements MenuGenerator{
    @Override
    public String supportedMediaType() {
        return MediaType.TEXT_PLAIN_VALUE;
    }

    @Override
    public byte[] generate(List<Category> categories) {
        StringBuilder stringBuilder = new StringBuilder();

        buildHeader(stringBuilder);
        buildBody(stringBuilder, categories);

        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void buildHeader(StringBuilder sb) {
        sb.append("==========================================================================\n");
        sb.append("               GRANDMAS FOOD MENU\n");
        sb.append("==========================================================================\n\n");

        sb.append("See all our food categories with all our best flavors and amazing prices.!\n\n");
    }


    private void buildBody(StringBuilder sb, List<Category> categories) {

        for (Category category : categories) {

            appendCategory(sb, category);
            appendProducts(sb, category);

            sb.append("\n");
        }
    }

    private void appendCategory(StringBuilder sb, Category category) {

        sb.append(category.getDisplayOrder())
                .append(" - ")
                .append(category.getName().toUpperCase())
                .append("\n");

        sb.append("------------------------------------\n");
    }

    private void appendProducts(StringBuilder sb, Category category) {

        for (Product product : category.getProducts()) {

            if (!product.isAvailable()) continue;

            appendProduct(sb, product);
        }
    }

    private void appendProduct(StringBuilder sb, Product product) {

        BigDecimal price = product.getBasePrice()
                .multiply(BigDecimal.valueOf(1.19))
                .setScale(2, RoundingMode.HALF_DOWN);

        sb.append(product.getName())
                .append(" ...... $")
                .append(price)
                .append("\n");

        appendDescription(sb, product);

        sb.append("\n");
    }

    private void appendDescription(StringBuilder sb, Product product) {

        if (product.getDescription() == null || product.getDescription().isBlank()) {
            return;
        }

        sb.append("   ")
                .append(product.getDescription())
                .append("\n");
    }
}
