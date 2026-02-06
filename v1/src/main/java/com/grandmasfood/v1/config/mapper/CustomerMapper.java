package com.grandmasfood.v1.config.mapper;

import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

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
