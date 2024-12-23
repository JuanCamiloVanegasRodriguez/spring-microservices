package bored.juan.microservices.shopping.client;

import bored.juan.microservices.shopping.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "customers")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    ResponseEntity<Customer> getCustomer(@PathVariable Long id);
}
