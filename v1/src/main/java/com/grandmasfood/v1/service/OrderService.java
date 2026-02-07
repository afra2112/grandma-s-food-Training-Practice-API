package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse updateDeliveryTime(UUID orderUUID, LocalDateTime timestamp);
}
