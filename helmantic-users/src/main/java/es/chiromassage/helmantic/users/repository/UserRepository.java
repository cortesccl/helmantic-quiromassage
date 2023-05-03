package es.chiromassage.helmantic.users.repository;

import es.chiromassage.helmantic.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername (String username);
    Optional<User> findByEmail (String email);
    Optional<User> findByEmailAndUsername (String email, String username);
    Optional<User> findByEmailOrUsername (String email, String username);
}
