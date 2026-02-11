package com.grandmasfood.v1.service.implement;
import com.grandmasfood.v1.service.BoxPdfService;
import com.grandmasfood.v1.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MenuServiceImplement implements MenuService {

    private final BoxPdfService boxPdfService;

    @Override
    public byte[] generateMenu(String contentTypeHeader) throws IOException {
        return switch (contentTypeHeader) {
            case "application/pdf" -> generatePdf();
            case "plain/text" -> generatePlainText();
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> generateWord();
            default -> throw new IllegalArgumentException();
        };
    }

    private byte[] generatePdf() throws IOException {
        return boxPdfService.generatePdfMenu();
    }

    private byte[] generateWord(){
        return new byte[0];
    }

    private byte[] generatePlainText(){
        return new byte[0];
    }
}
