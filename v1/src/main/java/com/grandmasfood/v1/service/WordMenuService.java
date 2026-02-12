package com.grandmasfood.v1.service;

import com.grandmasfood.v1.entity.Category;
import com.grandmasfood.v1.entity.Product;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class WordMenuService {

    public byte[] generateWordMenu(List<Category> categories) throws IOException, InvalidFormatException {

        XWPFDocument document = new XWPFDocument();

        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMar = sectPr.addNewPgMar();
        pageMar.setLeft(BigInteger.ZERO);
        pageMar.setRight(BigInteger.ZERO);
        pageMar.setTop(BigInteger.ZERO);
        pageMar.setHeader(BigInteger.ZERO);

        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);

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
        r.setColor("000000");

        //logo
        XWPFTable infoTable = header.createTable(1, 2);
        infoTable.setWidth("100%");
        infoTable.removeBorders();

        XWPFTableCell leftCell = infoTable.getRow(0).getCell(0);
        XWPFParagraph descParagraph = leftCell.getParagraphArray(0);
        descParagraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun descRun = descParagraph.createRun();
        descRun.setText("See all our food categories with all our best flavors and amazing prices.!");
        descRun.setFontSize(15);
        descRun.setTextPosition(30);
        descRun.setBold(false);

        XWPFTableCell rightCell = infoTable.getRow(0).getCell(1);
        XWPFParagraph imageParagraph = rightCell.getParagraphArray(0);
        imageParagraph.setAlignment(ParagraphAlignment.RIGHT);

        Path imagePath = Paths.get("C:\\Users\\andres.ramirez01\\Desktop\\practices\\Kevin Training\\Grandma's Food Training\\v1\\v1\\src\\main\\resources\\img\\images.png");

        XWPFRun imageRun = imageParagraph.createRun();
        imageRun.addPicture(
                Files.newInputStream(imagePath),
                XWPFDocument.PICTURE_TYPE_PNG,
                imagePath.getFileName().toString(),
                Units.toEMU(120),
                Units.toEMU(120)
        );

        for (Category category : categories){
            XWPFParagraph categoryTittle = document.createParagraph();
            categoryTittle.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun tittleRun = categoryTittle.createRun();
            tittleRun.setText(category.getDisplayOrder() + " - " + category.getName());
            tittleRun.setColor("F4B63D");
            tittleRun.setBold(true);
            tittleRun.setFontSize(20);
            tittleRun.setTextPosition(20);

            for (Product product : category.getProducts()){
                if (product.isAvailable()){
                    XWPFParagraph productName = document.createParagraph();
                    productName.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun nameRun = productName.createRun();
                    nameRun.setText(product.getName() + "...... $." + product.getBasePrice().multiply(BigDecimal.valueOf(1.19)).setScale(2, RoundingMode.HALF_DOWN));
                    nameRun.setFontSize(13);
                    nameRun.setTextPosition(10);
                    nameRun.setBold(true);
                }

                if(product.getDescription() != null){
                    XWPFParagraph description = document.createParagraph();
                    description.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun descriptionRun = description.createRun();
                    descriptionRun.setText(product.getDescription());
                    descriptionRun.setFontSize(13);
                    descriptionRun.setTextPosition(20);
                    descriptionRun.setBold(false);
                }
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
