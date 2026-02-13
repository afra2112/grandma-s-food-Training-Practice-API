package com.grandmasfood.v1.service.menu;

import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.entity.Product;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Component
public class DOCXMenuGenerator implements MenuGenerator{
    @Override
    public String supportedMediaType() {
        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    }

    @Override
    public byte[] generate(List<Category> categories) throws IOException, InvalidFormatException {
        XWPFDocument document = new XWPFDocument();

        configurePage(document);
        buildHeader(document);
        buildBody(document, categories);

        return writeDocument(document);
    }

    private void configurePage(XWPFDocument document){
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();

        pageMar.setLeft(BigInteger.ZERO);
        pageMar.setRight(BigInteger.ZERO);
        pageMar.setTop(BigInteger.ZERO);
        pageMar.setHeader(BigInteger.ZERO);
    }

    private void buildHeader(XWPFDocument document)
            throws IOException, InvalidFormatException {

        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);

        createBanner(header);
        createInfoSection(header);
    }

    private void createBanner(XWPFHeader header) {

        XWPFTable banner = header.createTable(1, 1);
        banner.setWidth("100%");
        banner.removeBorders();

        XWPFTableCell cell = banner.getRow(0).getCell(0);
        cell.setColor("F4B63D");

        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        p.setSpacingAfter(200);

        XWPFRun r = p.createRun();
        r.setText("MENU");
        r.setBold(true);
        r.setFontSize(42);
    }

    private void createInfoSection(XWPFHeader header)
            throws IOException, InvalidFormatException {

        XWPFTable infoTable = header.createTable(1, 2);
        infoTable.setWidth("100%");
        infoTable.removeBorders();

        XWPFParagraph descParagraph =
                infoTable.getRow(0).getCell(0).getParagraphArray(0);

        descParagraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun descRun = descParagraph.createRun();
        descRun.setText("See all our food categories with all our best flavors and amazing prices.!");
        descRun.setFontSize(15);
        descRun.setTextPosition(30);

        XWPFParagraph imageParagraph =
                infoTable.getRow(0).getCell(1).getParagraphArray(0);

        imageParagraph.setAlignment(ParagraphAlignment.RIGHT);

        ClassPathResource resource = new ClassPathResource("img/images.png");

        XWPFRun imageRun = imageParagraph.createRun();
        imageRun.addPicture(
                resource.getInputStream(),
                XWPFDocument.PICTURE_TYPE_PNG,
                resource.getFilename(),
                Units.toEMU(120),
                Units.toEMU(120)
        );
    }

    private void buildBody(XWPFDocument document, List<Category> categories) {

        for (Category category : categories) {

            addCategoryTitle(document, category);
            addProducts(document, category);
        }
    }

    private void addCategoryTitle(XWPFDocument document, Category category) {

        XWPFParagraph categoryTitle = document.createParagraph();
        categoryTitle.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun run = categoryTitle.createRun();
        run.setText(category.getDisplayOrder() + " - " + category.getName());
        run.setColor("F4B63D");
        run.setBold(true);
        run.setFontSize(20);
        run.setTextPosition(20);
    }

    private void addProducts(XWPFDocument document, Category category) {

        for (Product product : category.getProducts()) {

            if (!product.isAvailable()) continue;

            addProductName(document, product);
            addProductDescription(document, product);
        }
    }

    private void addProductName(XWPFDocument document, Product product) {

        XWPFParagraph productName = document.createParagraph();
        productName.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun run = productName.createRun();
        run.setText(product.getName() +
                "...... $." +
                product.getBasePrice().multiply(BigDecimal.valueOf(1.19)).setScale(2, RoundingMode.HALF_DOWN));

        run.setFontSize(13);
        run.setTextPosition(10);
        run.setBold(true);
    }

    private void addProductDescription(XWPFDocument document, Product product) {

        if (product.getDescription() == null) return;

        XWPFParagraph description = document.createParagraph();
        description.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun run = description.createRun();
        run.setText(product.getDescription());
        run.setFontSize(13);
        run.setTextPosition(20);
    }

    private byte[] writeDocument(XWPFDocument document) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        document.close();

        return outputStream.toByteArray();
    }
}
