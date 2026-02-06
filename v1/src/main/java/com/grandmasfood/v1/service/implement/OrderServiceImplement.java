package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import com.grandmasfood.v1.entity.Order;
import com.grandmasfood.v1.repository.OrderRepository;
import com.grandmasfood.v1.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OrderServiceImplement implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();

        return null;
    }

    public BigDecimal calculateSubtotal(BigDecimal basePrice, int quantity){
        return basePrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal iva){
        return subtotal.multiply(iva);
    }
}
