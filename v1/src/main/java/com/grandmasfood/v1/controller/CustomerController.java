package com.grandmasfood.v1.controller;

import com.grandmasfood.v1.config.customBeans.Document;
import com.grandmasfood.v1.config.enums.OrderByEnum;
import com.grandmasfood.v1.config.enums.OrderDirectionEnum;
import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.dto.UpdateCustomerRequest;
import com.grandmasfood.v1.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/{document}")
    public ResponseEntity<CustomerResponse> getUser(
            @PathVariable @Document
            String document
    ){
        return ResponseEntity.ok(customerService.getCustomerByDocument(document));
    }

    @PutMapping("/{document}")
    public ResponseEntity<HttpStatus> updateCustomerData(
            @PathVariable @Document
            String document,

            @Valid @RequestBody
            UpdateCustomerRequest request
    ){
        customerService.updateCustomerByDocument(request, document);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{document}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable @Document String document){
        customerService.deleteCustomerByDocument(document);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomersOrdered(
            @RequestParam(required = false, defaultValue = "DOCUMENT")
            OrderByEnum orderBy,
            @RequestParam(required = false, defaultValue = "ASC")
            Sort.Direction direction
    ){
        return ResponseEntity.ok(customerService.findOrderedByAndDirection(orderBy, direction));
    }
}
