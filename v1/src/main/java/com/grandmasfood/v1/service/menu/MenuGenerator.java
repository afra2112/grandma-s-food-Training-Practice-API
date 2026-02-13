package com.grandmasfood.v1.service.menu;

import com.grandmasfood.v1.entity.Category;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

public interface MenuGenerator {

    String supportedMediaType();

    byte[] generate(List<Category> categories) throws IOException, InvalidFormatException;
}
