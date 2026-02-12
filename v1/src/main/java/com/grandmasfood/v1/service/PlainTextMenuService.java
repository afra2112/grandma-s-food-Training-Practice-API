package com.grandmasfood.v1.service;

import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PlainTextMenuService {

    public byte[] generateTextMenu(List<Category> categories) {

        StringBuilder sb = new StringBuilder();

        sb.append("====================================\n");
        sb.append("               GRANDMAS FOOD MENU\n");
        sb.append("====================================\n\n");
        sb.append("See all our food categories with all our best flavors and amazing prices.!\n\n");

        for (Category category : categories) {

            sb.append(category.getDisplayOrder())
                    .append(" - ")
                    .append(category.getName().toUpperCase())
                    .append("\n");

            sb.append("------------------------------------\n");

            for (Product product : category.getProducts()) {

                if (product.isAvailable()) {

                    BigDecimal finalPrice = product.getBasePrice()
                            .multiply(BigDecimal.valueOf(1.19))
                            .setScale(2, RoundingMode.HALF_DOWN);

                    sb.append(product.getName())
                            .append(" ...... $")
                            .append(finalPrice)
                            .append("\n");

                    if (product.getDescription() != null && !product.getDescription().isBlank()) {
                        sb.append("   ")
                                .append(product.getDescription())
                                .append("\n");
                    }

                    sb.append("\n");
                }
            }

            sb.append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

}
