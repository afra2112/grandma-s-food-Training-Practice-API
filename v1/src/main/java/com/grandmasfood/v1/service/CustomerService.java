package com.grandmasfood.v1.service;

import com.grandmasfood.v1.config.enums.OrderByEnum;
import com.grandmasfood.v1.config.enums.OrderDirectionEnum;
import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.dto.UpdateCustomerRequest;
import com.grandmasfood.v1.entity.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerByDocument(String document);

    void updateCustomerByDocument(UpdateCustomerRequest request, String document);

    void deleteCustomerByDocument(String document);

    Customer findByDocument(String document);

    List<CustomerResponse> findOrderedByAndDirection(OrderByEnum orderBy, Sort.Direction direction);
}
