package ua.abihle.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.abihle.marketplace.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
