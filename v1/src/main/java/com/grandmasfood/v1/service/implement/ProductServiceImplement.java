package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.ProductMapper;
import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import com.grandmasfood.v1.entity.Product;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.repository.ProductRepository;
import com.grandmasfood.v1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByName(request.fantasyName())){
            throw new EntityAlreadyExistsException(Product.class.getSimpleName(), request.fantasyName());
        }

        return productMapper.toDto(productRepository.save(createProductFromRequest(request)));
    }

    @Override
    public ProductResponse getProductByUUID(UUID uuid) {
        return productMapper.toDto(productRepository.findByProductId(uuid).orElseThrow(
                () -> new EntityNotFoundException(Product.class.getSimpleName(), uuid.toString())
        ));
    }

    private Product createProductFromRequest(ProductRequest request){
        return new Product(
                request.fantasyName().toUpperCase(),
                request.category(),
                request.description(),
                request.price(),
                request.available()
        );
    }
}
