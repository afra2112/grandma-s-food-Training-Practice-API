package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerResponse dto){
        return new Customer(
                dto.customerDocument(),
                dto.customerNameAndSurname(),
                dto.customerEmail(),
                dto.phoneNumber(),
                dto.shippingAddress()
        );
    }

    public CustomerResponse toDto(Customer entity){
        return new CustomerResponse(
                entity.getDocument(),
                entity.getNameAndSurname(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getShippingAddress()
        );
    }
}
