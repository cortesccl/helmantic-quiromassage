package es.chiromassage.helmantic.users.services.impl;

import es.chiromassage.helmantic.users.models.entity.UserDetails;
import es.chiromassage.helmantic.users.repository.UserDetailsRepository;
import es.chiromassage.helmantic.users.services.UserDetailsService;
import es.chiromassage.helmantic.users.services.exceptions.UserDetailsAlreadyExistsException;
import es.chiromassage.helmantic.users.services.exceptions.UserDetailsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetails> getUserDetails(Long id) {
        return userDetailsRepository.findById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDetails> findByFiscalId(String fiscalId) {
        return userDetailsRepository.findByFiscalId(fiscalId);
    }

    @Override
    @Transactional
    public UserDetails createUserDetails(UserDetails userDetails) throws UserDetailsAlreadyExistsException {
        Optional<UserDetails> userDetailsOp = findByFiscalId(userDetails.getFiscalId());
        if (userDetailsOp.isPresent()) {
            throw new UserDetailsAlreadyExistsException(String.format("Ya existe un usuario con documento fiscal %s", userDetails.getFiscalId()));
        }
        return userDetailsRepository.save(userDetails);
    }

    @Override
    @Transactional
    public UserDetails updateUserDetails(UserDetails userDetails) throws UserDetailsNotFoundException, UserDetailsAlreadyExistsException {
        Optional<UserDetails> userDetailsOp = getUserDetails(userDetails.getId());

        if (!userDetailsOp.isPresent()) {
            throw new UserDetailsNotFoundException("No se ha encontrado el detalle del usuario");
        }
        UserDetails userDetailsDB = userDetailsOp.get();

        String fiscalIdDB = userDetailsDB.getFiscalId();
        String fiscalIdRq = userDetails.getFiscalId();

        if (!fiscalIdDB.equalsIgnoreCase(fiscalIdRq) && findByFiscalId(fiscalIdRq).isPresent()) {
            throw new UserDetailsAlreadyExistsException(String.format("Ya existe un usuario con documento fiscal %s", userDetails.getFiscalId()));
        }

        userDetailsDB.setFirstSurname(userDetails.getFirstSurname());
        userDetailsDB.setSecondSurname(userDetails.getSecondSurname());
        userDetailsDB.setFiscalId(userDetails.getFiscalId());
        userDetailsDB.setName(userDetails.getName());
        return userDetailsRepository.save(userDetailsDB);
    }

    @Override
    @Transactional
    public void deleteUserDetails(Long id) throws UserDetailsNotFoundException {
        Optional<UserDetails> userDetailsOp = getUserDetails(id);
        if (userDetailsOp.isEmpty()) {
            throw new UserDetailsNotFoundException("No se ha encontrado el detalle del usuario con id " + id);
        }
        userDetailsRepository.deleteById(id);
    }
}
