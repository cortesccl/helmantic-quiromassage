package es.chiromassage.helmantic.users.services;

import es.chiromassage.helmantic.users.models.entity.User;
import es.chiromassage.helmantic.users.services.exceptions.UserNotCreatedException;
import es.chiromassage.helmantic.users.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(Long id);

    Optional<User> findByEmail (String email);

    Optional<User> findByUsername (String username);

    Optional<User> findByUsernameOrEmail (String username, String email);

    User createUser(User user) throws UserNotCreatedException;

    User updateUser (Long id, User user) throws UserNotCreatedException;

    void removeUser(Long id) throws UserNotFoundException;

    List<User> findAllUsersById(Iterable<Long> ids);
}
