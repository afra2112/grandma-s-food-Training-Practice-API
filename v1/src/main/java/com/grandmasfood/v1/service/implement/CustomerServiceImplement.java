package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.config.mapper.CustomerMapper;
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
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByDocument(request.document())){
            throw new UserAlreadyExistsException("Customer already Exists by the document: " + request.document());
        }

        return customerMapper.toDto(customerRepository.save(buildCustomerFromRequest(request)));
    }

    private Customer buildCustomerFromRequest(CustomerRequest request){
        return new Customer(
                request.document(),
                request.nameAndSurname(),
                request.email(),
                request.phoneNumber(),
                request.shippingAddress()
        );
    }
}
