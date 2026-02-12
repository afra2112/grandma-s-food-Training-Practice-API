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
    public ResponseEntity<byte[]> generateMenu(@RequestHeader(value = "Content-Type") String contentTypeHeader) throws Exception {
        switch (contentTypeHeader){
            case "plain/text" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"menu.txt\"")
                        .body(menuService.generateMenu(contentTypeHeader));
            }
            case "application/pdf" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"menu.pdf\"")
                        .body(menuService.generateMenu(contentTypeHeader));
            }
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; \"menu.docx\"")
                        .body(menuService.generateMenu(contentTypeHeader));
            }
        }

        return ResponseEntity.badRequest()
                .body(menuService.generateMenu(contentTypeHeader));
    }
}