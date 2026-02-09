package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.ProductMapper;
import com.grandmasfood.v1.dto.*;
import com.grandmasfood.v1.entity.Product;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.exception.InvalidDateToProductReportException;
import com.grandmasfood.v1.exception.SameDataRequestComparedToDBException;
import com.grandmasfood.v1.repository.ProductRepository;
import com.grandmasfood.v1.service.OrderService;
import com.grandmasfood.v1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<ProductResponse> findByFantasyNameLike(String q) {
        return productRepository.findByNameContainingIgnoreCase(q).stream().map(
                productMapper::toDto
        ).toList();
    }

    @Override
    public ProductReportResponse generateReportByDatesRange(LocalDate date1, LocalDate date2) {
        if (date2.isBefore(date1.plusDays(1)) || date2.isAfter(LocalDate.now())){
            throw new InvalidDateToProductReportException("Invalid date, expected date2 at least one day after date 1 or date2 after date1, date2 cannot be after today.");
        }

        List<MostOrLessSoldProductResponse> productsWithSells = productRepository.findProductsToReportMoreThan0Sells(date1.atStartOfDay(), date2.plusDays(1).atStartOfDay());

        long maxSoldNumber = productsWithSells.stream()
                .mapToLong(MostOrLessSoldProductResponse::soldUnits).max().orElse(0);

        long minSoldNumber = productsWithSells.stream()
                .mapToLong(MostOrLessSoldProductResponse::soldUnits).min().orElse(0);

        List<MostOrLessSoldProductResponse> maxSoldProducts = productsWithSells.stream()
                .filter(product -> product.soldUnits() == maxSoldNumber).toList();

        List<MostOrLessSoldProductResponse> minSoldProducts = productsWithSells.stream()
                .filter(product -> product.soldUnits() == minSoldNumber).toList();

        boolean mostIsMoreThanOne = maxSoldProducts.size() > 1;
        boolean lessIsMoreThanOne = minSoldProducts.size() > 1;

        return new ProductReportResponse(
                productsWithSells,
                mostIsMoreThanOne ? null : maxSoldProducts.getFirst().productName(),
                lessIsMoreThanOne ? null : minSoldProducts.getFirst().productName(),
                mostIsMoreThanOne ? maxSoldProducts : null,
                lessIsMoreThanOne ? minSoldProducts : null
        );
    }

    @Override
    @Transactional
    public void increaseSells(UUID productUUID, Integer quantity) {
        productRepository.increaseSellsAtomic(productUUID, quantity);
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
