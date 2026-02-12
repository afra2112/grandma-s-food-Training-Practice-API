package com.grandmasfood.v1.service.implement;
import com.grandmasfood.v1.service.BoxPdfService;
import com.grandmasfood.v1.service.CategoryService;
import com.grandmasfood.v1.service.MenuService;
import com.grandmasfood.v1.service.WordMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MenuServiceImplement implements MenuService {

    private final BoxPdfService boxPdfService;
    private final WordMenuService wordMenuService;
    private final CategoryService categoryService;

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
        return new byte[0];
    }
}
