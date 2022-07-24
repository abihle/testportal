package ua.abihle.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.abihle.marketplace.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
