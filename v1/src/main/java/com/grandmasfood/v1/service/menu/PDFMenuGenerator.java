package com.grandmasfood.v1.service.menu;

import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.entity.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class PDFMenuGenerator implements MenuGenerator{
    private static final PDType1Font TITTLE_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDType1Font NORMAL_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    @Override
    public String supportedMediaType() {
        return MediaType.APPLICATION_PDF_VALUE;
    }

    @Override
    public byte[] generate(List<Category> categories) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            float pageWidth = (int) page.getTrimBox().getWidth();
            float pageHeight = (int) page.getTrimBox().getHeight();

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                putOrangeLine(contentStream, pageHeight, pageWidth);
                putImageLogo(contentStream, pageHeight, pageWidth, document);
                putMenuMainText(contentStream, pageHeight);
                putDescriptionMainText(contentStream, pageHeight);
                putCategoriesAndProductsSections(contentStream, pageHeight, categories);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);

            return outputStream.toByteArray();
        }
    }

    private void putOrangeLine(PDPageContentStream contentStream, float pageHeight, float pageWidth) throws IOException {
        contentStream.setNonStrokingColor(new Color(245, 188, 66));
        contentStream.addRect(0, pageHeight-90, pageWidth, 90);
        contentStream.fill();
    }

    private void putImageLogo(PDPageContentStream contentStream, float pageHeight, float pageWidth, PDDocument document) throws IOException {
        ClassPathResource resource = new ClassPathResource("img/images.png");
        PDImageXObject grandmaImage = PDImageXObject.createFromByteArray(document, resource.getInputStream().readAllBytes(), resource.getFilename());
        contentStream.drawImage(grandmaImage, pageWidth-210, pageHeight-220);
    }

    private void putMenuMainText(PDPageContentStream contentStream, float pageHeight) throws IOException {
        contentStream.beginText();
        contentStream.setFont(TITTLE_FONT, 70);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(70, pageHeight-70);
        contentStream.showText("Menu");
        contentStream.endText();
    }

    private void putDescriptionMainText(PDPageContentStream contentStream, float pageHeight) throws IOException {
        contentStream.beginText();
        contentStream.setLeading(17);
        contentStream.setFont(NORMAL_FONT, 17);
        contentStream.newLineAtOffset(40, pageHeight-120);
        contentStream.showText("See all our food categories with all ");
        contentStream.newLine();
        contentStream.showText("our best flavors and amazing prices.!");
        contentStream.endText();
    }

    private void putCategoriesAndProductsSections(PDPageContentStream contentStream, float pageHeight, List<Category> categories) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(40, pageHeight-200);

        for (Category category : categories){
            putCategoryName(contentStream, category);

            for (Product product : category.getProducts()){
                putProductNameAndPrice(contentStream, product);
                putProductDescriptionIfExists(contentStream, product);

            }
        }
        contentStream.endText();
    }

    private void putCategoryName(PDPageContentStream contentStream, Category category) throws IOException {
        contentStream.setLeading(35);
        contentStream.setFont(TITTLE_FONT, 20);
        contentStream.setNonStrokingColor(new Color(245, 188, 66));
        contentStream.showText(category.getDisplayOrder() + " - " + category.getName());
        contentStream.newLine();
    }

    private void putProductNameAndPrice(PDPageContentStream contentStream, Product product) throws IOException {
        if (product.isAvailable()){
            contentStream.setLeading(20);
            contentStream.setFont(TITTLE_FONT, 15);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.showText(product.getName() + "..............  $. " + String.valueOf(product.getBasePrice().multiply(BigDecimal.valueOf(1.19)).setScale(2, RoundingMode.HALF_DOWN)));
            contentStream.newLine();
        }
    }

    private void putProductDescriptionIfExists(PDPageContentStream contentStream, Product product) throws IOException {
        if (product.getDescription() != null){
            contentStream.setFont(NORMAL_FONT, 13);
            contentStream.showText(product.getDescription());
            contentStream.newLine();
        }
        contentStream.newLine();
    }
}
