package es.chiromassage.helmantic.users.services;

import es.chiromassage.helmantic.users.models.entity.User;
import es.chiromassage.helmantic.users.models.entity.UserDetails;
import es.chiromassage.helmantic.users.services.exceptions.UserDetailsAlreadyExistsException;
import es.chiromassage.helmantic.users.services.exceptions.UserDetailsNotFoundException;
import es.chiromassage.helmantic.users.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {
    Optional<UserDetails> getUserDetails(Long id);

    Optional<UserDetails> findByFiscalId (String fiscalId);

    UserDetails createUserDetails(UserDetails userDetails) throws UserDetailsAlreadyExistsException;

    UserDetails updateUserDetails (UserDetails userDetails) throws UserDetailsNotFoundException, UserDetailsAlreadyExistsException;

    void deleteUserDetails (Long id) throws UserDetailsNotFoundException;
}
