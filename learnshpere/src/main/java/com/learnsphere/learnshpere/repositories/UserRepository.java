package com.learnsphere.learnshpere.repositories;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.learnsphere.learnshpere.model.User;

public interface UserRepository extends MongoRepository<User, String> {
Optional<User> findByEmail(String email);
boolean existsByEmail(String email);
}
