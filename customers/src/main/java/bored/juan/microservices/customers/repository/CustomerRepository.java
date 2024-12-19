package bored.juan.microservices.customers.repository;

import bored.juan.microservices.customers.entity.Customer;
import bored.juan.microservices.customers.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
    Customer findByNumberID(String numberID);

    List<Customer> findByStateNot(String state);
    List<Customer> findByLastNameAndStateNot(String lastName, String state);
    List<Customer> findByRegionAndStateNot(Region region, String state);
}
