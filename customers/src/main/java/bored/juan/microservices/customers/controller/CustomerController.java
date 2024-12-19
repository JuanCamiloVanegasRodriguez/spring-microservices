package bored.juan.microservices.customers.controller;

import bored.juan.microservices.customers.entity.Customer;
import bored.juan.microservices.customers.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId", required = false) Long regionId,
                                                           @RequestParam(name = "lastName", required = false) String lastName) {
        if (regionId != null && lastName != null) {
            log.error("Retrieve operation failed: You can't filter by regionId and lastName at the same time");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't filter by regionId and lastName at the same time");
        }

        List<Customer> customers;
        if (regionId != null) {
            customers = customerService.findCustomersByRegion(regionId);
        } else if (lastName != null) {
            customers = customerService.findCustomersByLastName(lastName);
        } else {
            customers = customerService.findCustomerAll();
        }

        if (customers == null || customers.isEmpty()) {
            log.error("Customers not found");
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            log.error("Retrieve operation failed: Customer with id {} does not exist", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            log.error("Retrieve operation failed: Customer with email {} does not exist", email);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/numberID/{numberID}")
    public ResponseEntity<Customer> getCustomerByNumberID(@PathVariable String numberID) {
        Customer customer = customerService.getCustomerByNumberID(numberID);
        if (customer == null) {
            log.error("Retrieve operation failed: Customer with numberID {} does not exist", numberID);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer,
                                                   BindingResult result) {
        if (result.hasErrors()) {
            log.error("Failed to create customer due to validation errors: {}", formatMessage(result));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        Customer customerDB = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
                                                   @PathVariable Long id,
                                                   BindingResult result) {
        if (result.hasErrors()) {
            log.error("Failed to update customer due to validation errors: {}", formatMessage(result));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        customer.setId(id);
        Customer customerDB = customerService.updateCustomer(customer);
        if (customerDB == null) {
            log.error("Update operation failed: Customer with id {} does not exist", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customerDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            log.error("Delete operation failed: Customer with id {} does not exist", id);
            return ResponseEntity.notFound().build();
        }

        Customer customerDB = customerService.deleteCustomer(customer);
        return ResponseEntity.ok(customerDB);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getAllErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getCode(), err.getDefaultMessage());
                    return error;
                }).toList();
        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
