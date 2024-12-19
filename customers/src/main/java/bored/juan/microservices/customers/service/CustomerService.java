package bored.juan.microservices.customers.service;

import bored.juan.microservices.customers.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findCustomerAll();
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    Customer deleteCustomer(Customer customer);
    Customer getCustomer(Long id);
    Customer getCustomerByEmail(String email);
    Customer getCustomerByNumberID(String numberID);
    List<Customer> findCustomersByLastName(String lastName);
    List<Customer> findCustomersByRegion(Long regionId);
}
