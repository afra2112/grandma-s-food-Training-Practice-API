package com.grandmasfood.v1.service;

import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerByDocument(String document);

    void updateCustomerByDocument(UpdateCustomerRequest request, String document);

    void deleteCustomerByDocument(String document);
}
