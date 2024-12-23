package bored.juan.microservices.shopping.client;

import bored.juan.microservices.shopping.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customers")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customers", fallbackMethod = "getCustomerFallback")
    ResponseEntity<Customer> getCustomer(@PathVariable Long id);

    default ResponseEntity<Customer> getCustomerFallback(Long id, Throwable throwable) {
        Customer customer = Customer.builder()
                .id(id)
                .firstName("None")
                .lastName("None")
                .email("None")
                .photoUrl("None")
                .build();
        return ResponseEntity.ok(customer);
    }
}
