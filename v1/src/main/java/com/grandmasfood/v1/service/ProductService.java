package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductByUUID(UUID uuid);

    void updateProductByUUID(ProductRequest request, UUID uuid);
}
