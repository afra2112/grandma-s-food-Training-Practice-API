package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
}
