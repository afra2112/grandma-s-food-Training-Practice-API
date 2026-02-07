package com.grandmasfood.v1.controller;

import com.grandmasfood.v1.dto.OrderRequest;
import com.grandmasfood.v1.dto.OrderResponse;
import com.grandmasfood.v1.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PatchMapping("/{uuid}/delivered/{timestamp}")
    public ResponseEntity<OrderResponse> updateDeliveryTime(@PathVariable @NotNull UUID uuid, @PathVariable @NotNull LocalDateTime timestamp){
        return ResponseEntity.ok(orderService.updateDeliveryTime(uuid, timestamp));
    }
}
