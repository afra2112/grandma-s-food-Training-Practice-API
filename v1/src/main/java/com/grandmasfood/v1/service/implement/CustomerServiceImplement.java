package com.grandmasfood.v1.service.implement;

import com.grandmasfood.v1.exception.EntityNotFoundException;
import com.grandmasfood.v1.config.mapper.CustomerMapper;
import com.grandmasfood.v1.dto.CustomerRequest;
import com.grandmasfood.v1.dto.CustomerResponse;
import com.grandmasfood.v1.dto.UpdateCustomerRequest;
import com.grandmasfood.v1.entity.Customer;
import com.grandmasfood.v1.exception.SameDataRequestComparedToDBException;
import com.grandmasfood.v1.exception.EntityAlreadyExistsException;
import com.grandmasfood.v1.repository.CustomerRepository;
import com.grandmasfood.v1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerServiceImplement implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByDocumentAndDeletedFalse(request.document())){
            throw new EntityAlreadyExistsException(Customer.class.getSimpleName(), request.document());
        }

        return customerMapper.toDto(customerRepository.save(buildCustomerFromRequest(request)));
    }

    @Override
    public CustomerResponse getCustomerByDocument(String document) {
        return customerMapper.toDto(customerRepository.findByDocumentAndDeletedFalse(document).orElseThrow(
                () -> new EntityNotFoundException(Customer.class.getSimpleName(), document)
        ));
    }

    @Override
    public void updateCustomerByDocument(UpdateCustomerRequest request, String document) {
        Customer customer = customerRepository.findByDocumentAndDeletedFalse(document).orElseThrow(
                () -> new EntityNotFoundException(Customer.class.getSimpleName(), document)
        );

        validateAtLeastOneDifferentField(customer, request);

        updateUser(customer, request);
    }

    @Override
    public void deleteCustomerByDocument(String document) {
        Customer customer = customerRepository.findByDocumentAndDeletedFalse(document).orElseThrow(
                () -> new EntityNotFoundException(Customer.class.getSimpleName(), document)
        );

        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public Customer findByDocument(String document) {
        return customerRepository.findByDocumentAndDeletedFalse(document).orElseThrow(
                () -> new EntityNotFoundException(Customer.class.getSimpleName(), document)
        );
    }

    private void validateAtLeastOneDifferentField(Customer customer, UpdateCustomerRequest request){
        boolean sameEmail = Objects.equals(customer.getEmail(), request.email());
        boolean sameNameAndSurname = Objects.equals(customer.getNameAndSurname(), request.nameAndSurname());
        boolean sameShippingAddress = Objects.equals(customer.getShippingAddress(), request.shippingAddress());
        boolean samePhoneNumber = Objects.equals(customer.getPhoneNumber(), request.phoneNumber());

        if (sameEmail && sameShippingAddress && samePhoneNumber && sameNameAndSurname){
            throw new SameDataRequestComparedToDBException("Same data compared to db, expected different data to update.");
        }
    }

    private void updateUser(Customer customer, UpdateCustomerRequest request){
        customer.setEmail(request.email());
        customer.setNameAndSurname(request.nameAndSurname());
        customer.setShippingAddress(request.shippingAddress());
        customer.setPhoneNumber(request.phoneNumber());

        customerRepository.save(customer);
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
