package com.grandmasfood.v1.service.implement;
import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.service.*;
import com.grandmasfood.v1.service.menu.MenuGeneratorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@AllArgsConstructor
public class MenuServiceImplement implements MenuService {

    private final MenuGeneratorFactory menuGeneratorFactory;
    private final CategoryService categoryService;


    @Override
    public byte[] generateMenu(String contentTypeHeader) throws Exception {
        List<Category> categories = categoryService.getAllCategoriesOrderedByDisplayOrder();

        return menuGeneratorFactory
                .get(contentTypeHeader)
                .generate(categories);
    }
}
