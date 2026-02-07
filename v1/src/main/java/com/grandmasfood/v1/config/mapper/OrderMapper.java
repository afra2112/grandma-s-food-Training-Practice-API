package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import com.grandmasfood.v1.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toDtoResponse(Order entity){
        return new OrderResponse(
                entity.getOrderUUID(),
                entity.getOrderCreatedAt(),
                entity.getCustomer().getDocument(),
                entity.getProduct().getProductId(),
                entity.getQuantity(),
                entity.getAdditionalInfo(),
                entity.getSubtotal(),
                entity.getIva(),
                entity.getTotal(),
                entity.isDelivered(),
                entity.getDeliveryDate()
        );
    }
}
