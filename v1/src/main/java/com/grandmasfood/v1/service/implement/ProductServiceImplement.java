package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.ProductMapper;
import com.grandmasfood.v1.dto.ProductRequest;
import com.grandmasfood.v1.dto.ProductResponse;
import com.grandmasfood.v1.entity.Product;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.exception.SameDataRequestComparedToDBException;
import com.grandmasfood.v1.repository.ProductRepository;
import com.grandmasfood.v1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

        return productMapper.toDto(productRepository.save(productMapper.toEntity(request)));
    }

    @Override
    public ProductResponse getProductByUUID(UUID uuid) {
        return productMapper.toDto(productRepository.findByProductId(uuid).orElseThrow(
                () -> new EntityNotFoundException(Product.class.getSimpleName(), uuid.toString())
        ));
    }

    @Override
    public void updateProductByUUID(ProductRequest request, UUID uuid) {
        Product product = productRepository.findByProductId(uuid).orElseThrow(
                () -> new EntityNotFoundException(Product.class.getSimpleName(), uuid.toString())
        );

        validateAtLeatsOneFieldDifferent(request, product);
        validateNotExistingFantasyName(request.fantasyName(), product.getProductId());

        productRepository.save(mapEntityToUpdate(product, request));
    }

    @Override
    public void deleteProductByUUID(UUID uuid) {
        Product product = productRepository.findByProductId(uuid).orElseThrow(
                () -> new EntityNotFoundException(Product.class.getSimpleName(), uuid.toString())
        );

        productRepository.delete(product);
    }

    @Override
    public Product findByUUIDName(UUID uuid) {
        return productRepository.findByProductId(uuid).orElseThrow(
                () -> new EntityNotFoundException(Product.class.getSimpleName(), uuid.toString())
        );
    }

    private Product mapEntityToUpdate(Product product, ProductRequest request){
        product.setAvailable(request.available());
        product.setName(request.fantasyName());
        product.setCategory(request.category());
        product.setDescription(request.description());
        product.setBasePrice(request.price());
        return product;
    }

    private void validateAtLeatsOneFieldDifferent(ProductRequest request, Product product){
        boolean sameFantasyName = Objects.equals(request.fantasyName(), product.getName());
        boolean sameCategory = Objects.equals(request.category(), product.getCategory());
        boolean sameDescription = Objects.equals(request.description(), product.getDescription());
        boolean samePrice = Objects.equals(request.price(), product.getBasePrice());
        boolean sameAvailable = request.available() == product.isAvailable();

        if (sameFantasyName && sameAvailable && sameCategory && sameDescription && samePrice){
            throw new SameDataRequestComparedToDBException("Same data compared to db data. expected al least one field different to update");
        }
    }

    private void validateNotExistingFantasyName(String requestedFantasyName, UUID uuid){
        if (productRepository.existsByNameAndProductIdNot(requestedFantasyName, uuid)){
            throw new EntityAlreadyExistsException(Product.class.getSimpleName(), requestedFantasyName);
        }
    }
}
