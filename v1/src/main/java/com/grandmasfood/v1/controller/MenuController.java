package com.grandmasfood.v1.controller;

import com.grandmasfood.v1.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<byte[]> generateMenu(@RequestHeader(value = "Accept") String acceptHeader) throws Exception {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(HttpHeaders.CONTENT_DISPOSITION, buildDisposition(acceptHeader))
                .body(menuService.generateMenu(acceptHeader));
    }

    private String buildDisposition(String acceptHeader){
        return switch (acceptHeader){
            case MediaType.APPLICATION_PDF_VALUE -> "inline; filename=\"menu.pdf\"";
            case MediaType.TEXT_PLAIN_VALUE -> "inline; filename=\"menu.txt\"";
            default -> "attachment; filename=\"menu.docx\"";
        };
    }
}