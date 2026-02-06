package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponse createOrder(OrderRequest request);
}
