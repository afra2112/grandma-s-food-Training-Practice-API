package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerByDocument(String document);

    CustomerResponse updateCustomerByDocument(String document);
}
