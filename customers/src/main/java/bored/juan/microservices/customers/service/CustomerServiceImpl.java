package bored.juan.microservices.customers.service;

import bored.juan.microservices.customers.entity.Customer;
import bored.juan.microservices.customers.entity.Region;
import bored.juan.microservices.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findByStateNot("DELETED");
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if (customerDB != null) {
            return customerDB;
        }

        customer.setState("CREATED");
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB == null) {
            return null;
        }

        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        customerDB.setState("UPDATED");
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB == null) {
            return null;
        }

        customerDB.setState("DELETED");
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer getCustomerByNumberID(String numberID) {
        return customerRepository.findByNumberID(numberID);
    }

    @Override
    public List<Customer> findCustomersByLastName(String lastName) {
        return customerRepository.findByLastNameAndStateNot(lastName, "DELETED");
    }

    @Override
    public List<Customer> findCustomersByRegion(Long regionId) {
        Region region = new Region();
        region.setId(regionId);
        return customerRepository.findByRegionAndStateNot(region, "DELETED");
    }
}
