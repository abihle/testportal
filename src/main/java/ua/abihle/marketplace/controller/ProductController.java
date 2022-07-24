package ua.abihle.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.abihle.marketplace.exceptions.ItemNotFoundException;
import ua.abihle.marketplace.model.Product;
import ua.abihle.marketplace.model.User;
import ua.abihle.marketplace.repository.ProductRepository;
import ua.abihle.marketplace.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Set<User> getUsersWhoPurchasedThisProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Id = " + id + " not found."));

        return product.getUsers();
    }

    @PostMapping("/products")
    public ResponseEntity<String> newProduct(@Valid @RequestBody Product product) {
        Product p = productRepository.save(product);
        return ResponseEntity.ok("Product created, id =" + p.getId());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Product Id = " + id + " not found."));

        for (User u : product.getUsers()) {
            u.getProducts().remove(product);
            userRepository.save(u);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product with id: " + id + " deleted");
    }
}
