package es.chiromassage.helmantic.users.repository;

import es.chiromassage.helmantic.users.models.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {

    Optional<UserDetails> findByFiscalId(String fiscalId);
}
