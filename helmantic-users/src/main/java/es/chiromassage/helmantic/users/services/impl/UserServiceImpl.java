package es.chiromassage.helmantic.users.services.impl;

import es.chiromassage.helmantic.users.clients.CourseClientRest;
import es.chiromassage.helmantic.users.models.entity.User;
import es.chiromassage.helmantic.users.models.entity.UserDetails;
import es.chiromassage.helmantic.users.repository.UserRepository;
import es.chiromassage.helmantic.users.services.UserDetailsService;
import es.chiromassage.helmantic.users.services.UserService;
import es.chiromassage.helmantic.users.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseClientRest client;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {

        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByEmailOrUsername(email, username);
    }

    @Override
    @Transactional
    public User createUser(User user) throws UserNotCreatedException {
        try {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new UserAlreadyExistsException(String.format("El usuario con username %s ya existe", user.getUsername()));
            }
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException(String.format("El usuario con email %s ya existe", user.getEmail()));
            }
            UserDetails userDetails = userDetailsService.createUserDetails(user.getUserDetails());
            user.setUserDetails(userDetails);
            return userRepository.save(user);
        } catch (UserAlreadyExistsException | UserDetailsAlreadyExistsException e) {
            throw new UserNotCreatedException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) throws UserNotCreatedException {

        try {
            Optional<User> userDBOpt = findUserById(id);
            if (userDBOpt.isEmpty()) {
                throw new UserNotFoundException("El usuario no existe");
            }
            User userDB = userDBOpt.get();
            UserDetails userDetails = user.getUserDetails();

            userDetails.setId(userDB.getUserDetails().getId());
            UserDetails userDetailsDB = userDetailsService.updateUserDetails(userDetails);
            userDB.setPassword(user.getPassword());
            userDB.setEmail(user.getEmail());
            userDB.setUsername(user.getUsername());
            userDB.setUserDetails(userDetailsDB);
            return userRepository.save(userDB);
        } catch (Exception e) {
            throw new UserNotCreatedException(e.getMessage());
        }

    }

    @Override
    @Transactional
    public void removeUser(Long id) throws UserNotFoundException {
        Optional<User> userDBOpt = findUserById(id);
        if (userDBOpt.isEmpty()) {
            throw new UserNotFoundException("No existe el usuario");
        }
        userRepository.deleteById(id);
        client.removeCourseUser(id);
    }

    @Override
    public List<User> findAllUsersById(Iterable<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }
}
