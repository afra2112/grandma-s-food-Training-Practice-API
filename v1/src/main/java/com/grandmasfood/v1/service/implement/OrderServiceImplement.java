package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.OrderMapper;
import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import com.grandmasfood.v1.entity.Customer;
import com.grandmasfood.v1.entity.Order;
import com.grandmasfood.v1.entity.Product;
import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.repository.OrderRepository;
import com.grandmasfood.v1.service.CustomerService;
import com.grandmasfood.v1.service.OrderService;
import com.grandmasfood.v1.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImplement implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Product product = productService.findByUUIDName(request.productId());
        Customer customer = customerService.findByDocument(request.customerDocument());

        BigDecimal subtotal = calculateSubtotal(product.getBasePrice(), request.quantity());

        return orderMapper.toDtoResponse(orderRepository.save(buildOrderToCreate(request, customer, product, subtotal)));
    }

    @Override
    public OrderResponse updateDeliveryTime(UUID orderUUID, LocalDateTime timestamp) {
        Order order = orderRepository.findByOrderUUID(orderUUID).orElseThrow(
                () -> new EntityNotFoundException(Order.class.getSimpleName(), orderUUID.toString())
        );

        order.setDeliveryDate(timestamp);

        return orderMapper.toDtoResponse(orderRepository.save(order));
    }

    public Order buildOrderToCreate(OrderRequest request, Customer customer, Product product, BigDecimal sutotal){
        Order order = new Order();
        order.setAdditionalInfo(request.additionalInfo());
        order.setCustomer(customer);
        order.setQuantity(request.quantity());
        order.setProduct(product);
        order.setSubtotal(sutotal);
        order.setTotal(calculateTotal(sutotal, order.getIva()));
        order.setOrderCreatedAt(LocalDateTime.now());
        return order;
    }

    public BigDecimal calculateSubtotal(BigDecimal basePrice, int quantity){
        return basePrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal iva){
        return subtotal.multiply(iva);
    }
}
