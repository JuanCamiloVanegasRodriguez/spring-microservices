package bored.juan.microservices.shopping.service;

import bored.juan.microservices.shopping.client.CustomerClient;
import bored.juan.microservices.shopping.client.ProductClient;
import bored.juan.microservices.shopping.entity.Invoice;
import bored.juan.microservices.shopping.model.Customer;
import bored.juan.microservices.shopping.model.Product;
import bored.juan.microservices.shopping.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(CustomerClient customerClient,
                              ProductClient productClient,
                              InvoiceRepository invoiceRepository) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }


    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB != null) {
            return invoiceDB;
        }
        invoice.setState("CREATED");
        invoiceDB = invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach(item -> productClient.updateStock(item.getProductId(), item.getQuantity() * -1));
        return invoiceRepository.save(invoice);
    }


    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }


    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if (invoiceDB == null) {
            return null;
        }
        invoiceDB.setState("DELETED");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
            invoice.setCustomer(customer);
            invoice.getItems().forEach(item -> {
                Product product = productClient.getProduct(item.getProductId()).getBody();
                item.setProduct(product);
            });
        }
        return invoice;
    }
}