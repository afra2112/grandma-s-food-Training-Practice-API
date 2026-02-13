package com.grandmasfood.v1.service.menu;

import com.grandmasfood.v1.exception.InvalidMediaTypeRequested;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MenuGeneratorFactory {

    private final Map<String, MenuGenerator> generators;

    public MenuGeneratorFactory(List<MenuGenerator> menuGeneratorList){
        this.generators = menuGeneratorList.stream()
                .collect(Collectors.toMap(
                        MenuGenerator::supportedMediaType,
                        Function.identity()
                ));
    }

    public MenuGenerator get(String mediaType){
        MenuGenerator menuGenerator = generators.get(mediaType);

        if (menuGenerator == null){
            throw new InvalidMediaTypeRequested(mediaType);
        }

        return menuGenerator;
    }
}
