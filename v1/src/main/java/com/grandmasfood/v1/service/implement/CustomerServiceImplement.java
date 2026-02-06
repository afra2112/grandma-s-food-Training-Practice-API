package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.entity.Customer;
import com.grandmasfood.v1.exception.UserAlreadyExistsException;
import com.grandmasfood.v1.repository.CustomerRepository;
import com.grandmasfood.v1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImplement implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByDocument(request.document())){
            throw new UserAlreadyExistsException("Customer already Exists by the document: " + request.document());
        }

        Customer customer = new Customer();
        customer.setEmail(request.email());
        customer.setDocument(request.document());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setShippingAddress(request.shippingAddress());
        customer.setNameAndSurname(request.nameAndSurname());

        return new CustomerResponse();
    }
}
