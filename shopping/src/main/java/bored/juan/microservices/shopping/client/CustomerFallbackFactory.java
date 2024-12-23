package bored.juan.microservices.shopping.client;

import bored.juan.microservices.shopping.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerFallbackFactory implements CustomerClient {

    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        Customer customer = Customer.builder()
                .firstName("None")
                .lastName("None")
                .email("None")
                .photoUrl("None")
                .build();
        return ResponseEntity.ok(customer);
    }
}
