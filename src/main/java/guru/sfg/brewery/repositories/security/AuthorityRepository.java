package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by sousaJ on 16/06/2021
 * in package - guru.sfg.brewery.repositories.security
 **/
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
