package com.grandmasfood.v1.controller;

import com.grandmasfood.v1.dto.ProductReportResponse;
import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import com.grandmasfood.v1.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponse> getProductByUUID(@PathVariable UUID uuid){
        return ResponseEntity.ok(productService.getProductByUUID(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable UUID uuid, @Valid @RequestBody ProductRequest request){
        productService.updateProductByUUID(request, uuid);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable UUID uuid){
        productService.deleteProductByUUID(uuid);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> getByFantasyNameLike(@RequestParam String q){
        return ResponseEntity.ok(productService.findByFantasyNameLike(q));
    }

    @GetMapping("/sales_report/{date1}/{date2}")
    public ResponseEntity<ProductReportResponse> generateProductReport(
            @PathVariable
            @DateTimeFormat(pattern = "YYYYMMDD")
            LocalDateTime date1,
            @PathVariable
            @DateTimeFormat(pattern = "YYYYMMDD")
            LocalDateTime date2
    ){
        return ResponseEntity.ok(productService.generateReportByDatesRange(date1,date2));
    }
}
