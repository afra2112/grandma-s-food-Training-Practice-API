package com.grandmasfood.v1.service.implement;
import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MenuServiceImplement implements MenuService {

    private final BoxPdfService boxPdfService;
    private final WordMenuService wordMenuService;
    private final CategoryService categoryService;
    private final PlainTextMenuService plainTextMenuService;

    public byte[] generateMenu(String contentTypeHeader) throws Exception {
        return switch (contentTypeHeader) {
            case "application/pdf" -> generatePdf();
            case "plain/text" -> generatePlainText();
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> generateWord();
            default -> throw new IllegalArgumentException();
        };
    }

    private byte[] generatePdf() throws IOException {
        return boxPdfService.generatePdfMenu(categoryService.getAllCategoriesOrderedByDisplayOrder());
    }

    private byte[] generateWord() throws Exception {
        return wordMenuService.generateWordMenu(categoryService.getAllCategoriesOrderedByDisplayOrder());
    }

    private byte[] generatePlainText(){
        return plainTextMenuService.generateTextMenu(categoryService.getAllCategoriesOrderedByDisplayOrder());
    }
}
