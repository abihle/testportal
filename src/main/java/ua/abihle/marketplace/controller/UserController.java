package ua.abihle.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.abihle.marketplace.exceptions.ItemNotFoundException;
import ua.abihle.marketplace.exceptions.UserBuyException;
import ua.abihle.marketplace.model.Product;
import ua.abihle.marketplace.model.User;
import ua.abihle.marketplace.repository.ProductRepository;
import ua.abihle.marketplace.repository.UserRepository;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{userId}/{productId}")
    public ResponseEntity buyProduct(@PathVariable Long userId, @PathVariable Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ItemNotFoundException("User id=" + userId + " not found"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ItemNotFoundException("Product id=" + productId + " not found"));
        if (user.getProducts().contains(product)) {
            throw new UserBuyException("User id=" + userId + " already have product id=" + productId);
        }
        if (user.getAmountOfMoney().compareTo(product.getPrice()) < 0) {
            throw new UserBuyException("User id=" + userId + " have not enough money to buy product id=" + productId);
        }
        user.setAmountOfMoney(user.getAmountOfMoney().subtract(product.getPrice()));
        user.getProducts().add(product);
        product.getUsers().add(user);
        productRepository.save(product);
        userRepository.save(user);

        return ResponseEntity.ok("User id=" + userId + "bought product id=" + productId);
    }

    @GetMapping("/users/{id}")
    public Set<Product> getPurchasedProducts(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Id = " + id + " not found."));
        return user.getProducts();
    }

    @PostMapping("/users")
    public ResponseEntity<String> newUser(@Valid @RequestBody User user) {
        User u = userRepository.save(user);
        return ResponseEntity.ok("User added, id = " + u.getId());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id))
            throw new ItemNotFoundException("User Id = " + id + " not found.");
        userRepository.deleteById(id);
        return ResponseEntity.ok("User with id = " + id + " deleted");
    }

}
